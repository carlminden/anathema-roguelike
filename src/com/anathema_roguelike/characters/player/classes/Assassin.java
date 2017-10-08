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
import com.anathema_roguelike.characters.player.perks.abilities.techniques.Assassinate;
import com.anathema_roguelike.characters.player.perks.abilities.techniques.HiddenStrikes;
import com.anathema_roguelike.characters.player.perks.abilities.techniques.Hide;
import com.anathema_roguelike.characters.player.perks.abilities.techniques.LightStep;
import com.anathema_roguelike.characters.player.perks.abilities.techniques.Lockpicking;
import com.anathema_roguelike.characters.player.perks.abilities.techniques.QuietAssassination;
import com.anathema_roguelike.characters.player.perks.abilities.techniques.Technique;
import com.anathema_roguelike.characters.player.perks.masteries.MasteryLevel;
import com.anathema_roguelike.characters.player.perks.skills.AffinityForDarkness;
import com.anathema_roguelike.characters.player.perks.specializations.AbilitySpecialization;
import com.anathema_roguelike.stats.characterstats.masteries.CrossbowMastery;
import com.anathema_roguelike.stats.characterstats.masteries.DualWieldMastery;
import com.anathema_roguelike.stats.characterstats.masteries.LongBladeMastery;
import com.anathema_roguelike.stats.characterstats.masteries.ShortBladeMastery;
import com.anathema_roguelike.stats.characterstats.masteries.SpellMastery;

public class Assassin extends CharacterClass {

	public Assassin() {
		super(
			new PerkGroup/*1*/(new MasteryLevel<>(ShortBladeMastery.class), new HiddenStrikes()),
			new PerkGroup/*2*/(new SpellOrSpecialization(1, Shadow.class), new MasteryLevel<>(CrossbowMastery.class)),
			new PerkGroup/*3*/(new MasteryLevel<>(ShortBladeMastery.class), new Hide()),
			new PerkGroup/*4*/(new AbilitySpecialization(Technique.class), new MasteryLevel<>(SpellMastery.class)),
			new PerkGroup/*5*/(new SpellOrSpecialization(1, Shadow.class), new MasteryLevel<>(LongBladeMastery.class), new AffinityForDarkness()),
			new PerkGroup/*6*/(new AbilitySpecialization(Technique.class), new MasteryLevel<>(DualWieldMastery.class)),
			new PerkGroup/*7*/(new MasteryLevel<>(ShortBladeMastery.class), new MasteryLevel<>(SpellMastery.class)),
			new PerkGroup/*8*/(new SpellOrSpecialization(1, Shadow.class), new LightStep()),
			new PerkGroup/*9*/(new MasteryLevel<>(CrossbowMastery.class), new AbilitySpecialization(Technique.class)),
			new PerkGroup/*10*/(new SpellOrSpecialization(1, Shadow.class), new MasteryLevel<>(SpellMastery.class), new Lockpicking()),
			new PerkGroup/*11*/(new MasteryLevel<>(ShortBladeMastery.class), new AbilitySpecialization(Technique.class)),
			new PerkGroup/*12*/(new SpellOrSpecialization(1, Shadow.class), new MasteryLevel<>(LongBladeMastery.class)),
			new PerkGroup/*13*/(new MasteryLevel<>(SpellMastery.class), new MasteryLevel<>(DualWieldMastery.class)),
			new PerkGroup/*14*/(new MasteryLevel<>(CrossbowMastery.class), new AbilitySpecialization(Technique.class)),
			new PerkGroup/*15*/(new SpellOrSpecialization(2, Shadow.class), new MasteryLevel<>(LongBladeMastery.class), new QuietAssassination()),
			new PerkGroup/*16*/(new MasteryLevel<>(ShortBladeMastery.class), new MasteryLevel<>(SpellMastery.class)),
			new PerkGroup/*17*/(new AbilitySpecialization(Technique.class), new MasteryLevel<>(LongBladeMastery.class)),
			new PerkGroup/*18*/(new MasteryLevel<>(CrossbowMastery.class), new SpellOrSpecialization(2, Shadow.class)),
			new PerkGroup/*19*/(new MasteryLevel<>(ShortBladeMastery.class), new MasteryLevel<>(SpellMastery.class)),
			new PerkGroup/*20*/(new SpellOrSpecialization(2, Shadow.class), new MasteryLevel<>(SpellMastery.class), new Assassinate())
		);
	}
}
