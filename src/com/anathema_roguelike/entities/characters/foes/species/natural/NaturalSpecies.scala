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
package entities.characters.foes.species.natural

import com.anathema_roguelike.entities.characters.foes.Foe
import com.anathema_roguelike.entities.characters.foes.corruptions.Corruption
import com.anathema_roguelike.entities.characters.foes.roles.Role
import com.anathema_roguelike.entities.characters.foes.traits.Trait
import com.anathema_roguelike.environment.{HasLocation, Location}

abstract class NaturalSpecies(location: HasLocation, role: Role, corruption: Corruption, traits: Trait[_]*)
  extends Foe(location, role, corruption, traits:_*) {
}