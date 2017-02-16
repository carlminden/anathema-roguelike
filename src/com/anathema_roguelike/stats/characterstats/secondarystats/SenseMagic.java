package com.anathema_roguelike.stats.characterstats.secondarystats;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.stats.characterstats.attributes.Intelligence;

public class SenseMagic extends SecondaryStat {

	public SenseMagic(Character character) {
		super(character);
	}
	
	@Override
	public double getAmount() {
		return getObject().getStatAmount(Intelligence.class);
	}
}
