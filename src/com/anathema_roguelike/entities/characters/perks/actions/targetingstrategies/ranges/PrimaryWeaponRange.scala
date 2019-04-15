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

import com.anathema_roguelike.entities.characters.inventory.PrimaryWeapon
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints.{LineOfEffect, LineOfSight, TargetConstraint}
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.shapes.{Circle, Shape, Square}
import com.anathema_roguelike.stats.itemstats.WeaponRange
import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.stats.effects.FixedCalculation

import scala.reflect.ClassTag
import scala.reflect.runtime.universe._

class PrimaryWeaponRange[T <: Targetable : TypeTag : ClassTag](constraints: TargetConstraint[T, Character]*) extends Range[T](constraints:_*) {

  addConstraint(new LineOfSight[T])
  addConstraint(new LineOfEffect[T])

  protected def getShape (character: Character): Shape = {
    val range: Double = character.getInventory.getSlot(classOf[PrimaryWeapon]).getEquippedItem.get.getStatAmount[WeaponRange]

    if (range == 1) {
      new Square(character, new FixedCalculation (1))
    } else {
      new Circle(character, new FixedCalculation (range))
    }
  }
}
