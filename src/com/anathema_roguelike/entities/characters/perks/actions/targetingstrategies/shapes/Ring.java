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
