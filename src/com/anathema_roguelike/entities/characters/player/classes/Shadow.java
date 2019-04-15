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
package com.anathema_roguelike.entities.characters.player.classes;

import com.anathema_roguelike.stats.characterstats.masteries.ShortBladeMastery;
import com.anathema_roguelike.stats.characterstats.masteries.SpearMastery;
import com.anathema_roguelike.stats.characterstats.masteries.SpellMastery;

public class Shadow extends PlayerClass {

	public Shadow() {
		super(
			new PerkGroup/*1*/(new MasteryLevel<>(SpearMastery.class), new SpellOrSpecialization(1, Shadow.class)),
			new PerkGroup/*2*/(new MasteryLevel<>(SpellMastery.class), new MasteryLevel<>(ShortBladeMastery.class)),
			new PerkGroup/*3*/(new SpellOrSpecialization(1, Shadow.class), new AffinityForDarkness()),
			new PerkGroup/*4*/(new MasteryLevel<>(SpellMastery.class), new Hide()),
			new PerkGroup/*5*/(new MasteryLevel<>(SpearMastery.class), new MasteryLevel<>(ShortBladeMastery.class), new SpellOrSpecialization(1, Shadow.class)),
			new PerkGroup/*6*/(new MasteryLevel<>(SpellMastery.class), new Shadowdance()),
			new PerkGroup/*7*/(new MasteryLevel<>(SpearMastery.class), new SpellOrSpecialization(1, Shadow.class)),
			new PerkGroup/*8*/(new MasteryLevel<>(SpellMastery.class), new Analysis()),
			new PerkGroup/*9*/(new MasteryLevel<>(ShortBladeMastery.class), new SpellOrSpecialization(1, Shadow.class), new Discipline()),
			new PerkGroup/*10*/(new MasteryLevel<>(SpellMastery.class), new AbilitySpecialization(Technique.class)),
			new PerkGroup/*11*/(new MasteryLevel<>(SpearMastery.class), new SpellOrSpecialization(2, Shadow.class)),
			new PerkGroup/*12*/(new MasteryLevel<>(ShortBladeMastery.class), new MasteryLevel<>(SpellMastery.class)),
			new PerkGroup/*13*/(new SpellOrSpecialization(2, Shadow.class), new AbilitySpecialization(Technique.class)),
			new PerkGroup/*14*/(new MasteryLevel<>(SpearMastery.class), new MasteryLevel<>(SpellMastery.class)),
			new PerkGroup/*15*/(new SpellOrSpecialization(2, Shadow.class), new AbilitySpecialization(Technique.class), new Attunement()),
			new PerkGroup/*16*/(new MasteryLevel<>(SpearMastery.class), new MasteryLevel<>(SpellMastery.class)),
			new PerkGroup/*17*/(new MasteryLevel<>(ShortBladeMastery.class), new SpellOrSpecialization(2, Shadow.class)),
			new PerkGroup/*18*/(new MasteryLevel<>(SpellMastery.class), new AbilitySpecialization(Technique.class)),
			new PerkGroup/*19*/(new MasteryLevel<>(SpearMastery.class), new SpellOrSpecialization(2, Shadow.class)),
			new PerkGroup/*20*/(new MasteryLevel<>(ShortBladeMastery.class), new MasteryLevel<>(SpellMastery.class), new SpellOrSpecialization(3, Shadow.class))
		);
	}
}
