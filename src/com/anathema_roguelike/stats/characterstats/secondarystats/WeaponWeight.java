package com.anathema_roguelike.stats.characterstats.secondarystats;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.inventory.PrimaryWeapon;
import com.anathema_roguelike.stats.itemstats.Weight;

public class WeaponWeight extends SecondaryStat {

	public WeaponWeight(Character character) {
		super(character);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public double getAmount() {
		return getObject().getInventory().getSlot(PrimaryWeapon.class).getEquippedItem().getStatAmount(Weight.class);
	}
}
