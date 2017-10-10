package com.anathema_roguelike.environment;

public interface HasLocation {
	
	public abstract Location getLocation();
	
	public default Environment getEnvironment() {
		return getLocation().getEnvironment();
	}
	
	public default Point getPosition() {
		return getLocation().getPosition();
	}
	
	public default int getX() {
		return getPosition().getX();
	}
	
	public default int getY() {
		return getPosition().getY();
	}
}
