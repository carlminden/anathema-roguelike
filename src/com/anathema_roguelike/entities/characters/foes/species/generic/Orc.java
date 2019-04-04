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
package com.anathema_roguelike.entities.characters.foes.species.generic;

import java.util.Optional;

import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.entities.characters.foes.corruptions.Corruption;
import com.anathema_roguelike.entities.characters.foes.roles.Role;
import com.anathema_roguelike.entities.characters.foes.traits.Deficient;
import com.anathema_roguelike.entities.characters.foes.traits.Extraordinary;
import com.anathema_roguelike.entities.characters.foes.traits.Supernatural;
import com.anathema_roguelike.entities.characters.perks.Buff;
import com.anathema_roguelike.main.display.Color;
import com.anathema_roguelike.main.display.VisualRepresentation;
import com.anathema_roguelike.stats.characterstats.attributes.Intelligence;
import com.anathema_roguelike.stats.characterstats.attributes.Strength;
import com.anathema_roguelike.stats.characterstats.secondarystats.LightEmission;
import com.anathema_roguelike.stats.characterstats.secondarystats.detection.senses.Hearing;
import com.anathema_roguelike.stats.effects.AdditiveCalculation;
import com.anathema_roguelike.stats.effects.Modifier;

public class Orc extends GenericSpecies {

	public Orc(Role role, Corruption corruption) {
		super(role, corruption,
				new Supernatural<>(Strength.class),
				new Extraordinary<>(Hearing.class),
				new Deficient<>(Intelligence.class));
		
		applyEffect(Optional.of(new Buff(null, new Modifier<>(
				LightEmission.class, AdditiveCalculation.fixed(15.0)))));
		
	}
	
	@Override
	public String toString() {
		return "The Orc";
	}
	
	@Override
	public VisualRepresentation getVisualRepresentation() {
		return new VisualRepresentation('o', Color.GREEN);
	}
}
