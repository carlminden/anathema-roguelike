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
import com.anathema_roguelike.characters.player.perks.abilities.potions.ElixirOfAzureFlame;
import com.anathema_roguelike.characters.player.perks.abilities.potions.ElixirOfPurity;
import com.anathema_roguelike.characters.player.perks.abilities.potions.FortificationElixir;
import com.anathema_roguelike.characters.player.perks.abilities.potions.PerceptiveElixir;
import com.anathema_roguelike.characters.player.perks.abilities.potions.Potion;
import com.anathema_roguelike.characters.player.perks.abilities.techniques.FlurryOfBlows;
import com.anathema_roguelike.characters.player.perks.abilities.techniques.FlyingKick;
import com.anathema_roguelike.characters.player.perks.abilities.techniques.HiddenStrikes;
import com.anathema_roguelike.characters.player.perks.abilities.techniques.Hide;
import com.anathema_roguelike.characters.player.perks.abilities.techniques.LightStep;
import com.anathema_roguelike.characters.player.perks.abilities.techniques.Technique;
import com.anathema_roguelike.characters.player.perks.abilities.techniques.UnfetteredSpirit;
import com.anathema_roguelike.characters.player.perks.masteries.MasteryLevel;
import com.anathema_roguelike.characters.player.perks.skills.IronBody;
import com.anathema_roguelike.characters.player.perks.skills.SureFooting;
import com.anathema_roguelike.characters.player.perks.skills.Swiftness;
import com.anathema_roguelike.characters.player.perks.specializations.AbilitySpecialization;
import com.anathema_roguelike.stats.characterstats.masteries.BrewingMastery;
import com.anathema_roguelike.stats.characterstats.masteries.UnarmedMastery;

public class Monk extends CharacterClass {

	public Monk() {
		super(
			new PerkGroup/*1*/(new MasteryLevel<>(UnarmedMastery.class), new UnfetteredSpirit()),
			new PerkGroup/*2*/(new MasteryLevel<>(BrewingMastery.class), new PerceptiveElixir()),
			new PerkGroup/*3*/(new MasteryLevel<>(UnarmedMastery.class), new FlurryOfBlows()),
			new PerkGroup/*4*/(new AbilitySpecialization(Technique.class), new LightStep()),
			new PerkGroup/*5*/(new MasteryLevel<>(UnarmedMastery.class), new MasteryLevel<>(BrewingMastery.class), new Hide()),
			new PerkGroup/*6*/(new AbilitySpecialization(Technique.class), new FortificationElixir()),
			new PerkGroup/*7*/(new MasteryLevel<>(UnarmedMastery.class), new AbilitySpecialization(Potion.class)),
			new PerkGroup/*8*/(new AbilitySpecialization(Technique.class), new FlyingKick()),
			new PerkGroup/*9*/(new MasteryLevel<>(UnarmedMastery.class), new AbilitySpecialization(Potion.class)),
			new PerkGroup/*10*/(new AbilitySpecialization(Technique.class), new ElixirOfAzureFlame(), new SureFooting()),
			new PerkGroup/*11*/(new MasteryLevel<>(UnarmedMastery.class), new AbilitySpecialization(Potion.class)),
			new PerkGroup/*12*/(new AbilitySpecialization(Technique.class), new Swiftness()),
			new PerkGroup/*13*/(new MasteryLevel<>(UnarmedMastery.class), new MasteryLevel<>(BrewingMastery.class)),
			new PerkGroup/*14*/(new AbilitySpecialization(Technique.class), new ElixirOfPurity()),
			new PerkGroup/*15*/(new MasteryLevel<>(BrewingMastery.class), new AbilitySpecialization(Potion.class), new HiddenStrikes()),
			new PerkGroup/*16*/(new MasteryLevel<>(UnarmedMastery.class), new AbilitySpecialization(Technique.class)),
			new PerkGroup/*17*/(new MasteryLevel<>(BrewingMastery.class), new AbilitySpecialization(Potion.class)),
			new PerkGroup/*18*/(new MasteryLevel<>(UnarmedMastery.class), new AbilitySpecialization(Technique.class)),
			new PerkGroup/*19*/(new MasteryLevel<>(BrewingMastery.class), new AbilitySpecialization(Potion.class)),
			new PerkGroup/*20*/(new MasteryLevel<>(UnarmedMastery.class), new AbilitySpecialization(Technique.class), new IronBody())
		);
	}
}
