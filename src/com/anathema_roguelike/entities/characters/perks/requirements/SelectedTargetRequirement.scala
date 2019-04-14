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
package entities.characters.perks.requirements

import java.util.function.Supplier

import com.anathema_roguelike.entities.characters.perks.actions.GenericTargetedPerk
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.ranges.Range
import com.anathema_roguelike.main.utilities.Utils

abstract class SelectedTargetRequirement[TargetType <: Targetable, OriginType <: Targetable](
    perk: GenericTargetedPerk[TargetType, OriginType],
    var range: Range[OriginType]) extends PerkRequirement(perk) {

  protected def targeted(target: OriginType): Unit

  override def getCondition = () => range.getTarget(getPerk.getCharacter).foreach(t => targeted(t)).isDefined

  override def getRequirementUnmetMessage: String = Utils.getName(getPerk) + " activation canceled, no Target was selected"
}