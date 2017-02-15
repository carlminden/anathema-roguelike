/*******************************************************************************
 * This file is part of AnathemaRL.
 *
 *     AnathemaRL is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     AnathemaRL is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with AnathemaRL.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package com.anathema_roguelike.stats.effects;

import java.util.Collection;
import java.util.HashSet;

import com.anathema_roguelike.characters.events.TurnEvent;
import com.anathema_roguelike.stats.Stat;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class EffectCollection<T, S extends Stat<? extends T>> {
	
	private HashSet<Effect<? extends T, ? extends S>> effects = new HashSet<>();
	
	public EffectCollection(EventBus eventBus) {
		eventBus.register(this);
	}
	
	public double getStatBonus(Class<? extends S> stat) {
		
		double bonus = 0;
		
		for(Effect<? extends T, ? extends S> effect : effects) {
			for(Modifier<? extends S> modifier : effect.getModifiers()) {
				if(modifier.getAffectedStat() == stat) {
					 bonus = bonus + modifier.getStaticAmount();
				}
			}
		}
		
		return bonus;
	}
	
	public double getStatMultiplier(Class<? extends S> stat) {
		
		double bonus = 1;
		
		for(Effect<? extends T, ? extends S> effect : effects) {
			for(Modifier<? extends S> modifier : effect.getModifiers()) {
				if(modifier.getAffectedStat() == stat) {
					bonus *= modifier.getMultiplier();
				}
			}
		}
		
		return bonus;
	}
	
	@Subscribe
	public void handleTurnEvent(TurnEvent event) {
		decrement();
		removeExpired();
	}
	
	public Collection<? extends Effect<? extends T, ? extends S>> getEffects() {
		return effects;
	}
	
	public void apply(Effect<? extends T, ? extends S> effect) {
		effect.getDuration().activate();
		
		effects.add(effect);
	}
	
	public void remove(Effect<? extends T, ? extends S> effect) {
		effects.remove(effect);
	}
	
	public void addStuff(Effect<T, ? extends S> effect) {
		if(!effect.getDuration().isExpired()) {
			effects.add(effect);
		}
	}
	
	public void decrement() {
		for(Effect<? extends T, ? extends S> effect : effects) {
			effect.getDuration().decrement();
		}
	}
	
	public void removeExpired() {
		
		HashSet<Effect<? extends T, ? extends S>> expired = new HashSet<>();
		
		for(Effect<? extends T, ? extends S> effect : effects) {
			if(effect.getDuration().isExpired()) {
				expired.add(effect);
			}
		}
		
		for(Effect<? extends T, ? extends S> modifier : expired) {
			remove(modifier);
		}
	}
}
