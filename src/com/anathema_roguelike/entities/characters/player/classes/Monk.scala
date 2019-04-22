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
import com.anathema_roguelike.entities.characters.player.perks.abilities.techniques._
import com.anathema_roguelike.entities.characters.player.perks.masteries.MasteryLevel
import com.anathema_roguelike.entities.characters.player.perks.skills.IronBody
import com.anathema_roguelike.entities.characters.player.perks.skills.SureFooting
import com.anathema_roguelike.entities.characters.player.perks.skills.Swiftness
import com.anathema_roguelike.entities.characters.player.perks.specializations.AbilitySpecialization
import com.anathema_roguelike.stats.characterstats.masteries.BrewingMastery
import com.anathema_roguelike.stats.characterstats.masteries.UnarmedMastery

class Monk() extends PlayerClass(
  new PerkGroup /*1*/ (new MasteryLevel[UnarmedMastery], new UnfetteredSpirit),
  new PerkGroup /*2*/ (new MasteryLevel[BrewingMastery], new PerceptiveElixir),
  new PerkGroup /*3*/ (new MasteryLevel[UnarmedMastery], new FlurryOfBlows),
  new PerkGroup /*4*/ (new AbilitySpecialization[Technique[_]], new LightStep),
  new PerkGroup /*5*/ (new MasteryLevel[UnarmedMastery], new MasteryLevel[BrewingMastery], new Hide),
  new PerkGroup /*6*/ (new AbilitySpecialization[Technique[_]], new FortificationElixir),
  new PerkGroup /*7*/ (new MasteryLevel[UnarmedMastery], new AbilitySpecialization[Potion[_]]),
  new PerkGroup /*8*/ (new AbilitySpecialization[Technique[_]], new FlyingKick),
  new PerkGroup /*9*/ (new MasteryLevel[UnarmedMastery], new AbilitySpecialization[Potion[_]]),
  new PerkGroup /*10*/ (new AbilitySpecialization[Technique[_]], new ElixirOfAzureFlame, new SureFooting),
  new PerkGroup /*11*/ (new MasteryLevel[UnarmedMastery], new AbilitySpecialization[Potion[_]]),
  new PerkGroup /*12*/ (new AbilitySpecialization[Technique[_]], new Swiftness),
  new PerkGroup /*13*/ (new MasteryLevel[UnarmedMastery], new MasteryLevel[BrewingMastery]),
  new PerkGroup /*14*/ (new AbilitySpecialization[Technique[_]], new ElixirOfPurity),
  new PerkGroup /*15*/ (new MasteryLevel[BrewingMastery], new AbilitySpecialization[Potion[_]], new HiddenStrikes),
  new PerkGroup /*16*/ (new MasteryLevel[UnarmedMastery], new AbilitySpecialization[Technique[_]]),
  new PerkGroup /*17*/ (new MasteryLevel[BrewingMastery], new AbilitySpecialization[Potion[_]]),
  new PerkGroup /*18*/ (new MasteryLevel[UnarmedMastery], new AbilitySpecialization[Technique[_]]),
  new PerkGroup /*19*/ (new MasteryLevel[BrewingMastery], new AbilitySpecialization[Potion[_]]),
  new PerkGroup /*20*/ (new MasteryLevel[UnarmedMastery], new AbilitySpecialization[Technique[_]], new IronBody)) {
}