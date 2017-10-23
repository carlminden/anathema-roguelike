package com.anathema_roguelike.stats.characterstats.secondarystats;

import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.entities.characters.inventory.PrimaryWeapon;
import com.anathema_roguelike.stats.characterstats.CharacterStat;
import com.anathema_roguelike.stats.itemstats.WeaponSpeed;

public class AttackSpeed extends CharacterStat {

	public AttackSpeed(Character object) {
		super(object);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double getAmount() {
		return getObject().getInventory().getSlot(PrimaryWeapon.class).getEquippedItem().getStatAmount(WeaponSpeed.class);
	}
}
