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
	private AdditiveCalculation additiveModifier;
	private MultiplicativeCalculation multiplicativeModifier;
	
	public Modifier(Class<? extends S> affectedStat, AdditiveCalculation additiveModifier) {

		this.affectedStat = affectedStat;
		this.additiveModifier = additiveModifier;
	}
	
	public Modifier(Class<? extends S> affectedStat, MultiplicativeCalculation multiplicativeModifier) {

		this.affectedStat = affectedStat;
		this.multiplicativeModifier = multiplicativeModifier;
	}
	
	public Class<? extends S> getAffectedStat() {
		return affectedStat;
	}

	public double getAdditiveAmount(T arg) {
		if(additiveModifier != null) {
			return additiveModifier.get();
		} else {
			return 0;
		}
	}
	
	protected MultiplicativeCalculation getMultiplicativeModifier() {
		return multiplicativeModifier;
	}
	
	public double getMultiplier(T arg) {
		if(multiplicativeModifier != null) {
			return multiplicativeModifier.get();
		} else {
			return 1;
		}
	}
	
	protected AdditiveCalculation getAdditiveModifier() {
		return additiveModifier;
	}
}
