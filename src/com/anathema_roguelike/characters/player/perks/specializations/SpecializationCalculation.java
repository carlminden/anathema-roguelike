package com.anathema_roguelike.characters.player.perks.specializations;

import java.util.function.Supplier;

import com.anathema_roguelike.characters.player.perks.abilities.Ability;

public abstract class SpecializationCalculation<T> implements Supplier<T> {
	
	Ability ability;
	
	public SpecializationCalculation(Ability ability) {
		this.ability = ability;
	}
	
	@Override
	public T get() {
		switch (ability.getSpecializationLevel()) {
		case 0:
			return zero();
		case 1:
			return one();
		case 2:
			return two();
		case 3:
			return three();
		default:
			throw new RuntimeException("Invalid Specialization Level");
		}
	}
	
	protected abstract T zero();
	protected abstract T one();
	protected abstract T two();
	protected abstract T three();

}
