package com.anathema_roguelike.characters.stats.itemstats;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.stats.Stat;

public abstract class ItemStat extends Stat<Double> {

	public ItemStat(Character character) {
		super(character);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Double getAmount() {
		return 0.0;
	}
}
