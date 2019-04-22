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
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.SpellOrSpecialization
import com.anathema_roguelike.entities.characters.player.perks.abilities.techniques._
import com.anathema_roguelike.entities.characters.player.perks.masteries.MasteryLevel
import com.anathema_roguelike.entities.characters.player.perks.skills.AffinityForDarkness
import com.anathema_roguelike.entities.characters.player.perks.specializations.AbilitySpecialization
import com.anathema_roguelike.stats.characterstats.masteries._

class Assassin() extends PlayerClass(
  new PerkGroup /*1*/ (new MasteryLevel[ShortBladeMastery], new HiddenStrikes),
  new PerkGroup /*2*/ (new SpellOrSpecialization[Shadow](1), new MasteryLevel[CrossbowMastery]),
  new PerkGroup /*3*/ (new MasteryLevel[ShortBladeMastery], new Hide),
  new PerkGroup /*4*/ (new AbilitySpecialization[Technique[_]], new MasteryLevel[SpellMastery]),
  new PerkGroup /*5*/ (new SpellOrSpecialization[Shadow](1), new MasteryLevel[LongBladeMastery], new AffinityForDarkness),
  new PerkGroup /*6*/ (new AbilitySpecialization[Technique[_]], new MasteryLevel[DualWieldMastery]),
  new PerkGroup /*7*/ (new MasteryLevel[ShortBladeMastery], new MasteryLevel[SpellMastery]),
  new PerkGroup /*8*/ (new SpellOrSpecialization[Shadow](1), new LightStep),
  new PerkGroup /*9*/ (new MasteryLevel[CrossbowMastery], new AbilitySpecialization[Technique[_]]),
  new PerkGroup /*10*/ (new SpellOrSpecialization[Shadow](1), new MasteryLevel[SpellMastery], new Lockpicking),
  new PerkGroup /*11*/ (new MasteryLevel[ShortBladeMastery], new AbilitySpecialization[Technique[_]]),
  new PerkGroup /*12*/ (new SpellOrSpecialization[Shadow](1), new MasteryLevel[LongBladeMastery]),
  new PerkGroup /*13*/ (new MasteryLevel[SpellMastery], new MasteryLevel[DualWieldMastery]),
  new PerkGroup /*14*/ (new MasteryLevel[CrossbowMastery], new AbilitySpecialization[Technique[_]]),
  new PerkGroup /*15*/ (new SpellOrSpecialization[Shadow](2), new MasteryLevel[LongBladeMastery], new QuietAssassination),
  new PerkGroup /*16*/ (new MasteryLevel[ShortBladeMastery], new MasteryLevel[SpellMastery]),
  new PerkGroup /*17*/ (new AbilitySpecialization[Technique[_]], new MasteryLevel[LongBladeMastery]),
  new PerkGroup /*18*/ (new MasteryLevel[CrossbowMastery], new SpellOrSpecialization[Shadow](2)),
  new PerkGroup /*19*/ (new MasteryLevel[ShortBladeMastery], new MasteryLevel[SpellMastery]),
  new PerkGroup /*20*/ (new SpellOrSpecialization[Shadow](2), new MasteryLevel[SpellMastery], new Assassinate)) {
}