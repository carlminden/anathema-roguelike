package com.anathema_roguelike.entities.characters.foes.traits;

import java.util.Optional;

import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.entities.characters.perks.Buff;
import com.anathema_roguelike.entities.characters.perks.PassivePerk;
import com.anathema_roguelike.stats.characterstats.CharacterStat;
import com.anathema_roguelike.stats.effects.AdditiveCalculation;
import com.anathema_roguelike.stats.effects.Modifier;
import com.anathema_roguelike.stats.effects.MultiplicativeCalculation;

public class StatTrait<T extends CharacterStat> extends Trait<PassivePerk> {
	
	private Class<T> stat;
	private String name;
	private AdditiveCalculation additiveCalculation;
	private MultiplicativeCalculation multiplicativeCalculation;
	
	public StatTrait(String name, Class<T> stat, AdditiveCalculation additiveCalculation) {
		super();
		
		this.stat = stat;
		this.name = name;
		this.additiveCalculation = additiveCalculation;
	}
	
	public StatTrait(String name, Class<T> stat, MultiplicativeCalculation multiplicativeCalculation) {
		super();
		
		this.stat = stat;
		this.name = name;
		this.multiplicativeCalculation = multiplicativeCalculation;
	}
	
	public Class<T> getStat() {
		return stat;
	}

	@Override
	protected PassivePerk createPerk() {
		return new PassivePerk(name) {

			@Override
			public Optional<Buff> getEffect() {
				if(additiveCalculation != null) {
					return Optional.<Buff>of(new Buff(this, new Modifier<>(stat, additiveCalculation)));
				} else {
					return Optional.<Buff>of(new Buff(this, new Modifier<>(stat, multiplicativeCalculation)));
				}
			}
		};
	}
}
