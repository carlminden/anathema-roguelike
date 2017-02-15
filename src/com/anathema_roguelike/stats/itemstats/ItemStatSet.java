package com.anathema_roguelike.stats.itemstats;

import com.anathema_roguelike.items.EquippableItem;
import com.anathema_roguelike.stats.StatSet;
import com.google.common.eventbus.EventBus;

public class ItemStatSet extends StatSet<EquippableItem, ItemStat>{

	public ItemStatSet(EquippableItem object, EventBus eventBus) {
		super(object, EquippableItem.class, ItemStat.class, eventBus);
	}
}
