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
import com.anathema_roguelike.entities.characters.player.perks.abilities.potions.Potion
import com.anathema_roguelike.entities.characters.player.perks.abilities.potions.RadiantBomb
import com.anathema_roguelike.entities.characters.player.perks.abilities.potions.SlowingPoison
import com.anathema_roguelike.entities.characters.player.perks.abilities.potions.SmokeBomb
import com.anathema_roguelike.entities.characters.player.perks.abilities.techniques._
import com.anathema_roguelike.entities.characters.player.perks.masteries.MasteryLevel
import com.anathema_roguelike.entities.characters.player.perks.skills.SecondSight
import com.anathema_roguelike.entities.characters.player.perks.specializations.AbilitySpecialization
import com.anathema_roguelike.stats.characterstats.masteries.BrewingMastery
import com.anathema_roguelike.stats.characterstats.masteries.LongBladeMastery
import com.anathema_roguelike.stats.characterstats.masteries.ThrowingWeaponMastery
import com.anathema_roguelike.stats.characterstats.masteries.UnarmedMastery

class Ninja() extends PlayerClass(
  new PerkGroup /*1*/ (new MasteryLevel[LongBladeMastery], new KatanaExpertise),
  new PerkGroup /*2*/ (new MasteryLevel[UnarmedMastery], new LightStep),
  new PerkGroup /*3*/ (new MasteryLevel[ThrowingWeaponMastery], new RadiantBomb),
  new PerkGroup /*4*/ (new MasteryLevel[BrewingMastery], new Hide),
  new PerkGroup /*5*/ (new MasteryLevel[LongBladeMastery], new MasteryLevel[UnarmedMastery], new AbilitySpecialization[Technique[_]]),
  new PerkGroup /*6*/ (new MasteryLevel[BrewingMastery], new SlowingPoison),
  new PerkGroup /*7*/ (new MasteryLevel[LongBladeMastery], new AbilitySpecialization[Potion[_]]),
  new PerkGroup /*8*/ (new MasteryLevel[BrewingMastery], new WallRunning),
  new PerkGroup /*9*/ (new AbilitySpecialization[Technique[_]], new MasteryLevel[ThrowingWeaponMastery]),
  new PerkGroup /*10*/ (new MasteryLevel[LongBladeMastery], new MasteryLevel[UnarmedMastery], new LeapingStrike),
  new PerkGroup /*11*/ (new AbilitySpecialization[Potion[_]], new HurlBlade),
  new PerkGroup /*12*/ (new MasteryLevel[ThrowingWeaponMastery], new SmokeBomb),
  new PerkGroup /*13*/ (new AbilitySpecialization[Technique[_]], new MasteryLevel[BrewingMastery]),
  new PerkGroup /*14*/ (new MasteryLevel[LongBladeMastery], new MasteryLevel[UnarmedMastery]),
  new PerkGroup /*15*/ (new AbilitySpecialization[Potion[_]], new MasteryLevel[BrewingMastery]),
  new PerkGroup /*16*/ (new MasteryLevel[LongBladeMastery], new AbilitySpecialization[Technique[_]]),
  new PerkGroup /*17*/ (new AbilitySpecialization[Potion[_]], new MasteryLevel[ThrowingWeaponMastery]),
  new PerkGroup /*18*/ (new MasteryLevel[LongBladeMastery], new AbilitySpecialization[Technique[_]]),
  new PerkGroup /*19*/ (new AbilitySpecialization[Potion[_]], new MasteryLevel[BrewingMastery]),
  new PerkGroup /*20*/ (new MasteryLevel[LongBladeMastery], new MasteryLevel[UnarmedMastery], new SecondSight)) {
}