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

import com.anathema_roguelike.main.utilities.AutoClassToInstanceMap
import com.anathema_roguelike.stats.effects.Effect
import com.anathema_roguelike.stats.effects.EffectCollection
import com.anathema_roguelike.stats.effects.HasEffect
import kotlin.reflect.KClass

open class StatSet<T, S : Stat<out T>>(`object`: T, objectType: Class<T>, statType: Class<S>) {

    @PublishedApi internal val effects: EffectCollection<T, S>
    @PublishedApi internal val stats: AutoClassToInstanceMap<S>

    init {
        effects = EffectCollection(`object`)
        stats = AutoClassToInstanceMap(statType, arrayOf<Class<*>>(objectType), `object`)
    }

    fun applyEffect(effect: Effect<T, out S>) {
        effects.apply(effect)
    }

    fun removeEffectBySource(source: HasEffect<out Effect<out T, out S>>) {
        effects.removeBySource(source)
    }

    inline fun <reified G : S> getStat(): G {
        return stats.get(G::class.java)
    }

    fun <G : S>getStat(stat : Class<G>): G {
        return stats.get(stat)
    }

    protected inline fun <reified B : S> getBaseValue(): Double {
        return getStat<B>().amount
    }

    protected fun <G : S> getBaseValue(stat : Class<G>): Double {
        return getStat(stat).amount
    }

    inline fun <reified G : S> getValue(): Double {
        val base = getBaseValue<G>()
        val modifier = effects.getStatBonus<G>()

        return base + modifier * effects.getStatMultiplier<G>()
    }

    inline fun <G : S> getValue(stat : Class<G>): Double {
        val base = getBaseValue(stat)
        val modifier = effects.getStatBonus(stat)

        return base + modifier * effects.getStatMultiplier(stat)
    }
}
