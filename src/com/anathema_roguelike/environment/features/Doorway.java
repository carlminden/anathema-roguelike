/*******************************************************************************
 * This file is part of AnathemaRL.
 *
 *     AnathemaRL is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     AnathemaRL is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with AnathemaRL.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package com.anathema_roguelike.environment.features;

import com.anathema_roguelike.environment.Environment;
import com.anathema_roguelike.environment.Orientation;
import com.anathema_roguelike.environment.Point;
import com.anathema_roguelike.fov.ObstructionChangedEvent;
import com.anathema_roguelike.main.display.Color;
import com.anathema_roguelike.main.display.VisualRepresentation;

public class Doorway extends Feature {
	
	private boolean open;
	private int direction;
	
	private static VisualRepresentation getInitialRepresentation(int direction) {
		if((direction & Orientation.VERTICAL) != 0){
			return new VisualRepresentation('\u2015', Color.BROWN);
		} else {
			return new VisualRepresentation('|', Color.BROWN);
		}
	}
	
	public Doorway(Environment level, Point point, int direction, boolean open) {
		super(level, point, getInitialRepresentation(direction), true, open, open ? 0.0 : 1.0, open ? 0.0 : 1.0);
		this.direction = direction;
		this.open = open;
	}
	
	public Doorway(Environment level, Point point, int direction) {
		super(level, point, getInitialRepresentation(direction), true, false, 1.0, 1.0);
		this.direction = direction;
		this.open = false;
	}
	
	@Override
	public VisualRepresentation getRepresentation() {
		
		if(open) {
			return new VisualRepresentation('+', Color.BROWN);
		} else if((direction & Orientation.VERTICAL) != 0){
			return new VisualRepresentation('\u2015', Color.BROWN);
		} else {
			return new VisualRepresentation('|', Color.BROWN);
		}
	}

	public boolean isOpen() {
		return open;
	}
	
	public boolean open() {
		if(!open) {
			open = true;
			getLevel().getEventBus().post(new ObstructionChangedEvent(getPosition()));
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean isPassable() {
		return open;
	}

	public void close() {
		this.open = false;
	}

	public int getDirection() {
		return direction;
	}
	
	public void setOrientation(int orientation) {
		this.direction = orientation;
	}
}
