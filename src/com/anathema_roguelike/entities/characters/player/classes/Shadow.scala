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
import com.anathema_roguelike.entities.characters.player.perks.abilities.techniques.{Hide, Shadowdance, Technique}
import com.anathema_roguelike.entities.characters.player.perks.masteries.MasteryLevel
import com.anathema_roguelike.entities.characters.player.perks.skills.AffinityForDarkness
import com.anathema_roguelike.entities.characters.player.perks.skills.Analysis
import com.anathema_roguelike.entities.characters.player.perks.skills.Attunement
import com.anathema_roguelike.entities.characters.player.perks.skills.Discipline
import com.anathema_roguelike.entities.characters.player.perks.specializations.AbilitySpecialization
import com.anathema_roguelike.stats.characterstats.masteries.ShortBladeMastery
import com.anathema_roguelike.stats.characterstats.masteries.SpearMastery
import com.anathema_roguelike.stats.characterstats.masteries.SpellMastery

class Shadow() extends PlayerClass(
  new PerkGroup /*1*/ (new MasteryLevel[SpearMastery], new SpellOrSpecialization[Shadow](1)),
  new PerkGroup /*2*/ (new MasteryLevel[SpellMastery], new MasteryLevel[ShortBladeMastery]),
  new PerkGroup /*3*/ (new SpellOrSpecialization[Shadow](1), new AffinityForDarkness),
  new PerkGroup /*4*/ (new MasteryLevel[SpellMastery], new Hide),
  new PerkGroup /*5*/ (new MasteryLevel[SpearMastery], new MasteryLevel[ShortBladeMastery], new SpellOrSpecialization[Shadow](1)),
  new PerkGroup /*6*/ (new MasteryLevel[SpellMastery], new Shadowdance),
  new PerkGroup /*7*/ (new MasteryLevel[SpearMastery], new SpellOrSpecialization[Shadow](1)),
  new PerkGroup /*8*/ (new MasteryLevel[SpellMastery], new Analysis),
  new PerkGroup /*9*/ (new MasteryLevel[ShortBladeMastery], new SpellOrSpecialization[Shadow](1), new Discipline),
  new PerkGroup /*10*/ (new MasteryLevel[SpellMastery], new AbilitySpecialization[Technique[_]]),
  new PerkGroup /*11*/ (new MasteryLevel[SpearMastery], new SpellOrSpecialization[Shadow](2)),
  new PerkGroup /*12*/ (new MasteryLevel[ShortBladeMastery], new MasteryLevel[SpellMastery]),
  new PerkGroup /*13*/ (new SpellOrSpecialization[Shadow](2), new AbilitySpecialization[Technique[_]]),
  new PerkGroup /*14*/ (new MasteryLevel[SpearMastery], new MasteryLevel[SpellMastery]),
  new PerkGroup /*15*/ (new SpellOrSpecialization[Shadow](2), new AbilitySpecialization[Technique[_]], new Attunement),
  new PerkGroup /*16*/ (new MasteryLevel[SpearMastery], new MasteryLevel[SpellMastery]),
  new PerkGroup /*17*/ (new MasteryLevel[ShortBladeMastery], new SpellOrSpecialization[Shadow](2)),
  new PerkGroup /*18*/ (new MasteryLevel[SpellMastery], new AbilitySpecialization[Technique[_]]),
  new PerkGroup /*19*/ (new MasteryLevel[SpearMastery], new SpellOrSpecialization[Shadow](2)),
  new PerkGroup /*20*/ (new MasteryLevel[ShortBladeMastery], new MasteryLevel[SpellMastery], new SpellOrSpecialization[Shadow](3))) {
}