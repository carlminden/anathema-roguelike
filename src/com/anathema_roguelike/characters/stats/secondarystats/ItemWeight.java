package com.anathema_roguelike.characters.stats.secondarystats;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.stats.itemstats.Weight;

public class ItemWeight extends SecondaryStat<Double> {

	public ItemWeight(Character character) {
		super(character);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Double getAmount() {
		return getCharacter().getInventory().getEquippedItems().parallelStream().mapToDouble(a -> a.getStat(Weight.class)).sum();
	}
}
