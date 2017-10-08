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
import com.anathema_roguelike.characters.player.perks.abilities.potions.Potion;
import com.anathema_roguelike.characters.player.perks.abilities.potions.RadiantBomb;
import com.anathema_roguelike.characters.player.perks.abilities.potions.SlowingPoison;
import com.anathema_roguelike.characters.player.perks.abilities.potions.SmokeBomb;
import com.anathema_roguelike.characters.player.perks.abilities.techniques.Hide;
import com.anathema_roguelike.characters.player.perks.abilities.techniques.HurlBlade;
import com.anathema_roguelike.characters.player.perks.abilities.techniques.KatanaExpertise;
import com.anathema_roguelike.characters.player.perks.abilities.techniques.LeapingStrike;
import com.anathema_roguelike.characters.player.perks.abilities.techniques.LightStep;
import com.anathema_roguelike.characters.player.perks.abilities.techniques.Technique;
import com.anathema_roguelike.characters.player.perks.abilities.techniques.WallRunning;
import com.anathema_roguelike.characters.player.perks.masteries.MasteryLevel;
import com.anathema_roguelike.characters.player.perks.skills.SecondSight;
import com.anathema_roguelike.characters.player.perks.specializations.AbilitySpecialization;
import com.anathema_roguelike.stats.characterstats.masteries.BrewingMastery;
import com.anathema_roguelike.stats.characterstats.masteries.LongBladeMastery;
import com.anathema_roguelike.stats.characterstats.masteries.ThrowingWeaponMastery;
import com.anathema_roguelike.stats.characterstats.masteries.UnarmedMastery;

public class Ninja extends CharacterClass {

	public Ninja() {
		super(
			new PerkGroup/*1*/(new MasteryLevel<>(LongBladeMastery.class), new KatanaExpertise()),
			new PerkGroup/*2*/(new MasteryLevel<>(UnarmedMastery.class), new LightStep()),
			new PerkGroup/*3*/(new MasteryLevel<>(ThrowingWeaponMastery.class), new RadiantBomb()),
			new PerkGroup/*4*/(new MasteryLevel<>(BrewingMastery.class), new Hide()),
			new PerkGroup/*5*/(new MasteryLevel<>(LongBladeMastery.class), new MasteryLevel<>(UnarmedMastery.class), new AbilitySpecialization(Technique.class)),
			new PerkGroup/*6*/(new MasteryLevel<>(BrewingMastery.class), new SlowingPoison()),
			new PerkGroup/*7*/(new MasteryLevel<>(LongBladeMastery.class), new AbilitySpecialization(Potion.class)),
			new PerkGroup/*8*/(new MasteryLevel<>(BrewingMastery.class), new WallRunning()),
			new PerkGroup/*9*/(new AbilitySpecialization(Technique.class), new MasteryLevel<>(ThrowingWeaponMastery.class)),
			new PerkGroup/*10*/(new MasteryLevel<>(LongBladeMastery.class), new MasteryLevel<>(UnarmedMastery.class), new LeapingStrike()),
			new PerkGroup/*11*/(new AbilitySpecialization(Potion.class), new HurlBlade()),
			new PerkGroup/*12*/(new MasteryLevel<>(ThrowingWeaponMastery.class), new SmokeBomb()),
			new PerkGroup/*13*/(new AbilitySpecialization(Technique.class), new MasteryLevel<>(BrewingMastery.class)),
			new PerkGroup/*14*/(new MasteryLevel<>(LongBladeMastery.class), new MasteryLevel<>(UnarmedMastery.class)),
			new PerkGroup/*15*/(new AbilitySpecialization(Potion.class), new MasteryLevel<>(BrewingMastery.class)),
			new PerkGroup/*16*/(new MasteryLevel<>(LongBladeMastery.class), new AbilitySpecialization(Technique.class)),
			new PerkGroup/*17*/(new AbilitySpecialization(Potion.class), new MasteryLevel<>(ThrowingWeaponMastery.class)),
			new PerkGroup/*18*/(new MasteryLevel<>(LongBladeMastery.class), new AbilitySpecialization(Technique.class)),
			new PerkGroup/*19*/(new AbilitySpecialization(Potion.class), new MasteryLevel<>(BrewingMastery.class)),
			new PerkGroup/*20*/(new MasteryLevel<>(LongBladeMastery.class), new MasteryLevel<>(UnarmedMastery.class), new SecondSight())
		);
	}
}
