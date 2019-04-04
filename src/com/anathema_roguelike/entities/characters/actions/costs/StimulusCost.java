package com.anathema_roguelike.entities.characters.actions.costs;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.function.Supplier;

import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.entities.characters.player.Player;
import com.anathema_roguelike.entities.characters.stimuli.Sight;
import com.anathema_roguelike.entities.characters.stimuli.Stimulus;
import com.anathema_roguelike.environment.HasLocation;
import com.anathema_roguelike.environment.Location;

public class StimulusCost<T extends Stimulus> extends CharacterActionCost {
	
	private Class<T> stimulus;
	private Supplier<Double> magnitude;
	private Optional<Location> location = Optional.empty();
	
	public StimulusCost(Character character, Class<T> stimulus, Supplier<Double> magnitude) {
		super(character);
		this.stimulus = stimulus;
		this.magnitude = magnitude;
	}
	
	public StimulusCost(Character character, Class<T> stimulus, Supplier<Double> magnitude, HasLocation location) {
		this(character, stimulus, magnitude);
		
		this.location = Optional.of(location.getLocation());
	}
	
	public StimulusCost(Character character, Class<T> stimulus, Supplier<Double> magnitude, boolean after) {
		super(character, after);
		this.stimulus = stimulus;
		this.magnitude = magnitude;
		
	}
	
	public StimulusCost(Character character, Class<T> stimulus, Supplier<Double> magnitude, HasLocation location, boolean after) {
		this(character, stimulus, magnitude, after);

		this.location = Optional.of(location.getLocation());
	}

	@Override
	public void pay() {
		
		if(stimulus == Sight.class && getCharacter() instanceof Player) {
			System.out.println("PLAYER GENERATING STIMULI AT: " + (location.isPresent() ? location.get() : getCharacter().getLocation()));
		}
		
		try {
			Location l = location.isPresent() ? location.get() : getCharacter().getLocation();
			
			l.generateStimulus(stimulus.getConstructor(Double.TYPE, Character.class).newInstance(getMagnitude(), getCharacter()));
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new RuntimeException("Could Not Construct " + stimulus);
		}
	}
	
	public Class<T> getStimulus() {
		return stimulus;
	}
	
	public double getMagnitude() {
		return magnitude.get();
	}
}
