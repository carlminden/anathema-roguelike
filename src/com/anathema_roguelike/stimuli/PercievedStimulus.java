package com.anathema_roguelike.stimuli;

import com.anathema_roguelike.environment.Point;
import com.anathema_roguelike.main.Game;

public class PercievedStimulus {
	
	private int magnitude;
	private long created;
	private Point position;
	
	public PercievedStimulus(Point location, int magnitude) {
		this.position = location;
		this.magnitude = magnitude;
		
		this.created = Game.getInstance().getElapsedTime();
	}
	
	public int getMagnitude() {
		return  magnitude - (int)((Game.getInstance().getElapsedTime() - created) / 100);
	}
	
	public Point getPosition() {
		return position;
	}
}
