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
package entities.characters.perks
import com.anathema_roguelike.entities.characters.Character

abstract class PassthroughPerk[T <: Perk]() extends Perk("") {

  private var perk: Option[T] = None

  protected def createPerk: T

  override def grant(character: Character): Unit = {
    super.grant(character)
    getPerk.grant(character)
  }

  override def remove(character: Character): Unit = {
    super.remove(character)
    getPerk.remove(character)
  }

  def getPerk: T = {

    perk = perk match {
      case p: Some[T] => p
      case None => createPerk
    }

    perk.get
  }

  override def toString: String = getPerk.toString
}