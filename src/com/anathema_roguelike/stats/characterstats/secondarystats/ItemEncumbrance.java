package com.anathema_roguelike.stats.characterstats.secondarystats;

import com.anathema_roguelike.characters.Character;

public class ItemEncumbrance extends Encumbrance {

	public ItemEncumbrance(Character character) {
		super(character);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Class<? extends SecondaryStat> getWeight() {
		return ItemWeight.class;
	}

}
