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

import com.anathema_roguelike.characters.events.TurnEvent;
import com.anathema_roguelike.main.Game;
import com.anathema_roguelike.stats.Stat;
import com.google.common.collect.HashBiMap;
import com.google.common.eventbus.Subscribe;

public class EffectCollection<T, S extends Stat<? extends T>> {
	
	private HashBiMap<HasEffect<? extends Effect<? extends T, ? extends S>>, Effect<? extends T, ? extends S>> effects = HashBiMap.create();
	
	public EffectCollection() {
		Game.getInstance().getEventBus().register(this);
	}
	
	public double getStatBonus(Class<? extends S> stat) {
		
		double bonus = 0;
		
		for(Effect<? extends T, ? extends S> effect : effects.values()) {
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
		
		for(Effect<? extends T, ? extends S> effect : effects.values()) {
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
		return effects.values();
	}
	
	public void apply(Effect<? extends T, ? extends S> effect) {
		effect.getDuration().activate();
		
		effects.forcePut(effect.getSource(), effect);
	}
	
	public void removeBySource(HasEffect<? extends Effect<? extends T, ? extends S>> source) {
		effects.inverse().remove(source);
	}
	
	public void decrement() {
		for(Effect<? extends T, ? extends S> effect : effects.values()) {
			effect.getDuration().decrement();
		}
	}
	
	public void removeExpired() {
		effects.entrySet().removeIf(entry -> entry.getValue().getDuration().isExpired());
	}
}
