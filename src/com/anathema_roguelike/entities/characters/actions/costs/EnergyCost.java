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
package com.anathema_roguelike.entities.characters.actions.costs;

import com.anathema_roguelike.actors.Actor;

public class EnergyCost extends ActionCost {
	
	public static EnergyCost VERY_QUICK(Actor actor) {
		return new EnergyCost(actor, 0.25);
	}
	
	public static EnergyCost QUICK(Actor actor) {
		return new EnergyCost(actor, 0.5);
	}
	
	public static EnergyCost STANDARD(Actor actor) {
		return new EnergyCost(actor, 1);
	}
	
	public static EnergyCost SLOW(Actor actor) {
		return new EnergyCost(actor, 1.5);
	}
	
	public static EnergyCost VERY_SLOW(Actor actor) {
		return new EnergyCost(actor, 2);
	}
	
	public static EnergyCost EXTREMELY_SLOW(Actor actor) {
		return new EnergyCost(actor, 4);
	}
	
	private double energy;
	
	public EnergyCost(Actor actor, double energy) {
		super(actor);
		
		this.energy = energy;
	}

	@Override
	public void pay() {
		getActor().getEnergy().use(energy);
	}

}
