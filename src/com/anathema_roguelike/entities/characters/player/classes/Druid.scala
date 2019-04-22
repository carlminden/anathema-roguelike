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
import com.anathema_roguelike.entities.characters.player.perks.abilities.shapeshifting.Shapeshift
import com.anathema_roguelike.entities.characters.player.perks.abilities.shapeshifting.ShapeshiftBat
import com.anathema_roguelike.entities.characters.player.perks.abilities.shapeshifting.ShapeshiftBear
import com.anathema_roguelike.entities.characters.player.perks.abilities.shapeshifting.ShapeshiftPanther
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.SpellOrSpecialization
import com.anathema_roguelike.entities.characters.player.perks.masteries.MasteryLevel
import com.anathema_roguelike.entities.characters.player.perks.skills.AnimalisticCasting
import com.anathema_roguelike.entities.characters.player.perks.skills.Attunement
import com.anathema_roguelike.entities.characters.player.perks.skills.Discipline
import com.anathema_roguelike.entities.characters.player.perks.skills.SureFooting
import com.anathema_roguelike.entities.characters.player.perks.specializations.AbilitySpecialization
import com.anathema_roguelike.stats.characterstats.masteries.BluntWeaponMastery
import com.anathema_roguelike.stats.characterstats.masteries.ShapeshiftingMastery
import com.anathema_roguelike.stats.characterstats.masteries.SpellMastery

class Druid() extends PlayerClass(
  new PerkGroup /*1*/ (new SpellOrSpecialization[Druid](1), new ShapeshiftPanther),
  new PerkGroup /*2*/ (new SpellOrSpecialization[Druid](1), new MasteryLevel[SpellMastery]),
  new PerkGroup /*3*/ (new MasteryLevel[BluntWeaponMastery], new MasteryLevel[ShapeshiftingMastery]),
  new PerkGroup /*4*/ (new SpellOrSpecialization[Druid](1), new MasteryLevel[SpellMastery]),
  new PerkGroup /*5*/ (new SpellOrSpecialization[Druid](1), new MasteryLevel[BluntWeaponMastery], new MasteryLevel[ShapeshiftingMastery]),
  new PerkGroup /*6*/ (new SpellOrSpecialization[Druid](1), new MasteryLevel[SpellMastery]),
  new PerkGroup /*7*/ (new SpellOrSpecialization[Druid](2), new ShapeshiftBat),
  new PerkGroup /*8*/ (new MasteryLevel[SpellMastery], new AbilitySpecialization[Shapeshift]),
  new PerkGroup /*9*/ (new SpellOrSpecialization[Druid](2), new MasteryLevel[ShapeshiftingMastery]),
  new PerkGroup /*10*/ (new SpellOrSpecialization[Druid](2), new MasteryLevel[SpellMastery], new Discipline),
  new PerkGroup /*11*/ (new MasteryLevel[ShapeshiftingMastery], new AbilitySpecialization[Shapeshift]),
  new PerkGroup /*12*/ (new SpellOrSpecialization[Druid](2), new ShapeshiftBear),
  new PerkGroup /*13*/ (new MasteryLevel[ShapeshiftingMastery], new Attunement),
  new PerkGroup /*14*/ (new SpellOrSpecialization[Druid](3), new MasteryLevel[SpellMastery]),
  new PerkGroup /*15*/ (new MasteryLevel[BluntWeaponMastery], new AbilitySpecialization[Shapeshift], new SureFooting),
  new PerkGroup /*16*/ (new SpellOrSpecialization[Druid](3), new MasteryLevel[ShapeshiftingMastery]),
  new PerkGroup /*17*/ (new MasteryLevel[SpellMastery], new AbilitySpecialization[Shapeshift]),
  new PerkGroup /*18*/ (new SpellOrSpecialization[Druid](3), new MasteryLevel[SpellMastery]),
  new PerkGroup /*19*/ (new MasteryLevel[SpellMastery], new MasteryLevel[ShapeshiftingMastery]),
  new PerkGroup /*20*/ (new SpellOrSpecialization[Druid](4), new MasteryLevel[BluntWeaponMastery], new AnimalisticCasting)) {
}