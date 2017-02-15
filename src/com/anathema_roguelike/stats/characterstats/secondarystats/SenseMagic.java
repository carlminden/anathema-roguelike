package com.anathema_roguelike.stats.characterstats.secondarystats;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.main.utilities.Listed;
import com.anathema_roguelike.stats.characterstats.attributes.Intelligence;

@Listed
public class SenseMagic extends SecondaryStat {

	public SenseMagic(Character character) {
		super(character);
	}
	
	@Override
	public double getAmount() {
		return getObject().getStatAmount(Intelligence.class);
	}
}
