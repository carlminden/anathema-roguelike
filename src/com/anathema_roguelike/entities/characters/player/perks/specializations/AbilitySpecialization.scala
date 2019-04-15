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
package entities.characters.player.perks.specializations

import com.anathema_roguelike.entities.characters.perks.Perk
import com.anathema_roguelike.entities.characters.player.perks.abilities.Ability
import com.anathema_roguelike.main.utilities.Utils
import scala.reflect.runtime.universe._
import com.anathema_roguelike.entities.characters.Character

class AbilitySpecialization[T <: Ability : TypeTag](amount: Int = 1) extends Perk(Utils.getName(typeTagToClass[T]) + " Specialization") {

  override def grant(character: Character): Unit = {
    character.specialize[T](amount)
    super.grant(character)
  }

  override def remove(character: Character): Unit = {
    character.specialize[T](amount * -1)
    super.remove(character)
  }
}