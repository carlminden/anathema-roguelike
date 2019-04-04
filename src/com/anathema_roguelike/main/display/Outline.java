package com.anathema_roguelike.main.display;

import java.util.Collection;

import com.anathema_roguelike.main.Game;
import com.anathema_roguelike.main.ui.UIConfig;
import com.anathema_roguelike.main.utilities.position.Direction;
import com.anathema_roguelike.main.utilities.position.Point;

import squidpony.squidgrid.gui.gdx.SColor;

public abstract class Outline implements Renderable {
	
	private SColor color;
	private Point offset = new Point(0, 0);
	
	public Outline(Point offset, SColor color) {
        this.offset = offset;
        this.color = color;
	}
	
	public Outline(SColor color) {
        this.color = color;
	}
	
	public abstract Collection<Point> getPoints();
	public abstract boolean validPoint(Point point);
	
	@Override
	public void render() {
		for(Point p : getPoints()) {
			if(validPoint(p)) {
        		if(!validPoint(Direction.offset(p, Direction.UP))) {
        			top(p, color);
        		}
        		
        		if(!validPoint(Direction.offset(p, Direction.DOWN))) {
        			bottom(p, color);
        		}
        		
        		if(!validPoint(Direction.offset(p, Direction.LEFT))) {
        			left(p, color);
        		}
        		
        		if(!validPoint(Direction.offset(p, Direction.RIGHT))) {
            		right(p, color);
        		}
        	}
		}
	}
	
	private void top(Point point, SColor color) {
		int x = point.getX() + offset.getX();
		int y = point.getY() + offset.getY();
		
		Game.getInstance().getDisplay().drawLine(x * UIConfig.CELL_WIDTH - 1, y * UIConfig.CELL_HEIGHT, (x + 1) * UIConfig.CELL_WIDTH, y * UIConfig.CELL_HEIGHT, color);
	}
	
	private void bottom(Point point, SColor color) {
		int x = point.getX() + offset.getX();
		int y = point.getY() + offset.getY();
		
		Game.getInstance().getDisplay().drawLine(x * UIConfig.CELL_WIDTH - 1, (y + 1) * UIConfig.CELL_HEIGHT - 1, (x + 1) * UIConfig.CELL_WIDTH, (y + 1) * UIConfig.CELL_HEIGHT - 1, color);
	}
	
	private void left(Point point, SColor color) {
		int x = point.getX() + offset.getX();
		int y = point.getY() + offset.getY();
		
		Game.getInstance().getDisplay().drawLine(x * UIConfig.CELL_WIDTH, y * UIConfig.CELL_HEIGHT, x * UIConfig.CELL_WIDTH, (y + 1) * UIConfig.CELL_HEIGHT, color);
	}
	
	private void right(Point point, SColor color) {
		int x = point.getX() + offset.getX();
		int y = point.getY() + offset.getY();
		
		Game.getInstance().getDisplay().drawLine((x + 1) * UIConfig.CELL_WIDTH, y * UIConfig.CELL_HEIGHT, (x + 1) * UIConfig.CELL_WIDTH, (y + 1) * UIConfig.CELL_HEIGHT, color);
	}

	public void setOffset(Point point) {
		this.offset = point;
	}
}
