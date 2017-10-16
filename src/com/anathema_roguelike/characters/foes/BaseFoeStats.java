package com.anathema_roguelike.characters.foes;

import java.util.Optional;

import com.anathema_roguelike.characters.perks.Buff;
import com.anathema_roguelike.characters.perks.PassivePerk;
import com.anathema_roguelike.stats.characterstats.attributes.Agility;
import com.anathema_roguelike.stats.characterstats.attributes.Constitution;
import com.anathema_roguelike.stats.characterstats.attributes.Intelligence;
import com.anathema_roguelike.stats.characterstats.attributes.Perception;
import com.anathema_roguelike.stats.characterstats.attributes.Strength;
import com.anathema_roguelike.stats.characterstats.secondarystats.detection.defenses.Attenuation;
import com.anathema_roguelike.stats.characterstats.secondarystats.detection.defenses.Concealment;
import com.anathema_roguelike.stats.characterstats.secondarystats.detection.defenses.Veil;
import com.anathema_roguelike.stats.effects.AdditiveCalculation;
import com.anathema_roguelike.stats.effects.Modifier;

public class BaseFoeStats extends PassivePerk {
	
	public BaseFoeStats() {
		super("");
	}

	AdditiveCalculation onePerLevel = AdditiveCalculation.build(() -> new Double(getCharacter().getLevel()));
	AdditiveCalculation defenseCalculation = AdditiveCalculation.build(() -> new Double(10 + getCharacter().getLevel() * 5));
	
	@Override
	public Optional<Buff> getEffect() {
		return Optional.of(new Buff(this,
			new Modifier<>(Agility.class, onePerLevel),
			new Modifier<>(Constitution.class, onePerLevel),
			new Modifier<>(Intelligence.class, onePerLevel),
			new Modifier<>(Perception.class, onePerLevel),
			new Modifier<>(Strength.class, onePerLevel),
			new Modifier<>(Concealment.class, defenseCalculation),
			new Modifier<>(Veil.class, defenseCalculation),
			new Modifier<>(Attenuation.class, defenseCalculation)
		));
	}
}
