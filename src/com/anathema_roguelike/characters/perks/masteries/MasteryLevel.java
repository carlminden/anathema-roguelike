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
package com.anathema_roguelike.characters.perks.masteries;

import java.util.Optional;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.perks.Buff;
import com.anathema_roguelike.characters.perks.PassivePerk;
import com.anathema_roguelike.stats.characterstats.masteries.Mastery;
import com.anathema_roguelike.stats.effects.AdditiveCalculation;
import com.anathema_roguelike.stats.effects.Modifier;

public abstract class MasteryLevel<T extends Mastery> extends PassivePerk {
	
	private double amount = 1;
	private Class<T> mastery;
	
	public MasteryLevel(Class<T> mastery, int amount) {
		this.mastery = mastery;
		this.amount = amount;
	}
	
	public MasteryLevel(Class<T> mastery) {
		this.mastery = mastery;
	}
	
	@Override
	public Optional<Buff> getEffect() {
		return Optional.of(new Buff(this, new Modifier<Character, T>(mastery, AdditiveCalculation.build(() -> amount))));
	}
}
