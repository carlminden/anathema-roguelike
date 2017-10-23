package com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies;

import com.anathema_roguelike.stats.Stat;
import com.anathema_roguelike.stats.effects.Effect;
import com.anathema_roguelike.stats.effects.HasEffect;

public abstract class TargetEffect<T extends Targetable, S extends Stat<? extends T>> implements HasEffect<Effect<T, S>> {
	
	private Class<? extends T> targetType;
	private String name;
	
	public TargetEffect(Class<? extends T> targetType, String name) {
		this.targetType = targetType;
		this.name = name;
	}
	
	public Class<? extends T> getTargetType() {
		return targetType;
	}
	
	public void applyTo(T target) {
		getEffect().ifPresent(e -> e.applyTo(target));
	}
	
	@Override
	public String toString() {
		return name;
	}
}
