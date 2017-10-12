package com.anathema_roguelike.main.display;

import java.util.Collection;

import com.anathema_roguelike.main.utilities.position.Point;

import squidpony.squidgrid.gui.gdx.SColor;

public class PointsOutline extends Outline {
	
	private Collection<Point> points;

	public PointsOutline(Point offset, Collection<Point> points, SColor color) {
		super(offset, color);
		
		this.points = points;
	}

	@Override
	public Collection<Point> getPoints() {
		return points;
	}

	@Override
	public boolean validPoint(Point point) {
		return points.contains(point);
	}

}
