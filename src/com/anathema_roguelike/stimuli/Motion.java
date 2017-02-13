package com.anathema_roguelike.stimuli;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.stats.itemstats.Concealment;
import com.anathema_roguelike.environment.Direction;

public class Motion extends Stimulus {

	public Motion(Character character, int baseMagnitude) {
		super(character.getPosition(), (int) (baseMagnitude * character.getModifiedStatScore(Concealment.class) / 100.0));
	}

	@Override
	public PercievedStimulus computePercievedStimulus(Character character) {
		double distance = character.getPosition().distance(getOrigin());
		double angle = Math.abs(character.getFacing() - Direction.angleOf(character.getPosition(), getOrigin()));
		
		int baseMagnitude = getMagnitude();
		
		baseMagnitude -= distance * 2;
		baseMagnitude -= angle / 5;
		
		return new PercievedStimulus(getOrigin(), baseMagnitude);
	}
}
