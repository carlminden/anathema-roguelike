/*******************************************************************************
 * Copyright (c) 2019. Carl Minden
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package com.anathema_roguelike
package entities.characters

import com.anathema_roguelike.actors.Action
import com.anathema_roguelike.entities.Entity
import com.anathema_roguelike.entities.characters.actions.{BasicMovementAction, OpenDoorAction, TakeStairsAction}
import com.anathema_roguelike.entities.characters.actions.attacks.{BasicAttack, BasicAttackPerk}
import com.anathema_roguelike.entities.characters.events.{ResourceChangedEvent, TurnEndEvent, TurnStartEvent}
import com.anathema_roguelike.entities.characters.foes.ai.Faction
import com.anathema_roguelike.entities.characters.inventory.{Inventory, PrimaryWeapon}
import com.anathema_roguelike.entities.characters.perks.{Perk, PerkSet}
import com.anathema_roguelike.entities.characters.player.Player
import com.anathema_roguelike.entities.characters.player.perks.abilities.Ability
import com.anathema_roguelike.entities.characters.player.perks.specializations.AbilitySpecializationSet
import com.anathema_roguelike.entities.characters.stimuli.PerceivedStimuli
import com.anathema_roguelike.entities.events.EnvironmentChangedEvent
import com.anathema_roguelike.environment.{HasLocation, Location}
import com.anathema_roguelike.environment.features.Doorway
import com.anathema_roguelike.environment.terrain.grounds.Stairs
import com.anathema_roguelike.main.display.BufferMask
import com.anathema_roguelike.main.utilities.position.Direction
import com.anathema_roguelike.main.utilities.position.Direction.Direction2
import com.anathema_roguelike.stats.StatSet.CharacterStats
import com.anathema_roguelike.stats.characterstats.CharacterStat
import com.anathema_roguelike.stats.characterstats.attributes.Attribute
import com.anathema_roguelike.stats.{HasStats, StatSet}
import com.anathema_roguelike.stats.characterstats.resources.{BoundedResource, Resource}
import com.anathema_roguelike.stats.characterstats.secondarystats.LightEmission
import com.anathema_roguelike.stats.characterstats.secondarystats.detection.{Visibility, VisibilityLevel}
import com.anathema_roguelike.stats.effects.{Effect, HasEffect}
import com.google.common.eventbus.Subscribe

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.reflect.runtime.universe._

abstract class Character extends Entity with HasStats[Character, CharacterStat] {
  private var faction: Int = 0
  private var level: Int = 0
  private var turn: Long = 0
  private val perks: PerkSet = new PerkSet
  private val abilitySpecializations: AbilitySpecializationSet = new AbilitySpecializationSet
  private lazy val inventory: Inventory = new Inventory(this)
  private var alive: Boolean = true
  private var facing: Double = Direction.UP.toAngle

  //TODO: Initialize this better
  private var currentVisibility: BufferMask = _

  private val stats: CharacterStats = new CharacterStats(this)
  private val percievedStimuli: PerceivedStimuli = new PerceivedStimuli(this)
  private val lastSeenCharacterLocations: mutable.Map[Character, Location] = mutable.Map[Character, Location]()
  private val pendingActions: ListBuffer[Action[Character]] = ListBuffer[Action[Character]]()

  new BasicAttackPerk().grant(this)

  def onDeath(): Unit

  protected def setNextPendingAction(): Unit

  def levelUp(): Unit = {
    level += 1
  }

  override def getStatSet: StatSet[Character, CharacterStat] = stats

  def getInventory: Inventory = inventory

  def getFaction: Int = faction

  def getLevel: Int = level

  def getPerks[T <: Perk : TypeTag]: Iterable[T] = perks.get[T]

  def getPerks[T <: Perk : TypeTag](predicate: T => Boolean): Iterable[T] = perks.get[T](predicate)

  def addPerk(perk: Perk): Unit = {
    perks.add(perk)
  }

  def removePerk(perk: Perk): Unit = {
    perks.remove(perk)
  }

  def hasPerk[T <: Perk : TypeTag]: Boolean = getPerks[T].nonEmpty

  def getSpecialization(ability: Class[_ <: Ability]): Int = abilitySpecializations.getSpecializationLevel(ability)

  def specialize[T <: Ability : TypeTag](amount: Int): Unit = {
    abilitySpecializations.specialize[T](amount)
  }

  protected def setFaction(faction: Int): Unit = {
    this.faction = faction
  }

  def basicAttack(target: Character): Unit = {
    addPendingAction(getPerks[BasicAttackPerk].head.activate(List(target)))
  }

  @Subscribe def handleResourceChangedEvent(event: ResourceChangedEvent): Unit = {
    //TODO not sure if i need to do anything in this case but figure its a good thing to have an event for
  }

  override def act(): Unit = {
    computeVisibility()
    super.act()
    computeVisibility()
    lastSeenCharacterLocations.clear()
    getCurrentlyVisibleCharacters.foreach(c => lastSeenCharacterLocations.put(c, c.getLocation))
  }

  override def getNextAction: Option[Action[_]] = {
    getEventBus.post(new TurnStartEvent)
    if (!hasPendingActions) {
      turn += 1
      setNextPendingAction()
    }
    getEventBus.post(new TurnEndEvent)

    val ret = pendingActions.headOption

    pendingActions.drop(1)

    ret
  }

  @Subscribe
  def handleEnvironmentChangedEvent(e: EnvironmentChangedEvent): Unit = {
    e.reRegister(percievedStimuli)
  }

  def getTurn: Long = turn

  def move(direction: Direction): Unit = {
    val location: Location = getEnvironment.getLocation(Direction.offset(getPosition, direction))
    if (location.isPassable) {
      location.getTerrain match {
        case door: Doorway if this.isInstanceOf[Player] => addPendingAction(new OpenDoorAction(this, door))
        case _ => {
          val characters: Iterable[Character] = location.getEntities(classOf[Character])
          if (characters.nonEmpty) {
            val character: Character = location.getEntities(classOf[Character]).iterator.next
            if (!Faction.friendly(character, this)) basicAttack(character)
          }
          else addPendingAction(new BasicMovementAction(this, location))
        }
      }
    }
  }

  def isAlive: Boolean = alive

  def kill(): Unit = {
    alive = false
  }

  def getPrimaryWeaponDamage: Int = inventory.getSlot[PrimaryWeapon].getEquippedItem.get.getWeaponDamage

  def getResourceMax[R <: BoundedResource : TypeTag]: Int = getStat[R].getMaximum

  def setAttributeScore[A <: Attribute : TypeTag](amount: Int): Unit = {
    stats.getStat[A].setScore(amount)
  }

  def setResource[S <: Resource : TypeTag](initiator: Option[Character], source: Option[_ <: HasEffect[_ <: Effect[Character, _]]], amount: Int): Unit = {
    stats.getStat[S].set(initiator, source, amount)
  }

  def modifyResource[S <: Resource : TypeTag](initiator: Option[Character], source: Option[_ <: HasEffect[_ <: Effect[Character, _]]], amount: Int): Unit = {
    val resource: Resource = stats.getStat[S]
    resource.modify(initiator, source, amount)
  }

  def takeStairs(direction: Direction2): Unit = {
    getEnvironment.getLocation(getPosition).getTerrain match {
      case stairs: Stairs => {
        if (stairs.getDirection == direction) {
          addPendingAction(new TakeStairsAction(this, stairs))
        }
      }
      case _ =>
    }
  }

  def getVisibilityOf(character: Character): VisibilityLevel = {

    val ThisChar = this

    val visibility = character.getStat[Visibility].getVisibilityLevel
    val visible = getCurrentVisibility.get(character.getX, character.getY)
    val adjacent = getPosition.isAdjacentTo(character)

    character match {
      case null => VisibilityLevel.IMPERCEPTIBLE
      case ThisChar => VisibilityLevel.EXPOSED
      case _ if visible && adjacent => VisibilityLevel.EXPOSED
      //TODO: Check the logic below, it doesnt really make sense to me
      case _ if visible && adjacent && visibility.value < 4 => VisibilityLevel.VISIBLE
      case _ if visible && getPosition.distance(character) <= 2 &&
                ((visibility == VisibilityLevel.CONCEALED) || (visibility == VisibilityLevel.PARTIALLYCONCEALED)) => VisibilityLevel.VISIBLE
      case _ if visible => character.getStat[Visibility].getVisibilityLevel
      case _ => VisibilityLevel.IMPERCEPTIBLE
    }
  }

  override def isVisibleTo(character: Character): Boolean = character.getVisibilityOf(this).value >= 3

  def getCurrentVisibility: BufferMask = {
    if (currentVisibility == null) computeVisibility()
    currentVisibility
  }

  def hasLineOfSightTo(location: HasLocation): Boolean = getCurrentVisibility.get(location.getX, location.getY)

  def hasLineOfEffectTo(location: HasLocation): Boolean = getLocation.getEnvironment.lineOfEffectBetween(getLocation, location)

  def computeVisibility(): Unit = {
    getEnvironment.getLightLevels.recomputeLightLevels()
    currentVisibility = getEnvironment.getLitFOVProcessor.computeLitFOVMask(this)
  }

  def getCurrentlyVisibleCharacters: Iterable[Character] = {
    getEnvironment.getEntities(classOf[Character]).filter(c => c.isVisibleTo(this))
  }

  def getLastSeenCharacterLocations: mutable.Map[Character, Location] = lastSeenCharacterLocations

  def getFacing: Double = facing

  def setFacing(facing: Double): Unit = {
    this.facing = facing
  }

  def getPercievedStimuli: PerceivedStimuli = percievedStimuli

  override def getLightEmission: Double = getStatAmount[LightEmission]

  def addPendingAction(pendingAction: Option[Action[Character]]): Unit = {
    pendingAction.foreach(pa => pendingActions += pa)
  }

  def addPendingActions(pendingActions: Action[Character]*): Unit = {
    this.pendingActions ++= pendingActions
  }

  def addPendingActions(pendingActions: Iterable[_ <: Action[Character]]): Unit = {
    this.pendingActions ++= pendingActions
  }

  def hasPendingActions: Boolean = pendingActions.nonEmpty
}
