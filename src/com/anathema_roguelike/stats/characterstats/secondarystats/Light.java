package com.anathema_roguelike.stats.characterstats.secondarystats;

import com.anathema_roguelike.characters.Character;

public class Light extends SecondaryStat {

	public Light(Character character) {
		super(character);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public double getAmount() {
		return getObject().getEnvironment().getLightLevels().get(getObject().getPosition());
	}
}
