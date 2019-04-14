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
package entities.characters.foes.traits

import com.anathema_roguelike.entities.characters.perks.{Buff, PassivePerk, PassthroughPerk}
import com.anathema_roguelike.stats.Stat.CharacterStat
import com.anathema_roguelike.stats.effects.{Calculation, Modifier}

import scala.reflect.runtime.universe._

class StatTrait[T <: CharacterStat : TypeTag](name: String, calculation: Calculation) extends PassthroughPerk[PassivePerk] {

  def getStat: Class[T] = typeTagToClass

  override protected def createPerk: PassivePerk = new PassivePerk(name) {
    override def getEffect: Option[Buff] = {
      new Buff(this, List(new Modifier[T](calculation)))
    }
  }
}