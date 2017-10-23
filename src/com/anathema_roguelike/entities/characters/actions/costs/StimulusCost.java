package com.anathema_roguelike.entities.characters.actions.costs;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.entities.characters.stimuli.Stimulus;
import com.anathema_roguelike.environment.HasLocation;
import com.anathema_roguelike.environment.Location;

public class StimulusCost<T extends Stimulus> extends CharacterActionCost {
	
	private Class<T> stimulus;
	private double magnitude;
	private Optional<Location> location = Optional.empty();
	
	public StimulusCost(Character character, Class<T> stimulus, double magnitude) {
		super(character);
		this.stimulus = stimulus;
		this.magnitude = magnitude;
	}
	
	public StimulusCost(Character character, Class<T> stimulus, double magnitude, HasLocation location) {
		super(character);
		this.stimulus = stimulus;
		this.magnitude = magnitude;
		this.location = Optional.of(location.getLocation());
	}

	@Override
	public void pay() {
		try {
			Location l = location.isPresent() ? location.get() : getCharacter().getLocation();
			
			l.generateStimulus(stimulus.getConstructor(Double.TYPE, Character.class).newInstance(magnitude, getCharacter()));
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new RuntimeException("Could Not Construct " + stimulus);
		}
	}
	
	public Class<T> getStimulus() {
		return stimulus;
	}
	
	public double getMagnitude() {
		return magnitude;
	}
}
