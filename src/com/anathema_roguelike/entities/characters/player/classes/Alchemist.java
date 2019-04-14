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

import com.anathema_roguelike.entities.characters.player.perks.abilities.potions.AmplificationElixir;
import com.anathema_roguelike.entities.characters.player.perks.abilities.potions.FireBomb;
import com.anathema_roguelike.entities.characters.player.perks.abilities.potions.FleetFootedElixir;
import com.anathema_roguelike.entities.characters.player.perks.abilities.potions.FreezeBomb;
import com.anathema_roguelike.entities.characters.player.perks.abilities.potions.KnockoutGasBomb;
import com.anathema_roguelike.entities.characters.player.perks.abilities.potions.RadiantBomb;
import com.anathema_roguelike.entities.characters.player.perks.abilities.potions.TarBomb;
import com.anathema_roguelike.entities.characters.player.perks.masteries.MasteryLevel;
import com.anathema_roguelike.entities.characters.player.perks.skills.GrandmasterBrewer;
import com.anathema_roguelike.entities.characters.player.perks.specializations.AbilitySpecialization;
import com.anathema_roguelike.stats.characterstats.masteries.BrewingMastery;
import com.anathema_roguelike.stats.characterstats.masteries.SpearMastery;

public class Alchemist extends PlayerClass {

	public Alchemist() {
		super(
			new PerkGroup/*1*/(new TarBomb(), new MasteryLevel<>(SpearMastery.class)),
			new PerkGroup/*2*/(new MasteryLevel<>(BrewingMastery.class), new FleetFootedElixir()),
			new PerkGroup/*3*/(new MasteryLevel<>(ThrowingWeaponMastery.class), new MasteryLevel<>(SpearMastery.class)),
			new PerkGroup/*4*/(new MasteryLevel<>(BrewingMastery.class), new KnockoutGasBomb()),
			new PerkGroup/*5*/(new AbilitySpecialization(Potion.class), new MasteryLevel<>(SpearMastery.class), new RadiantBomb()),
			new PerkGroup/*6*/(new MasteryLevel<>(BrewingMastery.class), new MasteryLevel<>(ThrowingWeaponMastery.class)),
			new PerkGroup/*7*/(new AbilitySpecialization(Potion.class), new AmplificationElixir()),
			new PerkGroup/*8*/(new MasteryLevel<>(BrewingMastery.class), new MasteryLevel<>(SpearMastery.class)),
			new PerkGroup/*9*/(new AbilitySpecialization(Potion.class), new FireBomb()),
			new PerkGroup/*10*/(new MasteryLevel<>(BrewingMastery.class), new GrandmasterBrewer()),
			new PerkGroup/*11*/(new AbilitySpecialization(Potion.class), new MasteryLevel<>(SpearMastery.class)),
			new PerkGroup/*12*/(new MasteryLevel<>(BrewingMastery.class), new MasteryLevel<>(ThrowingWeaponMastery.class)),
			new PerkGroup/*13*/(new AbilitySpecialization(Potion.class), new FreezeBomb()),
			new PerkGroup/*14*/(new MasteryLevel<>(BrewingMastery.class), new MasteryLevel<>(SpearMastery.class)),
			new PerkGroup/*15*/(new AbilitySpecialization(Potion.class), new MasteryLevel<>(ThrowingWeaponMastery.class)),
			new PerkGroup/*16*/(new AbilitySpecialization(Potion.class), new MasteryLevel<>(BrewingMastery.class)),
			new PerkGroup/*17*/(new AbilitySpecialization(Potion.class), new MasteryLevel<>(SpearMastery.class)),
			new PerkGroup/*18*/(new AbilitySpecialization(Potion.class), new MasteryLevel<>(BrewingMastery.class)),
			new PerkGroup/*19*/(new AbilitySpecialization(Potion.class), new MasteryLevel<>(ThrowingWeaponMastery.class)),
			new PerkGroup/*20*/(new MasteryLevel<>(BrewingMastery.class), new MasteryLevel<>(SpearMastery.class), new DisplacementBulwarkElixir())
		);
	}
}
