package com.anathema_roguelike.stats;

import com.anathema_roguelike.stats.effects.Effect;

public interface HasStats<T, S extends Stat<? extends T>> {
	public abstract StatSet<T, S> getStatSet();
	
	public default <R extends S> R getStat(Class<R> stat) {
		return getStatSet().getStat(stat);
	}
	
	public default <R extends S> double getStatAmount(Class<R> stat) {
		return getStatSet().getValue(stat);
	}
	
	public default void applyEffect(Effect<? extends T, ? extends S> effect) {
		getStatSet().applyEffect(effect);
	}
	
	public default void removeEffect(Effect<? extends T, ? extends S> effect) {
		getStatSet().removeEffect(effect);
	}
}
