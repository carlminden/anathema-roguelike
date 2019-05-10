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
package entities.characters.player

import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.entities.characters.foes.ai.Faction
import com.anathema_roguelike.entities.characters.inventory.PrimaryWeapon
import com.anathema_roguelike.entities.characters.inventory.SingleSlot.SecondaryWeapon
import com.anathema_roguelike.entities.characters.player.classes.{ClassSet, PlayerClass}
import com.anathema_roguelike.entities.items.AnyItemFactory
import com.anathema_roguelike.entities.items.armor.{ArmorMaterial, Boots, Chestpiece, Helm, Pants}
import com.anathema_roguelike.entities.items.weapons.Weapon
import com.anathema_roguelike.entities.items.weapons.types.WeaponType
import com.anathema_roguelike.environment.{HasLocation, Location}
import com.anathema_roguelike.main.Game
import com.anathema_roguelike.main.display.DungeonMap.DungeonLayer
import com.anathema_roguelike.main.display.{Color, VisualRepresentation}
import com.anathema_roguelike.main.input.{InputHandler, PlayerKeyHandler}
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.SelectionScreen
import com.anathema_roguelike.main.utilities.Utils
import com.anathema_roguelike.stats.characterstats.secondarystats.Light
import squidpony.squidgrid.gui.gdx.SColor

import scala.reflect.runtime.universe._

class Player(location: HasLocation) extends Character(location) {
  setFaction(Faction.PLAYER)
  val itemFactory: AnyItemFactory = new AnyItemFactory

  getInventory.equip(new Helm(ArmorMaterial.CHAINMAIL))
  getInventory.equip(new Chestpiece(ArmorMaterial.LEATHER))
  getInventory.equip(new Pants(ArmorMaterial.CHAINMAIL))
  getInventory.equip(new Boots(ArmorMaterial.CHAINMAIL))

  itemFactory.generateByType[WeaponType]
  itemFactory.generateByType[WeaponType]

  System.out.println(getInventory.getEquippedItems)

  private var name: String = "Carl"
  private val inputHandler: InputHandler = new InputHandler(new PlayerKeyHandler(this))
  private val classSet: ClassSet = new ClassSet(this)

  override def toString: String = name

  def setName(name: String): Unit = {
    this.name = name
  }

  def onDeath(): Unit = {
    System.out.println("YOU WERE KILLED")
  }

  def getClassLevels[C <: PlayerClass : TypeTag]: Int = {
    classSet.getClassLevels[C]
  }

  def grantClassLevel[C <: PlayerClass : TypeTag](): Unit = {
    classSet.grantClassLevel[C]()
  }

  def grantClassLevel[C <: PlayerClass : TypeTag](cls: Class[C]): Unit = {
    classSet.grantClassLevel[C]()
  }

  def getClasses: Iterable[Class[_ <: PlayerClass]] = classSet.getClasses

  override def levelUp(): Unit = {
    val classes: Array[Class[_ <: PlayerClass]] = Utils.getSubclasses[PlayerClass]().toArray

    val classSelectionScreen: SelectionScreen[Class[_ <: PlayerClass]] = {
      new SelectionScreen[Class[_ <: PlayerClass]]("Select your Class", classes, cancellable = false)
    }

    grantClassLevel(classSelectionScreen.run.get)
    super.levelUp()
  }

  def setNextPendingAction(): Unit = {
    Game.getInstance.getUserInterface.newLine()
    Game.getInstance.getDisplay.unlock()
    Game.getInstance.getInput.proccessInput(inputHandler, () => hasPendingActions, null)
    Game.getInstance.getDisplay.lock()
  }

  protected def renderThis(): Unit = {
    Game.getInstance.getMap.renderEntity(DungeonLayer.PLAYER, this)

    val color: SColor = Color.factory.blend(Color.NO_LIGHT_PLAYER, Color.WHITE, getStatAmount[Light] + 0.2)

    Game.getInstance.getMap.renderChar(DungeonLayer.PLAYER, getX, getY, getVisualRepresentation.char, color)
  }

  override def setFacing(facing: Double): Unit = {
    super.setFacing(facing)
  }

  def getVisualRepresentation: VisualRepresentation = new VisualRepresentation('@')
}
