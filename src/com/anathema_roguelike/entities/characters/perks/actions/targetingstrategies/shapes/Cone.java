package com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.shapes;

import com.anathema_roguelike.main.utilities.position.Direction;
import com.anathema_roguelike.main.utilities.position.HasPosition;
import com.anathema_roguelike.main.utilities.position.Point;

public class Cone extends Shape {

	private double radius;
	private int direction;
	
	public Cone(HasPosition center, double radius, int direction) {
		super(center);
		
		this.radius = radius;
		this.direction = direction;
	}

	@Override
	public boolean validPoint(Point point) {
		return pointSet.contains(point);
	}

	@Override
	protected void generatePoints() {
		
		double angle = Direction.angleToDirection(direction);
		
		int x = getX();
		int y = getY();
		for(int i = 0; i <= radius; i++) {
			for(int j = 0; j <= radius; j++) {
				Point p = new Point(x + i, y + j);
				
				if(getPosition().squareDistance(p) <= radius*radius && Math.abs(Direction.angleOf(getPosition(), p) - angle) <= 22.5) {
					addPoint(new Point(x + i, y + j));
				}
			}
		}
	}

}
