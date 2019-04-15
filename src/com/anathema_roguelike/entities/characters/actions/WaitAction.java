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

package com.anathema_roguelike.entities.characters.actions;

import com.anathema_roguelike.actors.Duration;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.TargetEffect;
import com.anathema_roguelike.stats.characterstats.CharacterStat;
import com.anathema_roguelike.stats.effects.Effect;
import com.anathema_roguelike.entities.characters.Character;

import java.util.Collections;
import java.util.Optional;

public class WaitAction extends TargetedAction<Character> {

	public WaitAction(Character character) {
		super(character, character, Collections.singletonList(character), EnergyCost.STANDARD(character), new TargetEffect<Character, CharacterStat>(character.getClass(), "Waiting") {
			@Override
			public Optional<Effect<Character, CharacterStat>> getEffect() {
				return Optional.of(
					new Effect<>(Optional.of(this), new Duration(Duration.VERY_SHORT),
						new Modifier<>(Vision.class, AdditiveCalculation.fixed(5.0)),
						new Modifier<>(Hearing.class, AdditiveCalculation.fixed(5.0))
					)
				);
			}
		});
	}

	@Override
	protected void onTake() {
		
	}
}
