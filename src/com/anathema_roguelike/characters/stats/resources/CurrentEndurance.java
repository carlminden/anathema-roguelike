package com.anathema_roguelike.characters.stats.resources;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.stats.secondarystats.Endurance;
import com.anathema_roguelike.main.utilities.Listed;

@Listed
public class CurrentEndurance extends BoundedResource {

	public CurrentEndurance(Character character) {
		super(character, true);
	}

	@Override
	public int getMaximum() {
		return getCharacter().getModifiedStatScore(Endurance.class).intValue();
	}
}
