package com.anathema_roguelike.characters.foes.traits;

import com.anathema_roguelike.main.utilities.Utils;
import com.anathema_roguelike.stats.characterstats.secondarystats.detection.senses.Sense;
import com.anathema_roguelike.stats.effects.AdditiveCalculation;

public class Extraordinary<T extends Sense> extends StatTrait<T> {

	public Extraordinary(Class<T> attribute) {
		super("Extraordinary: " + Utils.getName(attribute), attribute, AdditiveCalculation.build(() -> 10.0));
	}
}
