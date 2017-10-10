package com.anathema_roguelike.characters.perks.targetingstrategies;

public abstract class TargetConsumer<T extends Targetable> {
	
	Class<T> targetType;
	
	public TargetConsumer(Class<T> targetType) {
		this.targetType = targetType;
	}
	
	public abstract void consume(T target);
	
	public Class<T> getTargetType() {
		return targetType;
	}
}
