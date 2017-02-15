package com.anathema_roguelike.stats.characterstats;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.stats.StatSet;
import com.google.common.eventbus.EventBus;

public class CharacterStatSet extends StatSet<Character, CharacterStat> {

	public CharacterStatSet(Character object, EventBus eventBus) {
		super(object, Character.class, CharacterStat.class, eventBus);
	}
}
