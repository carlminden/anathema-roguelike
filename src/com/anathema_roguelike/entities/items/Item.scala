package com.anathema_roguelike
package entities.items

import java.util.Optional

import com.anathema_roguelike.actors.Action
import com.anathema_roguelike.entities.Entity
import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.environment.Location
import com.anathema_roguelike.main.Game
import com.anathema_roguelike.main.display.DungeonMap.DungeonLayer
import com.anathema_roguelike.stats.Stat.CharacterStat
import com.anathema_roguelike.stats.StatSet.ItemStats
import com.anathema_roguelike.stats.{HasStats, StatSet}
import com.anathema_roguelike.stats.effects.{Effect, HasEffect}
import com.anathema_roguelike.stats.itemstats.ItemStat

object Item {
  def locationOfCharacterOrLocation(location: Either[Location, Character]) = {
    location match {
      case Left(l) => l
      case Right(c) => c.getLocation
    }
  }
}

abstract class Item(location: Either[Location, Character])
  extends Entity(Item.locationOfCharacterOrLocation(location))
    with HasStats[Item, ItemStat] with HasEffect[Effect[Character, CharacterStat]] {

  location match {
    case Left(_) =>
    case Right(c) => c.getInventory.equip(this)
  }

  private val stats = new ItemStats(this)
  private var wearer: Option[Character] = None

  override protected def renderThis(): Unit = {
    Game.getInstance.getMap.renderEntity(DungeonLayer.NORMAL, this)
  }

  def equippedTo(character: Character): Unit = {
    wearer = Some(character)
    character.applyEffect(getEffect)
  }

  def removedFrom(character: Character): Unit = {
    wearer = Option.empty
    character.removeEffectBySource(this)
  }

  def getWearer: Option[Character] = wearer

  override def getStatSet: ItemStats = stats

  override def getLocation: Location = {
    wearer.map(_.getLocation).getOrElse(location)
  }

  override def getNextAction: Option[Action[_]] = {
    //Most Items shouldnt do anything, but it could be interesting in some cases, maybe certain magical items will make noise/resonance
    Option.empty
  }
}