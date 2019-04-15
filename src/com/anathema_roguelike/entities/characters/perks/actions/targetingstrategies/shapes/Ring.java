/*******************************************************************************
 * Copyright (c) 2019. Carl Minden
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.shapes;

import com.anathema_roguelike.main.utilities.position.HasPosition;
import com.anathema_roguelike.main.utilities.position.Point;

public class Ring extends Circle {

	public Ring(HasPosition center, Calculation radius) {
		super(center, radius);
	}
	
	@Override
	public void generatePoints() {
		int radius = getRadius();
		
		int x = getX();
		int y = getY();
		for(int i = 0; i <= radius; i++) {
			for(int j = 0; j <= radius; j++) {
				float squareDistance = getPosition().squareDistance(new Point(x + i, y + j));
				if(squareDistance < (radius + .5)*(radius + .5) && squareDistance > (radius - .5)*(radius - .5)) {
					addPoint(new Point(x + i, y + j));
					addPoint(new Point(x - i, y + j));
					addPoint(new Point(x + i, y - j));
					addPoint(new Point(x - i, y - j));
				}
			}
		}
	}

}
