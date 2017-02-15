package com.anathema_roguelike.stats;

public abstract class Stat<T> {
	
	private T object;
	
	public Stat(T object) {
		this.object = object;
	}
	
	public T getObject() {
		return object;
	}
	
	public abstract double getAmount();
}
