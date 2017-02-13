package com.anathema_roguelike.characters.stats.secondarystats;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.main.utilities.Listed;

@Listed
public class ItemEncumbrance extends Encumbrance {

	public ItemEncumbrance(Character character) {
		super(character);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Class<? extends SecondaryStat<Double>> getWeight() {
		return ItemWeight.class;
	}

}
