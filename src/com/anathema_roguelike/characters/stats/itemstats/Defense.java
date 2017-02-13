package com.anathema_roguelike.characters.stats.itemstats;

import com.anathema_roguelike.characters.Character;

public abstract class Defense extends ItemStat {

	public Defense(Character character) {
		super(character);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Double getAmount() {
		return getCharacter().getInventory().getDefense(this.getClass());
	}
}
