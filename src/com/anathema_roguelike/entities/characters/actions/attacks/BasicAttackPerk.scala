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
package entities.characters.actions.attacks

import com.anathema_roguelike.entities.characters.perks.actions.OffensiveTargetedPerk
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.SingleTargeted
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.ranges.PrimaryWeaponRange
import com.anathema_roguelike.entities.characters.Character

class BasicAttackPerk() extends OffensiveTargetedPerk(
  "Basic Attack",
  new PrimaryWeaponRange[Character],
  new SingleTargeted[Character]) {

    override protected def createAction = new BasicAttack(getCharacter)
}