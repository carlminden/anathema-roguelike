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

package com.anathema_roguelike
package entities.characters.player.classes

import com.anathema_roguelike.entities.characters.perks.PerkGroup
import com.anathema_roguelike.entities.characters.player.perks.abilities.potions._
import com.anathema_roguelike.entities.characters.player.perks.masteries.MasteryLevel
import com.anathema_roguelike.entities.characters.player.perks.skills.GrandmasterBrewer
import com.anathema_roguelike.entities.characters.player.perks.specializations.AbilitySpecialization
import com.anathema_roguelike.stats.characterstats.masteries.BrewingMastery
import com.anathema_roguelike.stats.characterstats.masteries.SpearMastery
import com.anathema_roguelike.stats.characterstats.masteries.ThrowingWeaponMastery

class Alchemist() extends PlayerClass(
  new PerkGroup /*1*/ (new TarBomb, new MasteryLevel[SpearMastery]),
  new PerkGroup /*2*/ (new MasteryLevel[BrewingMastery], new FleetFootedElixir),
  new PerkGroup /*3*/ (new MasteryLevel[ThrowingWeaponMastery], new MasteryLevel[SpearMastery]),
  new PerkGroup /*4*/ (new MasteryLevel[BrewingMastery], new KnockoutGasBomb),
  new PerkGroup /*5*/ (new AbilitySpecialization[Potion[_]], new MasteryLevel[SpearMastery], new RadiantBomb),
  new PerkGroup /*6*/ (new MasteryLevel[BrewingMastery], new MasteryLevel[ThrowingWeaponMastery]),
  new PerkGroup /*7*/ (new AbilitySpecialization[Potion[_]], new AmplificationElixir),
  new PerkGroup /*8*/ (new MasteryLevel[BrewingMastery], new MasteryLevel[SpearMastery]),
  new PerkGroup /*9*/ (new AbilitySpecialization[Potion[_]], new FireBomb),
  new PerkGroup /*10*/ (new MasteryLevel[BrewingMastery], new GrandmasterBrewer),
  new PerkGroup /*11*/ (new AbilitySpecialization[Potion[_]], new MasteryLevel[SpearMastery]),
  new PerkGroup /*12*/ (new MasteryLevel[BrewingMastery], new MasteryLevel[ThrowingWeaponMastery]),
  new PerkGroup /*13*/ (new AbilitySpecialization[Potion[_]], new FreezeBomb),
  new PerkGroup /*14*/ (new MasteryLevel[BrewingMastery], new MasteryLevel[SpearMastery]),
  new PerkGroup /*15*/ (new AbilitySpecialization[Potion[_]], new MasteryLevel[ThrowingWeaponMastery]),
  new PerkGroup /*16*/ (new AbilitySpecialization[Potion[_]], new MasteryLevel[BrewingMastery]),
  new PerkGroup /*17*/ (new AbilitySpecialization[Potion[_]], new MasteryLevel[SpearMastery]),
  new PerkGroup /*18*/ (new AbilitySpecialization[Potion[_]], new MasteryLevel[BrewingMastery]),
  new PerkGroup /*19*/ (new AbilitySpecialization[Potion[_]], new MasteryLevel[ThrowingWeaponMastery]),
  new PerkGroup /*20*/ (new MasteryLevel[BrewingMastery], new MasteryLevel[SpearMastery], new DisplacementBulwarkElixir)) {
}