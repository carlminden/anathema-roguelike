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
package com.anathema_roguelike.stats.characterstats.resources

import java.util.Optional

import com.anathema_roguelike.actors.Duration
import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.stats.effects.Calculation
import com.anathema_roguelike.stats.effects.Effect
import com.anathema_roguelike.stats.effects.FixedCalculation
import com.anathema_roguelike.stats.effects.HasEffect

open class ResourceModification<T : Resource> : Effect<Character, T> {

    private var calculation: Calculation
    private var resource: Class<out Resource>
    var initiator: Optional<Character> = Optional.empty()
        private set

    constructor(initiator: Optional<Character>, source: Optional<HasEffect<out Effect<Character, *>>>, resource: Class<out Resource>, amount: Int) : super(source.orElse(null), Duration.instant()) {

        this.resource = resource
        this.calculation = FixedCalculation(amount.toDouble())
        this.initiator = initiator
    }

    constructor(initiator: Optional<Character>, source: Optional<HasEffect<out Effect<Character, *>>>, resource: Class<out Resource>, calculation: Calculation) : super(source.orElse(null), Duration.instant()) {
        this.resource = resource
        this.calculation = calculation
        this.initiator = initiator
    }

    override fun onApplicationCallback(character: Character) {
        super.onApplicationCallback(character)

        character.modifyResource(initiator, Optional.of(source), resource, calculation!!.get().toInt())
    }
}
