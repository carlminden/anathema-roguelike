package com.anathema_roguelike.stats.characterstats.masteries;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.stats.characterstats.CharacterStat;

public class Mastery extends CharacterStat {

	public Mastery(Character object) {
		super(object);
	}

	@Override
	public double getAmount() {
		return 0;
	}
}
