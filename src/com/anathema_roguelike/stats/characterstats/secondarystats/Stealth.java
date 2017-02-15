package com.anathema_roguelike.stats.characterstats.secondarystats;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.stats.characterstats.resources.RecentMotion;

public class Stealth extends SecondaryStat {

	public Stealth(Character character) {
		super(character);
	}
	
	@Override
	public double getAmount() {
		double concealment = getObject().getStatAmount(Concealment.class);
		double motion = getObject().getStatAmount(RecentMotion.class);
		
		double light = getObject().getEnvironment().getLightLevels().get(getObject().getPosition());
		
		return 0.0;
		
	}
}
