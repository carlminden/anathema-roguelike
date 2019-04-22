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
import com.anathema_roguelike.entities.characters.player.perks.abilities.potions.SlowingPoison
import com.anathema_roguelike.entities.characters.player.perks.abilities.techniques._
import com.anathema_roguelike.entities.characters.player.perks.masteries.MasteryLevel
import com.anathema_roguelike.entities.characters.player.perks.skills.KeenHearing
import com.anathema_roguelike.entities.characters.player.perks.skills.Trapfinding
import com.anathema_roguelike.entities.characters.player.perks.skills.UncannySenses
import com.anathema_roguelike.entities.characters.player.perks.specializations.AbilitySpecialization
import com.anathema_roguelike.stats.characterstats.masteries.BowMastery
import com.anathema_roguelike.stats.characterstats.masteries.DualWieldMastery
import com.anathema_roguelike.stats.characterstats.masteries.LongBladeMastery
import com.anathema_roguelike.stats.characterstats.masteries.ShortBladeMastery

class Rogue() extends PlayerClass(
  new PerkGroup /*1*/ (new MasteryLevel[ShortBladeMastery], new ThrowRock),
  new PerkGroup /*2*/ (new MasteryLevel[LongBladeMastery], new MasteryLevel[BowMastery]),
  new PerkGroup /*3*/ (new MasteryLevel[ShortBladeMastery], new Lockpicking),
  new PerkGroup /*4*/ (new MasteryLevel[DualWieldMastery], new Hide),
  new PerkGroup /*5*/ (new MasteryLevel[ShortBladeMastery], new MasteryLevel[BowMastery], new AbilitySpecialization[Technique[_]]),
  new PerkGroup /*6*/ (new MasteryLevel[DualWieldMastery], new LightStep),
  new PerkGroup /*7*/ (new MasteryLevel[LongBladeMastery], new AbilitySpecialization[Technique[_]]),
  new PerkGroup /*8*/ (new MasteryLevel[ShortBladeMastery], new Vault),
  new PerkGroup /*9*/ (new AbilitySpecialization[Technique[_]], new KeenHearing),
  new PerkGroup /*10*/ (new MasteryLevel[ShortBladeMastery], new MasteryLevel[LongBladeMastery], new Trapfinding),
  new PerkGroup /*11*/ (new MasteryLevel[DualWieldMastery], new AbilitySpecialization[Technique[_]]),
  new PerkGroup /*12*/ (new MasteryLevel[ShortBladeMastery], new MasteryLevel[BowMastery]),
  new PerkGroup /*13*/ (new AbilitySpecialization[Technique[_]], new Vanish),
  new PerkGroup /*14*/ (new MasteryLevel[LongBladeMastery], new MasteryLevel[DualWieldMastery]),
  new PerkGroup /*15*/ (new MasteryLevel[ShortBladeMastery], new AbilitySpecialization[Technique[_]], new SlowingPoison),
  new PerkGroup /*16*/ (new MasteryLevel[BowMastery], new AbilitySpecialization[Technique[_]]),
  new PerkGroup /*17*/ (new MasteryLevel[LongBladeMastery], new AbilitySpecialization[Technique[_]]),
  new PerkGroup /*18*/ (new MasteryLevel[ShortBladeMastery], new AbilitySpecialization[Technique[_]]),
  new PerkGroup /*19*/ (new MasteryLevel[DualWieldMastery], new AbilitySpecialization[Technique[_]]),
  new PerkGroup /*20*/ (new MasteryLevel[ShortBladeMastery], new MasteryLevel[BowMastery], new UncannySenses)) {
}