/*******************************************************************************
 * Copyright (C) 2017 Carl Minden
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.anathema_roguelike.items.armor;

import com.anathema_roguelike.items.ItemProperty;

public abstract class ArmorProperty extends ItemProperty<Armor> {
	
	private double concealment;
	private double veil;
	private double attenuation;
	
	public ArmorProperty() {
		super();
	}
	
	public ArmorProperty(String name, double weight) {
		super(name, weight);
		// TODO Auto-generated constructor stub
	}
	
	protected double getConcealment() {
		return concealment;
	}
	
	protected double getVeil() {
		return veil;
	}
	
	protected double getAttenuation() {
		return attenuation;
	}
	
	public void setAttenuation(double attenuation) {
		this.attenuation = attenuation;
	}
	
	public void setConcealment(double concealment) {
		this.concealment = concealment;
	}
	
	public void setVeil(double veil) {
		this.veil = veil;
	}
}
