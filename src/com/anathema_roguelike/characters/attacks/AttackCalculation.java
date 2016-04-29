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
package com.anathema_roguelike.characters.attacks;

import java.lang.reflect.InvocationTargetException;

public class AttackCalculation {
	
	private int offense;
	private int modifier;
	private int damageResult;
	
	public static AttackCalculation copy(AttackCalculation calc) {
		try {
			return calc.getClass().getConstructor(calc.getClass()).newInstance(calc);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			return new AttackCalculation(calc.getOffense(), calc.getModifier(), calc.getDamageResult());
		}
	}
	
	public AttackCalculation(int offense, int modifier, int damageResult) {
		this.modifier = modifier;
		this.offense = offense;
		this.damageResult = damageResult;
	}

	public int getDamageResult() {
		return damageResult;
	}

	public int getOffense() {
		return offense;
	}

	public int getModifier() {
		return modifier;
	};
}
