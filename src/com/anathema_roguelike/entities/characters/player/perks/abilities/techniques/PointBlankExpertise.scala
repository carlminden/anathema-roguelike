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
package entities.characters.player.perks.abilities.techniques

import com.anathema_roguelike.entities.characters.actions.attacks.WeaponAttack
import com.anathema_roguelike.entities.characters.perks.PassthroughPerk
import com.anathema_roguelike.entities.characters.player.perks.abilities.Ability

//TODO: need to reimplement modification abilities
class PointBlankExpertise extends Technique[Nothing] with Ability {
  override protected def createPerk: Nothing = ??? // TODO Auto-generated method stub
}