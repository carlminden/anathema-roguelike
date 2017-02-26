package com.anathema_roguelike.stimuli;

import java.util.Optional;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.environment.Point;

public class Sight extends Stimulus {

	public Sight(Point origin, int magnitude, Character character) {
		super(origin, magnitude, character);
	}

	@Override
	public Optional<PercievedStimulus> computePercievedStimulus(Character character) {
		
		if(character.getVisibilityOf(getSource().get()).ordinal() >= 3) {
			return Optional.of(new PercievedStimulus(getOrigin(), getMagnitude()));
		} else {
			return Optional.empty();
		}
	}
}
