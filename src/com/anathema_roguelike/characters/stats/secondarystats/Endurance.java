package com.anathema_roguelike.characters.stats.secondarystats;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.stats.attributes.Constitution;
import com.anathema_roguelike.main.utilities.Listed;

@Listed
public class Endurance extends SecondaryStat<Integer> {

	public Endurance(Character character) {
		super(character);
	}
	
	@Override
	public Integer getAmount() {
		return 100 + getCharacter().getModifiedStatScore(Constitution.class);
	}
}
