package com.anathema_roguelike.stats.characterstats.secondarystats.detection;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.stats.characterstats.attributes.Perception;
import com.anathema_roguelike.stats.characterstats.secondarystats.SecondaryStat;

public class Hearing extends SecondaryStat {

	public Hearing(Character character) {
		super(character);
	}
	
	@Override
	public double getAmount() {
		return (getObject().getStatAmount(Perception.class) + 1) / 2;
	}
}
