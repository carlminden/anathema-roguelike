package com.anathema_roguelike.stimuli;

import java.util.Optional;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.environment.Point;

public abstract class Stimulus {
	
	private int magnitude;
	private Point origin;
	Optional<Character> source = Optional.empty();
	
	public Stimulus(Point origin, int magnitude) {
		this.origin = origin;
		this.magnitude = magnitude;
	}
	
	public Stimulus(Point origin, int magnitude, Character source) {
		this.origin = origin;
		this.magnitude = magnitude;
		this.source = Optional.of(source);
	}
	
	public int getMagnitude() {
		return magnitude;
	}
	
	public Point getOrigin() {
		return origin;
	}
	
	public Optional<Character> getSource() {
		return source;
	}
	
	public abstract Optional<PercievedStimulus> computePercievedStimulus(Character character);
}
