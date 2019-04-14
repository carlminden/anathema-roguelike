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
package com.anathema_roguelike.stats.characterstats.resources;

import java.util.Optional;

public class Heal<T extends Resource> extends ResourceModification<T> {

	public Heal(Optional<Character> initiator, Optional<HasEffect<? extends Effect<Character, ?>>> source, Class<T> resource, int amount) {
		super(initiator, source, resource, amount);
	}
	
	public Heal(Optional<Character> initiator, Optional<HasEffect<? extends Effect<Character, ?>>> source, Class<T> resource, final Calculation calculation) {
		super(initiator, source, resource, calculation);
	}
}
