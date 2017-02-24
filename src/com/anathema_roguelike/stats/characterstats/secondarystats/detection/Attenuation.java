package com.anathema_roguelike.stats.characterstats.secondarystats.detection;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.stats.itemstats.AttenuationDefense;

public class Attenuation extends Defense {

	public Attenuation(Character character) {
		super(character, AttenuationDefense.class);
	}
}
