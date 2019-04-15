/*******************************************************************************
 * Copyright (c) 2019. Carl Minden
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

package com.anathema_roguelike.entities.characters.foes;

import java.util.Optional;

import com.anathema_roguelike.stats.characterstats.attributes.Agility;
import com.anathema_roguelike.stats.characterstats.attributes.Constitution;
import com.anathema_roguelike.stats.characterstats.attributes.Intelligence;
import com.anathema_roguelike.stats.characterstats.attributes.Perception;
import com.anathema_roguelike.stats.characterstats.attributes.Strength;

public class BaseFoeStats extends PassivePerk {
	
	public BaseFoeStats() {
		super("");
	}

	private AdditiveCalculation onePerLevel = AdditiveCalculation.build(() -> (double) getCharacter().getLevel());
	
	@Override
	public Optional<Buff> getEffect() {
		return Optional.of(new Buff(Optional.of(this),
			new Modifier<>(Agility.class, onePerLevel),
			new Modifier<>(Constitution.class, onePerLevel),
			new Modifier<>(Intelligence.class, onePerLevel),
			new Modifier<>(Perception.class, onePerLevel),
			new Modifier<>(Strength.class, onePerLevel)
		));
	}
}
