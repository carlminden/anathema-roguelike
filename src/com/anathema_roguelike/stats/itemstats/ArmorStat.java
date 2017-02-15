package com.anathema_roguelike.stats.itemstats;

import com.anathema_roguelike.items.EquippableItem;

public abstract class ArmorStat extends ItemStat {

	public ArmorStat(EquippableItem item) {
		super(item);
	}
	
	@Override
	public double getAmount() {
		return getObject().getStat(this.getClass());
	}
}
