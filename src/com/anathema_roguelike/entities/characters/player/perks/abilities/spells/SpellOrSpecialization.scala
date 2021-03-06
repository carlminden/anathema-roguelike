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

import com.anathema_roguelike.entities.characters.perks.Perk
import com.anathema_roguelike.entities.characters.perks.PerkChoice
import com.anathema_roguelike.entities.characters.player.classes.PlayerClass
import com.anathema_roguelike.main.utilities.Utils

import com.anathema_roguelike.entities.characters.Character
import scala.reflect.runtime.universe._

class SpellOrSpecialization[T <: PlayerClass : TypeTag](spellLevel: Int)
  extends PerkChoice("Level " + spellLevel + " " + Utils.getName(typeTagToClass[T]) + " Spell") {

  private val casterClass = typeTagToClass[T]

  override def getChoices(character: Character): Iterable[_ <: Perk] = {
    val choices = new SpellSpecializationChoice(spellLevel, casterClass).getChoices(character)

    choices ++ Spell.findSpells(spellLevel, casterClass).filter(s => !character.hasPerk(s.getClass))
  }
}