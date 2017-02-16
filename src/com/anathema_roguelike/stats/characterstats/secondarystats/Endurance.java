package com.anathema_roguelike.stats.characterstats.secondarystats;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.stats.characterstats.attributes.Constitution;

public class Endurance extends SecondaryStat {

	public Endurance(Character character) {
		super(character);
	}
	
	@Override
	public double getAmount() {
		return 100 + getObject().getStatAmount(Constitution.class);
	}
}
