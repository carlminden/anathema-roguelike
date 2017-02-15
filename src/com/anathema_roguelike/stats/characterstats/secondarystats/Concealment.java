package com.anathema_roguelike.stats.characterstats.secondarystats;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.main.utilities.Listed;
import com.anathema_roguelike.stats.itemstats.ConcealmentDefense;

@Listed
public class Concealment extends Defense {

	public Concealment(Character character) {
		super(character, ConcealmentDefense.class);
	}

}
