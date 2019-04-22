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
import com.anathema_roguelike.entities.characters.player.perks.abilities.techniques.{Hide, PointBlankExpertise, QuickDraw, Technique}
import com.anathema_roguelike.entities.characters.player.perks.masteries.MasteryLevel
import com.anathema_roguelike.entities.characters.player.perks.skills._
import com.anathema_roguelike.entities.characters.player.perks.specializations.AbilitySpecialization
import com.anathema_roguelike.stats.characterstats.masteries.BowMastery
import com.anathema_roguelike.stats.characterstats.masteries.CrossbowMastery
import com.anathema_roguelike.stats.characterstats.masteries.SpellMastery

class Ranger() extends PlayerClass(
  new PerkGroup /*1*/ (new MasteryLevel[BowMastery], new PointBlankExpertise),
  new PerkGroup /*2*/ (new MasteryLevel[CrossbowMastery], new SpellOrSpecialization[Druid](1)),
  new PerkGroup /*3*/ (new MasteryLevel[BowMastery], new Hide),
  new PerkGroup /*4*/ (new MasteryLevel[SpellMastery], new Analysis),
  new PerkGroup /*5*/ (new MasteryLevel[BowMastery], new SpellOrSpecialization[Druid](1), new Trapfinding),
  new PerkGroup /*6*/ (new MasteryLevel[CrossbowMastery], new AbilitySpecialization[Technique[_]]),
  new PerkGroup /*7*/ (new MasteryLevel[BowMastery], new MasteryLevel[SpellMastery]),
  new PerkGroup /*8*/ (new SpellOrSpecialization[Druid](1), new QuickDraw),
  new PerkGroup /*9*/ (new MasteryLevel[BowMastery], new AbilitySpecialization[Technique[_]]),
  new PerkGroup /*10*/ (new MasteryLevel[CrossbowMastery], new MasteryLevel[SpellMastery], new SpellOrSpecialization[Druid](1)),
  new PerkGroup /*11*/ (new MasteryLevel[BowMastery], new AbilitySpecialization[Technique[_]]),
  new PerkGroup /*12*/ (new SpellOrSpecialization[Druid](2), new SureFooting),
  new PerkGroup /*13*/ (new MasteryLevel[BowMastery], new MasteryLevel[SpellMastery]),
  new PerkGroup /*14*/ (new AbilitySpecialization[Technique[_]], new EagleEye),
  new PerkGroup /*15*/ (new MasteryLevel[BowMastery], new MasteryLevel[CrossbowMastery], new SpellOrSpecialization[Druid](2)),
  new PerkGroup /*16*/ (new MasteryLevel[SpellMastery], new Swiftness),
  new PerkGroup /*17*/ (new MasteryLevel[BowMastery], new AbilitySpecialization[Technique[_]]),
  new PerkGroup /*18*/ (new MasteryLevel[CrossbowMastery], new SpellOrSpecialization[Druid](2)),
  new PerkGroup /*19*/ (new MasteryLevel[BowMastery], new MasteryLevel[SpellMastery]),
  new PerkGroup /*20*/ (new SpellOrSpecialization[Druid](3), new MasteryLevel[SpellMastery], new Autoload)) {
}