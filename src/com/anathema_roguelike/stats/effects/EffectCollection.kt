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
package com.anathema_roguelike.stats.effects

import com.anathema_roguelike.actors.TimeElapsedEvent
import com.anathema_roguelike.main.Game
import com.anathema_roguelike.stats.Stat
import com.google.common.collect.HashBiMap
import com.google.common.eventbus.Subscribe
import kotlin.reflect.KClass

class EffectCollection<T, S : Stat<out T>>(private val affected: T) {

    @PublishedApi internal val effects = HashBiMap.create<HasEffect<out Effect<T, *>>, Effect<out T, *>>()

    init {
        Game.getInstance().eventBus.register(this)
    }

    inline fun <G : S> getStatBonus(stat : Class<G>): Double {

        var bonus = 0.0

        for (effect in effects.values) {
            bonus += effect.getAdditiveBonus(stat)
        }

        return bonus
    }

    inline fun <reified G : S> getStatBonus(): Double {
        return getStatBonus(G::class.java)
    }

    inline fun <G : S> getStatMultiplier(stat : Class<G>): Double {

        var bonus = 1.0

        for (effect in effects.values) {
            bonus *= effect.getMultiplier(stat)
        }

        return bonus
    }

    inline fun <reified G : S> getStatMultiplier(): Double {
        return getStatBonus(G::class.java)
    }

    @Subscribe
    fun handleSegmentElapsedEvent(event: TimeElapsedEvent) {
        elapse(event.elapsedTime)
        removeExpired()
    }

    fun getEffects(): Collection<Effect<out T, *>> {
        return effects.values
    }

    fun apply(effect: Effect<T, *>) {

        effects.forcePut(effect.getSource(), effect)

        effect.applyTo(affected)
    }

    fun removeBySource(source: HasEffect<out Effect<out T, *>>) {

        val effect = effects[source]!!
        if (effect != null) {
            effects.remove(source)
            effect.remove()
        }
    }

    fun elapse(duration: Double) {
        for (effect in effects.values) {
            effect.getDuration().elapse(duration)
        }
    }

    fun removeExpired() {
        effects.entries.removeIf { entry ->
            return@removeIf when(entry.value.getDuration().isExpired) {
                true -> {
                    entry.value.remove()
                    return@removeIf true
                }
                false -> false
            }
        }
    }
}
