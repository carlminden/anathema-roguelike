package com.anathema_roguelike.characters.perks.masteries;

import java.util.Optional;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.perks.Buff;
import com.anathema_roguelike.characters.perks.PassivePerk;
import com.anathema_roguelike.stats.characterstats.masteries.Mastery;
import com.anathema_roguelike.stats.effects.AdditiveCalculation;
import com.anathema_roguelike.stats.effects.Modifier;

public abstract class MasteryLevel<T extends Mastery> extends PassivePerk {
	
	private double amount = 1;
	private Class<T> mastery;
	
	public MasteryLevel(Class<T> mastery, int amount) {
		this.mastery = mastery;
		this.amount = amount;
	}
	
	public MasteryLevel(Class<T> mastery) {
		this.mastery = mastery;
	}
	
	@Override
	public Optional<Buff> getEffect() {
		return Optional.of(new Buff(this, new Modifier<Character, T>(mastery, AdditiveCalculation.build(() -> amount))));
	}
}
