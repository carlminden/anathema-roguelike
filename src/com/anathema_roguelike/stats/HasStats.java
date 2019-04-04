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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.anathema_roguelike.stats;

import java.util.Optional;

import com.anathema_roguelike.stats.effects.Effect;
import com.anathema_roguelike.stats.effects.HasEffect;

public interface HasStats<T, S extends Stat<? extends T>> {
	StatSet<T, S> getStatSet();
	
	default <R extends S> R getStat(Class<R> stat) {
		return getStatSet().getStat(stat);
	}
	
	default double getStatAmount(Class<? extends S> stat) {
		return getStatSet().getValue(stat);
	}
	
	default void applyEffect(Optional<? extends Effect<T, ? extends S>> effect) {
		effect.ifPresent(e -> getStatSet().applyEffect(e));
	}
	
	default void removeEffectBySource(HasEffect<? extends Effect<? extends T, ? extends S>> source) {
		getStatSet().removeEffectBySource(source);
	}
}
