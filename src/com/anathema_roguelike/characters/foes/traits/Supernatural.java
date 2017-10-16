package com.anathema_roguelike.characters.foes.traits;

import com.anathema_roguelike.main.utilities.Utils;
import com.anathema_roguelike.stats.characterstats.attributes.Attribute;
import com.anathema_roguelike.stats.effects.MultiplicativeCalculation;

public class Supernatural<T extends Attribute> extends StatTrait<T> {
	
	public Supernatural(Class<T> attribute) {
		super("Supernatural: " + Utils.getName(attribute), attribute, MultiplicativeCalculation.build(() -> 1.75));
	}
}
