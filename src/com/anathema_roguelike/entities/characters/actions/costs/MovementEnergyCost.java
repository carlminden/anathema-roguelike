package com.anathema_roguelike.entities.characters.actions.costs;

import com.anathema_roguelike.stats.characterstats.secondarystats.MovementSpeed;
import com.anathema_roguelike.entities.characters.Character;

public class MovementEnergyCost extends EnergyCost {

	public MovementEnergyCost(Character character) {
		super(character, character.getStatAmount(MovementSpeed.class));
	}
}
