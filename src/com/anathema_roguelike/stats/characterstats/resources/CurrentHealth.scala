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

import com.anathema_roguelike.stats.characterstats.secondarystats.Health
import com.anathema_roguelike.stats.effects.Effect
import com.anathema_roguelike.stats.effects.HasEffect
import com.anathema_roguelike.entities.characters.Character

class CurrentHealth(character: Character) extends BoundedResource(character, true) {
  override def modify(initiator: Option[Character], source: Option[HasEffect[Effect[Character, _]]], amount: Int): Unit = {
    if(amount < 0) {

      val character = getObject
      val remainder = Math.min(0, amount)
      super.modify(initiator, source, remainder)

      if(getAmount <= 0 && character.isAlive) {
        character.onDeath()
      }
    } else if(amount > 0) {
      super.modify(initiator, source, amount)
    }
  }

  override def getMaximum: Int = getObject.getStatAmount[Health].toInt

  override def getAmount: Double = super.getAmount
}