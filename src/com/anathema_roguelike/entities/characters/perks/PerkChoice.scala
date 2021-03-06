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

import com.anathema_roguelike.main.ui.uielements.interactiveuielements.SelectionScreen
import java.util
import com.anathema_roguelike.entities.characters.Character

abstract class PerkChoice(var title: String) extends Perk(title) {

  def getChoices(character: Character): Iterable[Perk]

  override def grant(character: Character): Unit = {
    val choice = new SelectionScreen[Perk](title, getChoices(character).toArray, cancellable = false).run
    choice.get.grant(character)
  }

  override def remove(character: Character) = throw new RuntimeException("Perk Choices should never be granted and therefore shouldnt be able to be removed")

  override def getCharacter = throw new RuntimeException("Perk Choices should never be granted and therefore will never have a Character set")
}