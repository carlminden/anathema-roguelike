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
package com.anathema_roguelike.stats;

import com.anathema_roguelike.main.utilities.AutoClassToInstanceMap;
import com.anathema_roguelike.stats.effects.Effect;
import com.anathema_roguelike.stats.effects.EffectCollection;
import com.anathema_roguelike.stats.effects.HasEffect;

public class StatSet<T, S extends Stat<? extends T>> {
	
	private EffectCollection<T, S> effects;
	private AutoClassToInstanceMap<S> stats;
	
	public StatSet(T object, Class<T> objectType, Class<S> statType) {
		effects = new EffectCollection<T, S>();
		stats = new AutoClassToInstanceMap<>(statType, new Class[] { objectType }, object);
	}
	
	protected EffectCollection<T, S> getEffects() {
		return effects;
	}
	
	public void applyEffect(Effect<? extends T, ? extends S> effect) {
		effects.apply(effect);
	}
	
	public void removeEffectBySource(HasEffect<? extends Effect<? extends T, ? extends S>> source) {
		effects.removeBySource(source);
	}
	
	public <G extends S> G getStat(Class<G> stat) {
		return stats.get(stat);
	};
	
	private double getBaseValue(Class<? extends S> stat) {
		return getStat(stat).getAmount();
	}
	
	public double getValue(Class<? extends S> stat) {
		double base = getBaseValue(stat);
		double modifier = effects.getStatBonus(stat);
		
		return base + modifier * effects.getStatMultiplier(stat);
	}
}