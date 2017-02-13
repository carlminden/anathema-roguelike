package com.anathema_roguelike.stimuli;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.environment.Point;

public abstract class Stimulus {
	
	private int magnitude;
	private Point origin;
	
	public Stimulus(Point origin, int magnitude) {
		this.origin = origin;
		this.magnitude = magnitude;
	}
	
	public int getMagnitude() {
		return magnitude;
	}
	
	public Point getOrigin() {
		return origin;
	}
	
	public abstract PercievedStimulus computePercievedStimulus(Character character);
}
