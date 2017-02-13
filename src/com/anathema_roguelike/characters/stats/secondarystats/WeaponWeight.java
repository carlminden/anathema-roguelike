package com.anathema_roguelike.characters.stats.secondarystats;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.inventory.PrimaryWeapon;
import com.anathema_roguelike.characters.stats.itemstats.Weight;

public class WeaponWeight extends SecondaryStat<Double> {

	public WeaponWeight(Character character) {
		super(character);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Double getAmount() {
		return getCharacter().getInventory().getEquipedItem(PrimaryWeapon.class).getStat(Weight.class);
	}
}
