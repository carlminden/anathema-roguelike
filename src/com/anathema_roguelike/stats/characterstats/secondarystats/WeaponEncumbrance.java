package com.anathema_roguelike.stats.characterstats.secondarystats;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.main.utilities.Listed;

@Listed
public class WeaponEncumbrance extends Encumbrance {

	public WeaponEncumbrance(Character character) {
		super(character);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Class<? extends SecondaryStat> getWeight() {
		return WeaponWeight.class;
	}

}
