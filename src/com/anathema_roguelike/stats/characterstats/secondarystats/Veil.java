package com.anathema_roguelike.stats.characterstats.secondarystats;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.stats.itemstats.VeilDefense;

public class Veil extends Defense {

	public Veil(Character character) {
		super(character, VeilDefense.class);
	}
}
