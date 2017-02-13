package com.anathema_roguelike.characters.stats.secondarystats;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.stats.attributes.Perception;
import com.anathema_roguelike.main.utilities.Listed;

@Listed
public class Hearing extends SecondaryStat<Integer> {

	public Hearing(Character character) {
		super(character);
	}
	
	@Override
	public Integer getAmount() {
		return (getCharacter().getModifiedStatScore(Perception.class) + 1) / 2;
	}
}
