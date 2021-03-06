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
package entities.characters.player.perks.abilities.spells

import com.anathema_roguelike.entities.characters.perks.PassthroughPerk
import com.anathema_roguelike.entities.characters.perks.actions.ActionPerk
import com.anathema_roguelike.entities.characters.player.classes.PlayerClass
import com.anathema_roguelike.entities.characters.player.perks.abilities.Ability
import com.anathema_roguelike.main.utilities.AutoClassToInstanceMap

abstract class Spell[T <: ActionPerk[_]](spellLevel: Int, casterClass: Class[_ <: PlayerClass]) extends PassthroughPerk[T] with Ability {
  def getSpellLevel: Int = spellLevel

  def getCasterClass: Class[_ <: PlayerClass] = casterClass
}

object Spell {

  private val spells: AutoClassToInstanceMap[Spell[_]] = new AutoClassToInstanceMap[Spell[_]]

  def findSpells(spellLevel: Int, casterClass: Class[_ <: PlayerClass]): Iterable[Spell[_]] = {
    spells.getValues.filter(s => (s.getSpellLevel == spellLevel) && (s.getCasterClass == casterClass))
  }
}
