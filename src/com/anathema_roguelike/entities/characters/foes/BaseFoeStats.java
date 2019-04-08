package com.anathema_roguelike.entities.characters.foes;

import java.util.Optional;

import com.anathema_roguelike.entities.characters.perks.Buff;
import com.anathema_roguelike.entities.characters.perks.PassivePerk;
import com.anathema_roguelike.stats.characterstats.attributes.Agility;
import com.anathema_roguelike.stats.characterstats.attributes.Constitution;
import com.anathema_roguelike.stats.characterstats.attributes.Intelligence;
import com.anathema_roguelike.stats.characterstats.attributes.Perception;
import com.anathema_roguelike.stats.characterstats.attributes.Strength;

public class BaseFoeStats extends PassivePerk {
	
	public BaseFoeStats() {
		super("");
	}

	private AdditiveCalculation onePerLevel = AdditiveCalculation.build(() -> (double) getCharacter().getLevel());
	
	@Override
	public Optional<Buff> getEffect() {
		return Optional.of(new Buff(Optional.of(this),
			new Modifier<>(Agility.class, onePerLevel),
			new Modifier<>(Constitution.class, onePerLevel),
			new Modifier<>(Intelligence.class, onePerLevel),
			new Modifier<>(Perception.class, onePerLevel),
			new Modifier<>(Strength.class, onePerLevel)
		));
	}
}
