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

import com.anathema_roguelike.entities.characters.player.perks.specializations.AbilitySpecialization;
import com.anathema_roguelike.stats.characterstats.masteries.BowMastery;
import com.anathema_roguelike.stats.characterstats.masteries.DualWieldMastery;
import com.anathema_roguelike.stats.characterstats.masteries.LongBladeMastery;
import com.anathema_roguelike.stats.characterstats.masteries.ShortBladeMastery;

public class Rogue extends PlayerClass {

	public Rogue() {
		super(
			new PerkGroup/*1*/(new MasteryLevel<>(ShortBladeMastery.class), new ThrowRock()),
			new PerkGroup/*2*/(new MasteryLevel<>(LongBladeMastery.class), new MasteryLevel<>(BowMastery.class)),
			new PerkGroup/*3*/(new MasteryLevel<>(ShortBladeMastery.class), new Lockpicking()),
			new PerkGroup/*4*/(new MasteryLevel<>(DualWieldMastery.class), new Hide()),
			new PerkGroup/*5*/(new MasteryLevel<>(ShortBladeMastery.class), new MasteryLevel<>(BowMastery.class), new AbilitySpecialization(Technique.class)),
			new PerkGroup/*6*/(new MasteryLevel<>(DualWieldMastery.class), new LightStep()),
			new PerkGroup/*7*/(new MasteryLevel<>(LongBladeMastery.class), new AbilitySpecialization(Technique.class)),
			new PerkGroup/*8*/(new MasteryLevel<>(ShortBladeMastery.class), new Vault()),
			new PerkGroup/*9*/(new AbilitySpecialization(Technique.class), new KeenHearing()),
			new PerkGroup/*10*/(new MasteryLevel<>(ShortBladeMastery.class), new MasteryLevel<>(LongBladeMastery.class), new Trapfinding()),
			new PerkGroup/*11*/(new MasteryLevel<>(DualWieldMastery.class), new AbilitySpecialization(Technique.class)),
			new PerkGroup/*12*/(new MasteryLevel<>(ShortBladeMastery.class), new MasteryLevel<>(BowMastery.class)),
			new PerkGroup/*13*/(new AbilitySpecialization(Technique.class), new Vanish()),
			new PerkGroup/*14*/(new MasteryLevel<>(LongBladeMastery.class), new MasteryLevel<>(DualWieldMastery.class)),
			new PerkGroup/*15*/(new MasteryLevel<>(ShortBladeMastery.class), new AbilitySpecialization(Technique.class), new SlowingPoison()),
			new PerkGroup/*16*/(new MasteryLevel<>(BowMastery.class), new AbilitySpecialization(Technique.class)),
			new PerkGroup/*17*/(new MasteryLevel<>(LongBladeMastery.class), new AbilitySpecialization(Technique.class)),
			new PerkGroup/*18*/(new MasteryLevel<>(ShortBladeMastery.class), new AbilitySpecialization(Technique.class)),
			new PerkGroup/*19*/(new MasteryLevel<>(DualWieldMastery.class), new AbilitySpecialization(Technique.class)),
			new PerkGroup/*20*/(new MasteryLevel<>(ShortBladeMastery.class), new MasteryLevel<>(BowMastery.class), new UncannySenses())
		);
	}
}
