package com.anathema_roguelike.characters.stats.secondarystats;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.main.utilities.Listed;

@Listed
public class WeaponDamageMultiplier extends SecondaryStat<Double> {

	public WeaponDamageMultiplier(Character character) {
		super(character);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Double getAmount() {
		return 1.0;
	}
}
