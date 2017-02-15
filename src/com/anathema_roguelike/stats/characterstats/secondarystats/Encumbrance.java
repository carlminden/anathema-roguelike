package com.anathema_roguelike.stats.characterstats.secondarystats;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.stats.characterstats.attributes.Strength;

public abstract class Encumbrance extends SecondaryStat {

	public Encumbrance(Character character) {
		super(character);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public double getAmount() {
		double str = getObject().getStatAmount(Strength.class);
		double weight = getObject().getStatAmount(getWeight());
				
		return weight * 0.5 - (2 * str / (str + 10));
	}
	
	protected abstract Class<? extends SecondaryStat> getWeight();
}
