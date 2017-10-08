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
import com.anathema_roguelike.characters.player.perks.abilities.shapeshifting.Shapeshifting;
import com.anathema_roguelike.characters.player.perks.abilities.shapeshifting.ShapeshiftingBat;
import com.anathema_roguelike.characters.player.perks.abilities.shapeshifting.ShapeshiftingPanther;
import com.anathema_roguelike.characters.player.perks.abilities.spells.SpellOrSpecialization;
import com.anathema_roguelike.characters.player.perks.masteries.MasteryLevel;
import com.anathema_roguelike.characters.player.perks.skills.AnimalisticCasting;
import com.anathema_roguelike.characters.player.perks.skills.Attunement;
import com.anathema_roguelike.characters.player.perks.skills.Discipline;
import com.anathema_roguelike.characters.player.perks.skills.SureFooting;
import com.anathema_roguelike.characters.player.perks.specializations.AbilitySpecialization;
import com.anathema_roguelike.stats.characterstats.masteries.BluntWeaponMastery;
import com.anathema_roguelike.stats.characterstats.masteries.ShapeshiftingMastery;
import com.anathema_roguelike.stats.characterstats.masteries.SpellMastery;

public class Druid extends CharacterClass {

	public Druid() {
		super(
			new PerkGroup/*1*/(new SpellOrSpecialization(1, Druid.class), new ShapeshiftingPanther()),
			new PerkGroup/*2*/(new SpellOrSpecialization(1, Druid.class), new MasteryLevel<>(SpellMastery.class)),
			new PerkGroup/*3*/(new MasteryLevel<>(BluntWeaponMastery.class), new MasteryLevel<>(ShapeshiftingMastery.class)),
			new PerkGroup/*4*/(new SpellOrSpecialization(1, Druid.class), new MasteryLevel<>(SpellMastery.class)),
			new PerkGroup/*5*/(new SpellOrSpecialization(1, Druid.class), new MasteryLevel<>(BluntWeaponMastery.class), new MasteryLevel<>(ShapeshiftingMastery.class)),
			new PerkGroup/*6*/(new SpellOrSpecialization(1, Druid.class), new MasteryLevel<>(SpellMastery.class)),
			new PerkGroup/*7*/(new SpellOrSpecialization(2, Druid.class), new ShapeshiftingBat()),
			new PerkGroup/*8*/(new MasteryLevel<>(SpellMastery.class), new AbilitySpecialization(Shapeshifting.class)),
			new PerkGroup/*9*/(new SpellOrSpecialization(2, Druid.class), new MasteryLevel<>(ShapeshiftingMastery.class)),
			new PerkGroup/*10*/(new SpellOrSpecialization(2, Druid.class), new MasteryLevel<>(SpellMastery.class), new Discipline()),
			new PerkGroup/*11*/(new MasteryLevel<>(ShapeshiftingMastery.class), new AbilitySpecialization(Shapeshifting.class)),
			new PerkGroup/*12*/(new SpellOrSpecialization(2, Druid.class)),
			new PerkGroup/*13*/(new MasteryLevel<>(ShapeshiftingMastery.class), new Attunement()),
			new PerkGroup/*14*/(new SpellOrSpecialization(3, Druid.class), new MasteryLevel<>(SpellMastery.class)),
			new PerkGroup/*15*/(new MasteryLevel<>(BluntWeaponMastery.class), new AbilitySpecialization(Shapeshifting.class), new SureFooting()),
			new PerkGroup/*16*/(new SpellOrSpecialization(3, Druid.class), new MasteryLevel<>(ShapeshiftingMastery.class)),
			new PerkGroup/*17*/(new MasteryLevel<>(SpellMastery.class), new AbilitySpecialization(Shapeshifting.class)),
			new PerkGroup/*18*/(new SpellOrSpecialization(3, Druid.class), new MasteryLevel<>(SpellMastery.class)),
			new PerkGroup/*19*/(new MasteryLevel<>(SpellMastery.class), new MasteryLevel<>(ShapeshiftingMastery.class)),
			new PerkGroup/*20*/(new SpellOrSpecialization(4, Druid.class), new MasteryLevel<>(BluntWeaponMastery.class), new AnimalisticCasting())
		);
	}
}