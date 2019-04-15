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
  def locationOfCharacterOrLocation(location: Either[Location, Character]): Location = {
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
    setLocation(character.getLocation)
  }

  def getWearer: Option[Character] = wearer

  override def getStatSet: ItemStats = stats

  override def getLocation: Location = {
    wearer.map(_.getLocation).getOrElse(super.getLocation)
  }

  override def getNextAction: Option[Action[_]] = {
    //Most Items shouldnt do anything, but it could be interesting in some cases, maybe certain magical items will make noise/resonance
    Option.empty
  }
}