package com.anathema_roguelike.stats.effects;

import java.util.ArrayList;
import java.util.Arrays;

import com.anathema_roguelike.stats.Stat;

public abstract class Effect<T, S extends Stat<? extends T>> {
	
	private HasEffect<? extends Effect<T, ? extends S>> source;
	private Duration duration;
	private ArrayList<Modifier<T, ? extends S>> modifiers;
	private T target;
	
	@SafeVarargs
	public Effect(HasEffect<? extends Effect<T, S>> source, Modifier<T, ? extends S>... modifiers) {
		this.source = source;
		this.duration = new FixedDuration(Duration.PERMANENT);
		this.modifiers = new ArrayList<Modifier<T, ? extends S>>(Arrays.asList(modifiers));
	}
	
	@SafeVarargs
	public Effect(HasEffect<? extends Effect<T, S>> source, Duration duration, Modifier<T, ? extends S>... modifiers) {
		this.source = source;
		this.duration = Duration.copy(duration);
		this.modifiers = new ArrayList<Modifier<T, ? extends S>>(Arrays.asList(modifiers));
	}
	
	public HasEffect<? extends Effect<T, ? extends S>> getSource() {
		return source;
	}
	
	private ArrayList<Modifier<T, ? extends S>> getModifiers() {
		return modifiers;
	}
	
	public void addModifier(Modifier<T, ? extends S> modifier) {
		modifiers.add(modifier);
	}
	
	public double getAdditiveBonus(Class<? extends Stat<?>> stat) {
		
		double ret = 0;
		
		for(Modifier<T, ? extends S> modifier : getModifiers()) {
			if(modifier.getAffectedStat().equals(stat)) {
				ret = ret + modifier.getAdditiveAmount(getTarget());
			}
		}
		
		return ret;
	}
	
	public double getMultiplier(Class<? extends Stat<?>> stat) {
		
		double ret = 1.0;
		
		for(Modifier<T, ? extends S> modifier : getModifiers()) {
			if(modifier.getAffectedStat().equals(stat)) {
				ret *= modifier.getMultiplier(getTarget());
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
		builder.append("Duration: " + duration.getRemaining());
		modifiers.forEach(m -> builder.append(" Modifier: " + m.getAffectedStat().getSimpleName()
				+ " +" + m.getAdditiveAmount(getTarget()) + " *" + m.getMultiplier(getTarget())));
		
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
