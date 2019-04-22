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

import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.entities.characters.actions.costs.{ActionCosts, ResourceCost, StimulusCost}
import com.anathema_roguelike.entities.characters.stimuli.AttenuatedSound
import com.anathema_roguelike.stats.characterstats.resources.{CurrentEndurance, RecentMotion}

class BasicAttack(attacker: Character) extends WeaponAttack(attacker, new ActionCosts()) {
    addCost(new ResourceCost[RecentMotion](attacker, 50))
    addCost(new StimulusCost[AttenuatedSound](attacker,  () => 30.0))
    addCost(new ResourceCost[CurrentEndurance](attacker, -30))
}
