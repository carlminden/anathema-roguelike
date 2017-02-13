/*******************************************************************************
 * This file is part of AnathemaRL.
 *
 *     AnathemaRL is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     AnathemaRL is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with AnathemaRL.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package com.anathema_roguelike.characters.effects.modifiers;

import com.anathema_roguelike.characters.effects.Calculation;
import com.anathema_roguelike.characters.effects.Effect;
import com.anathema_roguelike.characters.effects.FixedCalculation;
import com.anathema_roguelike.characters.stats.Stat;

public class Modifier {
	
	private Class<? extends Stat<?>> affectedStat;
	private Calculation<Integer> staticCalculation;
	private Calculation<Double> multiplicativeCalculation;
	private Effect group;
	private Object source;
	
	public Modifier(Object source, Class<? extends Stat<?>> affectedStat,
			Calculation<Integer> staticCalculation, Calculation<Double> multiplicativeCalculation) {
		
		init(source, affectedStat, staticCalculation, multiplicativeCalculation);
	}
	
	public void init(Object source, Class<? extends Stat<?>> affectedStat,
			Calculation<Integer> staticCalculation, Calculation<Double> multiplicativeCalculation) {
		
		this.source = source;
		this.affectedStat = affectedStat;
		this.staticCalculation = staticCalculation;
		this.multiplicativeCalculation = multiplicativeCalculation;
	}
	
	public Modifier(Object source, Class<? extends Stat<?>> affectedStat, int amount) {
		init(source, affectedStat, new FixedCalculation<Integer>(amount), null);
	}
	
	public Modifier(Object source, Class<? extends Stat<?>> affectedStat, double amount) {
		init(source, affectedStat, null, new FixedCalculation<Double>(amount));
	}
	
	public Modifier(Modifier modifier) {
		init(modifier.getSource(), modifier.getAffectedStat(), modifier.getStaticCalculation(), modifier.getMultiplicativeCalculation());
		
		setGroup(modifier.getGroup());
	}
	
	public Object getSource() {
		return source;
	}

	public Class<? extends Stat<?>> getAffectedStat() {
		return affectedStat;
	}

	public int getStaticAmount() {
		if(staticCalculation != null) {
			return staticCalculation.calculate();
		} else {
			return 0;
		}
	}
	
	protected Calculation<Double> getMultiplicativeCalculation() {
		return multiplicativeCalculation;
	}
	
	public double getMultiplier() {
		if(multiplicativeCalculation != null) {
			return multiplicativeCalculation.calculate();
		} else {
			return 1;
		}
	}
	
	protected Calculation<Integer> getStaticCalculation() {
		return staticCalculation;
	}

	public Effect getGroup() {
		return group;
	}
	
	public void setGroup(Effect group) {
		this.group = group;
	}
}
