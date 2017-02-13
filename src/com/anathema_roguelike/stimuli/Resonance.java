package com.anathema_roguelike.stimuli;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.environment.Point;

public class Resonance extends Stimulus {

	public Resonance(Point origin, int magnitude) {
		super(origin, magnitude);
	}

	@Override
	public PercievedStimulus computePercievedStimulus(Character character) {
		return null;
	}

}
