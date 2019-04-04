package com.anathema_roguelike.environment;

import com.anathema_roguelike.main.utilities.position.HasPosition;
import com.anathema_roguelike.main.utilities.position.Point;

public interface HasLocation extends HasPosition {
	
	Location getLocation();
	
	default Environment getEnvironment() {
		return getLocation().getEnvironment();
	}
	
	default Point getPosition() {
		return getLocation().getPosition();
	}
	
	default int getX() {
		return getPosition().getX();
	}
	
	default int getY() {
		return getPosition().getY();
	}
}
