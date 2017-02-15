package com.anathema_roguelike.stats.characterstats.resources;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.main.utilities.Listed;
import com.anathema_roguelike.stats.characterstats.secondarystats.Endurance;

@Listed
public class CurrentEndurance extends BoundedResource {

	public CurrentEndurance(Character character) {
		super(character, true);
	}

	@Override
	public int getMaximum() {
		return (int) getObject().getStatAmount(Endurance.class);
	}
}
