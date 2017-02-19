package com.anathema_roguelike.characters.abilities;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.stats.characterstats.CharacterStat;
import com.anathema_roguelike.stats.effects.Duration;
import com.anathema_roguelike.stats.effects.Effect;
import com.anathema_roguelike.stats.effects.HasEffect;
import com.anathema_roguelike.stats.effects.Modifier;

public class Buff extends Effect<Character, CharacterStat> {
	
	@SafeVarargs
	public Buff(HasEffect<? extends Effect<Character, CharacterStat>> source, Modifier<Character, ? extends CharacterStat>... modifiers) {
		super(source, modifiers);
	}

	@SafeVarargs
	public Buff(HasEffect<? extends Effect<Character, CharacterStat>> source, Duration duration, Modifier<Character, ? extends CharacterStat>... modifiers) {
		super(source, duration, modifiers);
	}
}
