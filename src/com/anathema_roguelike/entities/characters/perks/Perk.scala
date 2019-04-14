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
import com.anathema_roguelike.entities.characters.perks.requirements.PerkRequirement


abstract class Perk(name: String, initialRequirements: PerkRequirement*) {

  var character: Option[Character] = Option.empty
  var requirements: List[PerkRequirement] = initialRequirements.toList

  override def toString: String = name

  def grant(character: Character): Unit = {
    this.character = character

    character.addPerk(this)
    character.getEventBus.register(this)
  }

  def remove(character: Character): Unit = {
    character.removePerk(this)
    character.getEventBus.unregister(this)

    this.character = Option.empty
  }

  def getCharacter: Character = character.get

  protected def setCharacter(character: Character): Unit = {
    this.character = character
  }

  def requirementsMet(): Boolean = requirements.forall(r => r.getCondition())

  def printUnmetConditionMessages(): Unit = {
    for (requirement <- requirements) {
      if (!requirement.getCondition()) requirement.printUnmetConditionMessage()
    }
  }

  def addRequirement(requirement: PerkRequirement): Unit = {
    requirements +:= requirement
  }

  def getPerkRequirements: Iterable[PerkRequirement] = requirements
}
