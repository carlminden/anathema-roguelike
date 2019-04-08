package com.anathema_roguelike.entities.characters.foes.traits;

import com.anathema_roguelike.main.utilities.Utils;

public class Supernatural<T extends Attribute> extends StatTrait<T> {
	
	public Supernatural(Class<T> attribute) {
		super("Supernatural: " + Utils.getName(attribute), attribute, MultiplicativeCalculation.fixed(1.75));
	}
}
