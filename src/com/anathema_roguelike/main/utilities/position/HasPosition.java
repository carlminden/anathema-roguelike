package com.anathema_roguelike.main.utilities.position;

public interface HasPosition {
	
	public abstract Point getPosition();
	
	public default int getX() {
		return getPosition().getX();
	}
	
	public default int getY() {
		return getPosition().getY();
	}
}
