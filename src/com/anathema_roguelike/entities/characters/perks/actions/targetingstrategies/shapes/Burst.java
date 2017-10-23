package com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.shapes;

import com.anathema_roguelike.main.utilities.position.Direction;
import com.anathema_roguelike.main.utilities.position.HasPosition;
import com.anathema_roguelike.main.utilities.position.Point;
import com.anathema_roguelike.stats.effects.Calculation;

public class Burst extends Shape {
	
	private Calculation sizeCalculation;
	
	
	public Burst(HasPosition origin, Calculation sizeCalculation) {
		super(origin);
		this.sizeCalculation = sizeCalculation;
	}

	@Override
	public void generatePoints() {
		int x = getX();
		int y = getY();
		
		int size = sizeCalculation.get().intValue();
		
		switch (size) {
		case 1:
			for(int d : Direction.DIRECTIONS_4) {
				addPoint(Direction.offset(getPosition(), d));
			}
			break;
		case 2:
			for(int d : Direction.DIRECTIONS_8) {
				addPoint(Direction.offset(getPosition(), d));
			}
			addPoint(new Point(x + 2, y));
			addPoint(new Point(x - 2, y));
			addPoint(new Point(x, y + 2));
			addPoint(new Point(x, y - 2));
		case 3:
			addPoint(new Point(x + 2, y + 2));
			addPoint(new Point(x - 2, y - 2));
			addPoint(new Point(x + 2, y - 2));
			addPoint(new Point(x - 2, y + 2));
			addPoint(new Point(x + 1, y + 2));
			addPoint(new Point(x - 1, y + 2));
			addPoint(new Point(x + 1, y - 2));
			addPoint(new Point(x - 1, y - 2));
			addPoint(new Point(x + 3, y));
			addPoint(new Point(x - 3, y));
			addPoint(new Point(x, y + 3));
			addPoint(new Point(x, y - 3));
		default:
			break;
		}
	}

}
