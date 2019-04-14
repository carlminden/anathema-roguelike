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
package entities.characters.perks.actions.targetingstrategies.ranges

import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints.TargetConstraint
import com.anathema_roguelike.entities.items.Item
import com.anathema_roguelike.stats.characterstats.attributes.Strength
import com.anathema_roguelike.stats.characterstats.masteries.ThrowingWeaponMastery
import com.anathema_roguelike.stats.itemstats.Weight
import scala.reflect.runtime.universe._
import com.anathema_roguelike.entities.characters.Character

class ThrowingRange[T <: Targetable : TypeTag](var item: Item, val constraints: TargetConstraint[T, Character]*)
  extends CircularRange[T] (constraints:_*) {

  protected def getRadius (character: Character): Double = {
    val throwingMastery: Double = character.getStatAmount[ThrowingWeaponMastery]
    val strength: Double = character.getStatAmount[Strength]
    val weight: Double = item.getStatAmount[Weight]

    (2 + throwingMastery) * (1 + (2 * strength - weight) / 30)
  }
}