package com.anathema_roguelike.stats.characterstats.secondarystats;

import com.anathema_roguelike.characters.Character;

public class WeaponDamageMultiplier extends SecondaryStat {

	public WeaponDamageMultiplier(Character character) {
		super(character);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public double getAmount() {
		return 1.0;
	}
}
