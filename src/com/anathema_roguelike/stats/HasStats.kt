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
package com.anathema_roguelike.stats

import java.util.Optional

import com.anathema_roguelike.stats.effects.Effect
import com.anathema_roguelike.stats.effects.HasEffect

interface HasStats<T, S : Stat<out T>> {
    val statSet: StatSet<T, S>

    fun <R : S> getStat(stat: Class<R>): R {
        return statSet.getStat(stat)
    }

    fun getStatAmount(stat: Class<out S>): Double {
        return statSet.getValue(stat)
    }

    @JvmDefault
    fun applyEffect(effect: Optional<out Effect<T, out S>>) {
        effect.ifPresent { e -> statSet.applyEffect(e) }
    }

    fun removeEffectBySource(source: HasEffect<out Effect<out T, out S>>) {
        statSet.removeEffectBySource(source)
    }
}
