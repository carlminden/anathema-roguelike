package com.anathema_roguelike.stats.characterstats.secondarystats;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.main.utilities.Listed;
import com.anathema_roguelike.stats.itemstats.VeilDefense;

@Listed
public class Veil extends Defense {

	public Veil(Character character) {
		super(character, VeilDefense.class);
	}
}
