package com.anathema_roguelike.characters.stats.secondarystats;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.stats.itemstats.Concealment;
import com.anathema_roguelike.characters.stats.resources.RecentMotion;

public class Stealth extends SecondaryStat<Double> {

	public Stealth(Character character) {
		super(character);
	}
	
	@Override
	public Double getAmount() {
		double concealment = getCharacter().getModifiedStatScore(Concealment.class);
		int motion = getCharacter().getModifiedStatScore(RecentMotion.class);
		
		double light = getCharacter().getEnvironment().getLightLevels().get(getCharacter().getPosition());
		
		return 0.0;
		
	}
}
