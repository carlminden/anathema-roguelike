/*******************************************************************************
 * Copyright (C) 2017 Carl Minden
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
package com.anathema_roguelike.characters.perks;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.attacks.Attack;
import com.anathema_roguelike.characters.perks.targetingstrategies.TargetConsumer;
import com.anathema_roguelike.characters.perks.targetingstrategies.Targetable;
import com.anathema_roguelike.characters.perks.targetingstrategies.TargetingStrategy;
import com.anathema_roguelike.characters.perks.targetingstrategies.ranges.Range;

public abstract class AttackPerk<T extends Targetable> extends OffensiveTargetedPerk<T> {
	
	protected abstract Attack getAttack();
	
	public AttackPerk(Range<Character> range, TargetingStrategy<T, Character> strategy, TargetConsumer<?> ...targetConsumers) {
		super(range, strategy, targetConsumers);
		
		addTargetConsumer(new TargetConsumer<Character>(Character.class) {

			@Override
			public void consume(Character target) {
				if(getAttack().getEffect().isPresent()) {
					target.applyEffect(getAttack().getEffect());
				} else {
					throw new RuntimeException("Missing Effect");
				}
			}
		});
	}
}
