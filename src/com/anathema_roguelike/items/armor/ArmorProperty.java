package com.anathema_roguelike.items.armor;

import com.anathema_roguelike.items.ItemProperty;
import com.univocity.parsers.annotations.Parsed;

public abstract class ArmorProperty extends ItemProperty<Armor> {
	
	@Parsed(field = "Concealment")
	private double concealment;
	
	@Parsed(field = "Veil")
	private double veil;
	
	@Parsed(field = "Attenuation")
	private double attenuation;
	
	protected double getConcealment() {
		return concealment;
	}
	
	protected double getVeil() {
		return veil;
	}
	
	protected double getAttenuation() {
		return attenuation;
	}
}
