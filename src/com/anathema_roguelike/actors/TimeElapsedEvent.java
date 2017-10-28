package com.anathema_roguelike.actors;

public class TimeElapsedEvent {
	
	private double elapsedTime;
	
	public TimeElapsedEvent(double elapsedTime) {
		this.elapsedTime = elapsedTime;
	}
	
	public double getElapsedTime() {
		return elapsedTime;
	}
}
