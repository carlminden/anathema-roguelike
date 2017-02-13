package com.anathema_roguelike.characters.stats.secondarystats;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.stats.attributes.Strength;

public abstract class Encumbrance extends SecondaryStat<Double> {

	public Encumbrance(Character character) {
		super(character);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Double getAmount() {
		int str = getCharacter().getModifiedStatScore(Strength.class);
		double weight = getCharacter().getModifiedStatScore(getWeight());
				
		return weight * 0.5 - (2 * str / (str + 10));
	}
	
	protected abstract Class<? extends SecondaryStat<Double>> getWeight();
}
