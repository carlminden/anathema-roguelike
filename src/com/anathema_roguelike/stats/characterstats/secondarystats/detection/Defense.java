package com.anathema_roguelike.stats.characterstats.secondarystats.detection;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.stats.characterstats.secondarystats.SecondaryStat;
import com.anathema_roguelike.stats.itemstats.ArmorStat;

public abstract class Defense extends SecondaryStat {
	
	private Class<? extends ArmorStat> armorDefense;
	
	public Defense(Character character, Class<? extends ArmorStat> armorDefense) {
		super(character);
		
		this.armorDefense = armorDefense;
	}
	
	@Override
	public double getAmount() {
		return getObject().getInventory().getDefense(armorDefense);
	}
}
