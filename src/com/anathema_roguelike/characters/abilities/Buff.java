package com.anathema_roguelike.characters.abilities;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.stats.characterstats.CharacterStat;
import com.anathema_roguelike.stats.effects.Duration;
import com.anathema_roguelike.stats.effects.Effect;
import com.anathema_roguelike.stats.effects.Modifier;

public class Buff extends Effect<Character, CharacterStat> {
	
	@SafeVarargs
	public Buff(Object source, Modifier<? extends CharacterStat>... modifiers) {
		super(source, modifiers);
	}

	@SafeVarargs
	public Buff(Object source, Duration duration, Modifier<? extends CharacterStat>... modifiers) {
		super(source, duration, modifiers);
	}
}
