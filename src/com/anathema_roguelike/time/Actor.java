package com.anathema_roguelike.time;

public interface Actor {
	
	public Duration getDuration();
	public Energy getEnergy();
	public Action<?> getNextAction();
	
	public default void energize() {
		getEnergy().energize();
	}
	
	public default double getEnergyLevel() {
		return getEnergy().getEnergyLevel();
	}
	
	public default void act() {
		getNextAction().take();
	}
}
