package com.anathema_roguelike.stats.effects;

import com.anathema_roguelike.stats.Stat;

public abstract class Effect<T, S extends Stat<? extends T>> extends ModifierGroup<S> {

	@SafeVarargs
	public Effect(HasEffect<? extends Effect<T, S>> source, Modifier<? extends S>... modifiers) {
		super(source, modifiers);
		// TODO Auto-generated constructor stub
	}
	
	@SafeVarargs
	public Effect(HasEffect<? extends Effect<T, S>> source, Duration duration, Modifier<? extends S>... modifiers) {
		super(source, duration, modifiers);
		// TODO Auto-generated constructor stub
	}
	
	public void onApplicationCallback(T callback) {};
	public void onExpirationCallback(T callback) {};
}
