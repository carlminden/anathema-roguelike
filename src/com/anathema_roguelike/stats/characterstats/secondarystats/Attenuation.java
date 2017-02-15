package com.anathema_roguelike.stats.characterstats.secondarystats;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.main.utilities.Listed;
import com.anathema_roguelike.stats.itemstats.AttenuationDefense;

@Listed
public class Attenuation extends Defense {

	public Attenuation(Character character) {
		super(character, AttenuationDefense.class);
	}
}
