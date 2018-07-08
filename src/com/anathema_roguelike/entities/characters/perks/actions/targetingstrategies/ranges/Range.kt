/*******************************************************************************
 * Copyright (C) 2017 Carl Minden
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
 * along with this program.  If not, see <http:></http:>//www.gnu.org/licenses/>.
 */
package com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.ranges

import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.TargetFilter
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints.TargetConstraint
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.shapes.Shape
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.GetTargetInterface
import com.anathema_roguelike.main.utilities.Utils

abstract class Range<T : Targetable> @SafeVarargs
constructor(targetType: Class<T>, vararg constraints: TargetConstraint<T, Character>) : TargetFilter<T, Character>(targetType, *constraints) {

    protected abstract fun getShape(character: Character): Shape

    override fun getTargets(character: Character): Collection<T> {
        return getTargetsInShape(getShape(character), character.environment, character)
    }

    open fun getTarget(character: Character): T {
        val validTargets = getTargetsInShape(getShape(character), character.environment, character)

        if (validTargets.size == 1) {
            return validTargets.iterator().next()
        } else {
            val instructions = "Select a " + Utils.getName(targetType) + " within " + Utils.getName(this)

            return GetTargetInterface(validTargets, instructions).run()
        }
    }
}
