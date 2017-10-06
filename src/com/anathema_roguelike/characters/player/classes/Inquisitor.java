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
package com.anathema_roguelike.characters.player.classes;

import com.anathema_roguelike.characters.perks.PerkGroup;
import com.anathema_roguelike.characters.player.perks.abilities.spells.SpellOrSpecialization;
import com.anathema_roguelike.characters.player.perks.masteries.MasteryLevel;
import com.anathema_roguelike.stats.characterstats.masteries.BluntWeaponMastery;

public class Inquisitor extends CharacterClass {

	public Inquisitor() {
		super(new PerkGroup(new SpellOrSpecialization(1, Inquisitor.class), new MasteryLevel<>(BluntWeaponMastery.class)));
	}
}
