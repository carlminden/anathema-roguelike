package com.anathema_roguelike.stats.itemstats;

import com.anathema_roguelike.items.EquippableItem;
import com.anathema_roguelike.stats.StatSet;

public class ItemStatSet extends StatSet<EquippableItem, ItemStat>{

	public ItemStatSet(EquippableItem object) {
		super(object, EquippableItem.class, ItemStat.class);
	}
}
