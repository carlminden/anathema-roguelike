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
import java.util.Arrays;

import com.anathema_roguelike.actors.Duration;
import com.anathema_roguelike.stats.Stat;

public class Effect<T, S extends Stat<? extends T>> {

	//TODO: source should be Optional
	private HasEffect<? extends Effect<T, ?>> source;
	private Duration duration;
	private ArrayList<Modifier<T, ?>> modifiers;
	private T target;
	
	@SafeVarargs
	public Effect(HasEffect<? extends Effect<T, ?>> source, Modifier<T, ?>... modifiers) {
		this.source = source;
		this.duration = Duration.permanent();
		this.modifiers = new ArrayList<>(Arrays.asList(modifiers));
	}
	
	@SafeVarargs
	public Effect(HasEffect<? extends Effect<T, ?>> source, Duration duration, Modifier<T, ?>... modifiers) {
		this.source = source;
		this.duration = Duration.copy(duration);
		this.modifiers = new ArrayList<>(Arrays.asList(modifiers));
	}
	
	public HasEffect<? extends Effect<T, ?>> getSource() {
		return source;
	}
	
	public ArrayList<Modifier<T, ?>> getModifiers() {
		return modifiers;
	}
	
	public void addModifier(Modifier<T, ? extends S> modifier) {
		modifiers.add(modifier);
	}
	
	public double getAdditiveBonus(Class<? extends Stat<?>> stat) {
		
		double ret = 0;
		
		for(Modifier<T, ?> modifier : getModifiers()) {
			if(modifier.getAffectedStat().equals(stat)) {
				ret = ret + modifier.getAdditiveAmount();
			}
		}
		
		return ret;
	}
	
	public double getMultiplier(Class<? extends Stat<?>> stat) {
		
		double ret = 1.0;
		
		for(Modifier<T, ?> modifier : getModifiers()) {
			if(modifier.getAffectedStat().equals(stat)) {
				ret *= modifier.getMultiplier();
			}
		}
		
		return ret;
	}
	
	public Duration getDuration() {
		return duration;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("Effect: ");
		builder.append("Duration: ").append(duration.getRemaining());
		modifiers.forEach(m -> builder.append(" Modifier: " + m.getAffectedStat().getSimpleName()
				+ " +" + m.getAdditiveAmount() + " *" + m.getMultiplier()));
		
		return builder.toString();
	}
	
	final public void applyTo(T target) {
		
		this.target = target;
		
		onApplicationCallback(target);
	}
	
	final public void remove() {
		
		onExpirationCallback(getTarget());
		
		this.target = null;
	}
	
	public void onApplicationCallback(T target) {};
	public void onExpirationCallback(T target) {};
	
	public T getTarget() {
		return target;
	};
}
