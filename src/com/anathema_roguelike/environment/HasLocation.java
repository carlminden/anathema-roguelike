package com.anathema_roguelike.environment;

import com.anathema_roguelike.main.utilities.position.HasPosition;
import com.anathema_roguelike.main.utilities.position.Point;

public interface HasLocation extends HasPosition {
	
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