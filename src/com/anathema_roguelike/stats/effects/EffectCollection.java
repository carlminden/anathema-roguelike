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
package com.anathema_roguelike.stats.effects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;

import com.anathema_roguelike.actors.TimeElapsedEvent;
import com.anathema_roguelike.main.Game;
import com.anathema_roguelike.stats.Stat;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Iterables;
import com.google.common.eventbus.Subscribe;

public class EffectCollection<T, S extends Stat<? extends T>> {
	
	private HashBiMap<HasEffect<? extends Effect<? extends T, ?>>, Effect<? extends T, ?>> sourcedEffects = HashBiMap.create();
	private ArrayList<Effect<? extends T, ?>> unsourcedEffects = new ArrayList<>();

	private T affected;
	
	public EffectCollection(T affected) {
		Game.getInstance().getEventBus().register(this);
		
		this.affected = affected;
	}
	
	public double getStatBonus(Class<? extends S> stat) {
		
		double bonus = 0;
		
		for(Effect<? extends T, ?> effect : getEffects()) {
			bonus += effect.getAdditiveBonus(stat); 
		}
		
		return bonus;
	}
	
	public double getStatMultiplier(Class<? extends S> stat) {
		
		double bonus = 1;

		for(Effect<? extends T, ?> effect : getEffects()) {
			bonus *= effect.getMultiplier(stat);
		}

		return bonus;
	}
	
	@Subscribe
	public void handleSegmentElapsedEvent(TimeElapsedEvent event) {
		elapse(event.getElapsedTime());
		removeExpired();
	}

	public Iterable<Effect<? extends T, ?>> getEffects() {
		return Iterables.concat(sourcedEffects.values(), unsourcedEffects);
	}

	public void apply(Effect<T, ?> effect) {
 		effect.getSource().ifPresentOrElse(
 				src -> sourcedEffects.forcePut(src, effect),
				() -> unsourcedEffects.add(effect));

		effect.applyTo(affected);
	}
	
	public void removeBySource(HasEffect<? extends Effect<? extends T, ?>> source) {
		
		Effect<? extends T,?> effect = sourcedEffects.get(source);
		if(effect != null) {
			sourcedEffects.remove(effect);
			effect.remove();
		}
	}
	
	public void elapse(double duration) {
		for(Effect<? extends T, ?> effect : getEffects()) {
			effect.getDuration().elapse(duration);
		}
	}
	
	public void removeExpired() {
		sourcedEffects.entrySet().removeIf(entry -> {
			if(entry.getValue().getDuration().isExpired()) {
				entry.getValue().remove();
				return true;
			} else {
				return false;
			}
		});

		unsourcedEffects.removeIf(entry -> {
			if(entry.getDuration().isExpired()) {
				entry.remove();
				return true;
			} else {
				return false;
			}
		});
	}
}
