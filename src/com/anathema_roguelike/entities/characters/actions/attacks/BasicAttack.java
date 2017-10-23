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
package com.anathema_roguelike.entities.characters.actions.attacks;

import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.entities.characters.actions.costs.ResourceCost;
import com.anathema_roguelike.entities.characters.actions.costs.StimulusCost;
import com.anathema_roguelike.entities.characters.stimuli.Sound;
import com.anathema_roguelike.stats.characterstats.resources.CurrentEndurance;
import com.anathema_roguelike.stats.characterstats.resources.RecentMotion;

public class BasicAttack extends WeaponAttack {
	
	public BasicAttack(Character attacker, Character target) {
		super(attacker, target);
		
		addCost(new ResourceCost<>(attacker, RecentMotion.class, 50));
		addCost(new StimulusCost<>(attacker, Sound.class, 30));
		addCost(new ResourceCost<>(attacker, CurrentEndurance.class, -30));
	}
	
	public BasicAttack(Character attacker) {
		super(attacker);
		
		addCost(new ResourceCost<>(attacker, RecentMotion.class, 50));
		addCost(new StimulusCost<>(attacker, Sound.class, 30));
		addCost(new ResourceCost<>(attacker, CurrentEndurance.class, -30));
	}
}
