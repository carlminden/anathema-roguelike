package com.anathema_roguelike.stats;

import java.util.Optional;

import com.anathema_roguelike.stats.effects.Effect;
import com.anathema_roguelike.stats.effects.HasEffect;

public interface HasStats<T, S extends Stat<? extends T>> {
	public abstract StatSet<T, S> getStatSet();
	
	public default <R extends S> R getStat(Class<R> stat) {
		return getStatSet().getStat(stat);
	}
	
	public default double getStatAmount(Class<? extends S> stat) {
		return getStatSet().getValue(stat);
	}
	
	public default void applyEffect(Optional<? extends Effect<T, ? extends S>> effect) {
		effect.ifPresent(e -> getStatSet().applyEffect(e));
	}
	
	public default void removeEffectBySource(HasEffect<? extends Effect<? extends T, ? extends S>> source) {
		getStatSet().removeEffectBySource(source);
	}
}
