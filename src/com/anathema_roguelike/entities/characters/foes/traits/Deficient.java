package com.anathema_roguelike.entities.characters.foes.traits;

import com.anathema_roguelike.main.utilities.Utils;
import com.anathema_roguelike.stats.characterstats.attributes.Attribute;
import com.anathema_roguelike.stats.effects.MultiplicativeCalculation;

public class Deficient<T extends Attribute> extends StatTrait<T> {

	public Deficient(Class<T> attribute) {
		super("Deficient: " + Utils.getName(attribute), attribute, MultiplicativeCalculation.fixed(0.25));
	}
}