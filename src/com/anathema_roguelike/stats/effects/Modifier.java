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
package com.anathema_roguelike.stats.effects;

import com.anathema_roguelike.stats.Stat;

public class Modifier<T, S extends Stat<?>> {
	
	private Class<? extends S> affectedStat;
	private Calculation additiveModifier;
	private Calculation multiplicativeModifier;
	
	public Modifier(Class<? extends S> affectedStat, Calculation calculation) {

		this.affectedStat = affectedStat;
		
		if(calculation instanceof AdditiveCalculation) {
			this.additiveModifier = calculation;
		} else {
			this.multiplicativeModifier = calculation;
		}
		
	}
	
	public Class<? extends S> getAffectedStat() {
		return affectedStat;
	}

	public double getAdditiveAmount() {
		if(additiveModifier != null) {
			return additiveModifier.get();
		} else {
			return 0;
		}
	}
	
	protected Calculation getMultiplicativeModifier() {
		return multiplicativeModifier;
	}
	
	public double getMultiplier() {
		if(multiplicativeModifier != null) {
			return multiplicativeModifier.get();
		} else {
			return 1;
		}
	}
	
	protected Calculation getAdditiveModifier() {
		return additiveModifier;
	}
}
