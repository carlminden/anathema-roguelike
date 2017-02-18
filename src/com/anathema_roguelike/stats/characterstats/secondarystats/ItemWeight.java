package com.anathema_roguelike.stats.characterstats.secondarystats;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.stats.itemstats.Weight;

public class ItemWeight extends SecondaryStat {

	public ItemWeight(Character character) {
		super(character);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public double getAmount() {
		return getObject().getInventory().getEquippedItems().parallelStream().mapToDouble(a -> a.getStatAmount(Weight.class)).sum();
	}
}
