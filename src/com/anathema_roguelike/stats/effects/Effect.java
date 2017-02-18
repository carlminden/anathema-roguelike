package com.anathema_roguelike.stats.effects;

import java.util.ArrayList;
import java.util.Arrays;

import com.anathema_roguelike.stats.Stat;

public abstract class Effect<T, S extends Stat<? extends T>> {
	
	private HasEffect<? extends Effect<T, ? extends S>> source;
	private Duration duration;
	private ArrayList<Modifier<? extends S>> modifiers;
	
	@SafeVarargs
	public Effect(HasEffect<? extends Effect<T, S>> source, Modifier<? extends S>... modifiers) {
		this.source = source;
		this.duration = new FixedDuration(Duration.PERMANENT);
		this.modifiers = new ArrayList<Modifier<? extends S>>(Arrays.asList(modifiers));
	}
	
	@SafeVarargs
	public Effect(HasEffect<? extends Effect<T, S>> source, Duration duration, Modifier<? extends S>... modifiers) {
		this.source = source;
		this.duration = Duration.copy(duration);
		this.modifiers = new ArrayList<Modifier<? extends S>>(Arrays.asList(modifiers));
	}
	
	public HasEffect<? extends Effect<T, ? extends S>> getSource() {
		return source;
	}
	
	public ArrayList<Modifier<? extends S>> getModifiers() {
		return modifiers;
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
				+ " +" + m.getStaticAmount() + " *" + m.getMultiplier()));
		
		return builder.toString();
	}
	
	public void onApplicationCallback(T callback) {};
	public void onExpirationCallback(T callback) {};
}
