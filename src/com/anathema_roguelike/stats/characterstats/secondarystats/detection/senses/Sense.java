package com.anathema_roguelike.stats.characterstats.secondarystats.detection.senses;

import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.stats.characterstats.secondarystats.SecondaryStat;

public abstract class Sense extends SecondaryStat {

	public Sense(Character character) {
		super(character);
	}
}
