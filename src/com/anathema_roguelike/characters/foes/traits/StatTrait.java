package com.anathema_roguelike.characters.foes.traits;

import java.util.Optional;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.perks.Buff;
import com.anathema_roguelike.characters.perks.PassivePerk;
import com.anathema_roguelike.stats.characterstats.CharacterStat;
import com.anathema_roguelike.stats.effects.Calculation;
import com.anathema_roguelike.stats.effects.Modifier;

public class StatTrait<T extends CharacterStat> extends Trait<PassivePerk> {
	
	private Class<T> stat;
	
	public StatTrait(Class<T> stat, Calculation calculation) {
		super(new PassivePerk() {

			@Override
			public Optional<Buff> getEffect() {
				return Optional.<Buff>of(new Buff(this, new Modifier<Character, T>(stat, calculation)));
			}
		});
		
		this.stat = stat;
	}
	
	public Class<T> getStat() {
		return stat;
	}
}
