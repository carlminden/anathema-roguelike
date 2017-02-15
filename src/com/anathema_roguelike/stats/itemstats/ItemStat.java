package com.anathema_roguelike.stats.itemstats;

import com.anathema_roguelike.items.EquippableItem;
import com.anathema_roguelike.stats.Stat;

public abstract class ItemStat extends Stat<EquippableItem> {
	
	protected double base;
	
	public ItemStat(EquippableItem item) {
		super(item);
	}
	
	@Override
	public double getAmount() {
		return base;
	}
	
	public void setScore(int base) {
		this.base = base;
	}
}
