package com.anathema_roguelike.stimuli;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.environment.Point;

public class Sound extends Stimulus {

	public Sound(Point origin, int magnitude) {
		super(origin, magnitude);
	}

	@Override
	public PercievedStimulus computePercievedStimulus(Character character) {
		return null;
	}

}
