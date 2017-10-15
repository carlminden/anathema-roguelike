package com.anathema_roguelike.characters.perks.targetingstrategies.shapes;

import java.util.Collection;
import java.util.HashSet;

import com.anathema_roguelike.main.utilities.position.HasPosition;
import com.anathema_roguelike.main.utilities.position.Point;
import com.anathema_roguelike.stats.effects.Calculation;

public class Ring extends Circle {

	public Ring(HasPosition center, Calculation radius) {
		super(center, radius);
	}
	
	@Override
	public Collection<Point> generatePoints() {
		HashSet<Point> ret = new HashSet<>();
		
		int radius = getRadius();
		
		int x = getX();
		int y = getY();
		for(int i = 0; i <= radius; i++) {
			for(int j = 0; j <= radius; j++) {
				float squareDistance = getPosition().squareDistance(new Point(x + i, y + j));
				if(squareDistance < (radius + .5)*(radius + .5) && squareDistance > (radius - .5)*(radius - .5)) {
					ret.add(new Point(x + i, y + j));
					ret.add(new Point(x - i, y + j));
					ret.add(new Point(x + i, y - j));
					ret.add(new Point(x - i, y - j));
				}
			}
		}
		return ret;
	}

}
