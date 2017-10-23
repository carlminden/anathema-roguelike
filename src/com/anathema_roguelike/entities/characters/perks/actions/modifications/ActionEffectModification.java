package com.anathema_roguelike.entities.characters.perks.actions.modifications;

import java.util.Optional;

import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.TargetEffect;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable;
import com.anathema_roguelike.stats.Stat;
import com.anathema_roguelike.stats.effects.Effect;

public abstract class ActionEffectModification<T extends Targetable, S extends Stat<? extends T>> {

	private Class<T> targetType;
	
	public ActionEffectModification(Class<T> targetType) {
		this.targetType = targetType;
	}
	
	protected abstract Effect<T, S> modify(Effect<T, S> effect);
	
	public TargetEffect<T, S> modify(TargetEffect<T, S> modified) {
		return new TargetEffect<T, S>(modified.getTargetType(), modified.toString()) {
			@Override
			public Optional<Effect<T, S>> getEffect() {
				if(modified.getEffect().isPresent()) {
					return Optional.of(modify(modified.getEffect().get()));
				} else {
					return modified.getEffect();
				}
			}
		};
	}
	
	public Class<T> getType() {
		return targetType;
	}
}
