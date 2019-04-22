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
package stats.characterstats.resources

import com.anathema_roguelike.main.utilities.Utils
import com.anathema_roguelike.stats.effects.Effect
import com.anathema_roguelike.stats.effects.HasEffect
import java.util.Optional
import com.anathema_roguelike.entities.characters.Character

abstract class BoundedResource(character: Character, initiallyFull: Boolean) extends Resource(character) {
  private var initialized = false

  override def getAmount: Double = {
    if(!initialized) {
      initialized = true
      if(initiallyFull) set(Option.empty, Option.empty, getMaximum)
    }
    super.getAmount
  }

  override def set(initiator: Option[Character], source: Option[HasEffect[Effect[Character, _]]], amount: Int): Unit = {
    super.set(initiator, source, Utils.clamp(amount, 0, getMaximum))
  }

  def reset(): Unit = set(Option.empty, Option.empty, getMaximum)

  def getMaximum: Int
}