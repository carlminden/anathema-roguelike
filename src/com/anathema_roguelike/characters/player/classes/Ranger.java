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
import com.anathema_roguelike.characters.player.perks.abilities.techniques.Hide;
import com.anathema_roguelike.characters.player.perks.abilities.techniques.PointBlankExpertise;
import com.anathema_roguelike.characters.player.perks.abilities.techniques.QuickDraw;
import com.anathema_roguelike.characters.player.perks.abilities.techniques.Technique;
import com.anathema_roguelike.characters.player.perks.masteries.MasteryLevel;
import com.anathema_roguelike.characters.player.perks.skills.Analysis;
import com.anathema_roguelike.characters.player.perks.skills.Autoload;
import com.anathema_roguelike.characters.player.perks.skills.EagleEye;
import com.anathema_roguelike.characters.player.perks.skills.SureFooting;
import com.anathema_roguelike.characters.player.perks.skills.Swiftness;
import com.anathema_roguelike.characters.player.perks.skills.Trapfinding;
import com.anathema_roguelike.characters.player.perks.specializations.AbilitySpecialization;
import com.anathema_roguelike.stats.characterstats.masteries.BowMastery;
import com.anathema_roguelike.stats.characterstats.masteries.CrossbowMastery;
import com.anathema_roguelike.stats.characterstats.masteries.SpellMastery;

public class Ranger extends CharacterClass {

	public Ranger() {
		super(
			new PerkGroup/*1*/(new MasteryLevel<>(BowMastery.class), new PointBlankExpertise()),
			new PerkGroup/*2*/(new MasteryLevel<>(CrossbowMastery.class), new SpellOrSpecialization(1, Druid.class)),
			new PerkGroup/*3*/(new MasteryLevel<>(BowMastery.class), new Hide()),
			new PerkGroup/*4*/(new MasteryLevel<>(SpellMastery.class), new Analysis()),
			new PerkGroup/*5*/(new MasteryLevel<>(BowMastery.class), new SpellOrSpecialization(1, Druid.class), new Trapfinding()),
			new PerkGroup/*6*/(new MasteryLevel<>(CrossbowMastery.class), new AbilitySpecialization(Technique.class)),
			new PerkGroup/*7*/(new MasteryLevel<>(BowMastery.class), new MasteryLevel<>(SpellMastery.class)),
			new PerkGroup/*8*/(new SpellOrSpecialization(1, Druid.class), new QuickDraw()),
			new PerkGroup/*9*/(new MasteryLevel<>(BowMastery.class), new AbilitySpecialization(Technique.class)),
			new PerkGroup/*10*/(new MasteryLevel<>(CrossbowMastery.class), new MasteryLevel<>(SpellMastery.class), new SpellOrSpecialization(1, Druid.class)),
			new PerkGroup/*11*/(new MasteryLevel<>(BowMastery.class), new AbilitySpecialization(Technique.class)),
			new PerkGroup/*12*/(new SpellOrSpecialization(2, Druid.class), new SureFooting()),
			new PerkGroup/*13*/(new MasteryLevel<>(BowMastery.class), new MasteryLevel<>(SpellMastery.class)),
			new PerkGroup/*14*/(new AbilitySpecialization(Technique.class), new EagleEye()),
			new PerkGroup/*15*/(new MasteryLevel<>(BowMastery.class), new MasteryLevel<>(CrossbowMastery.class), new SpellOrSpecialization(2, Druid.class)),
			new PerkGroup/*16*/(new MasteryLevel<>(SpellMastery.class), new Swiftness()),
			new PerkGroup/*17*/(new MasteryLevel<>(BowMastery.class), new AbilitySpecialization(Technique.class)),
			new PerkGroup/*18*/(new MasteryLevel<>(CrossbowMastery.class), new SpellOrSpecialization(2, Druid.class)),
			new PerkGroup/*19*/(new MasteryLevel<>(BowMastery.class), new MasteryLevel<>(SpellMastery.class)),
			new PerkGroup/*20*/(new SpellOrSpecialization(3, Druid.class), new MasteryLevel<>(SpellMastery.class), new Autoload())
		);
	}
}
