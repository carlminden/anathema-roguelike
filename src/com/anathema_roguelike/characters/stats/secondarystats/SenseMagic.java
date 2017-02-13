package com.anathema_roguelike.characters.stats.secondarystats;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.stats.attributes.Intelligence;
import com.anathema_roguelike.main.utilities.Listed;

@Listed
public class SenseMagic extends SecondaryStat<Integer> {

	public SenseMagic(Character character) {
		super(character);
	}
	
	@Override
	public Integer getAmount() {
		return getCharacter().getModifiedStatScore(Intelligence.class);
	}
}
