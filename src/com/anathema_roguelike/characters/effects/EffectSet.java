/*******************************************************************************
 * This file is part of AnathemaRL.
 *
 *     AnathemaRL is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     AnathemaRL is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with AnathemaRL.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package com.anathema_roguelike.characters.effects;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.effects.buffs.Buff;
import com.anathema_roguelike.characters.effects.buffs.BuffCollection;
import com.anathema_roguelike.characters.effects.conditions.Condition;
import com.anathema_roguelike.characters.effects.conditions.ConditionCollection;
import com.anathema_roguelike.characters.effects.descriptors.Descriptor;
import com.anathema_roguelike.characters.effects.descriptors.VulnerabilityCollection;
import com.anathema_roguelike.characters.effects.modifiers.Modifier;
import com.anathema_roguelike.characters.events.TurnEvent;
import com.anathema_roguelike.characters.stats.Stat;
import com.google.common.eventbus.Subscribe;

public class EffectSet {
	
	private Character character;
	
	private BuffCollection buffs;
	private ConditionCollection conditions;
	private VulnerabilityCollection vulnerabilities;
		
	public EffectSet(Character character) {
		character.registerHandler(this);
		this.character = character;
		
		this.buffs = new BuffCollection(character);
		this.conditions = new ConditionCollection(character);
		this.vulnerabilities = new VulnerabilityCollection(character);
	}
	
	public int getStatBonus(Class<? extends Stat> stat) {
		
		int bonus = 0;
		
		for(Buff buff : buffs) {
			for(Modifier modifier : buff.getModifiers()) {
				if(modifier.getAffectedStat() == stat) {
					bonus += modifier.getStaticAmount();
				}
			}
		}
		
		for(Condition condition : conditions) {
			for(Modifier modifier : condition.getModifiers()) {
				bonus += modifier.getStaticAmount();
			}
		}
		
		return bonus;
	}
	
	public double getStatMultiplier(Class<? extends Stat> stat) {
		
		double bonus = 1;
		
		for(Buff buff : buffs) {
			for(Modifier modifier : buff.getModifiers()) {
				if(modifier.getAffectedStat() == stat) {
					bonus *= modifier.getMultiplier();
				}
			}
		}
		
		for(Condition condition : conditions) {
			for(Modifier modifier : condition.getModifiers()) {
				bonus *= modifier.getMultiplier();
			}
		}
		
		return bonus;
	}
	
	@Subscribe
	public void handleTurnEvent(TurnEvent event) {
		
		buffs.decrement();
		conditions.decrement();
		vulnerabilities.decrement();
		
		buffs.removeExpired();
		conditions.removeExpired();
		vulnerabilities.removeExpired();
	}
	
	public boolean hasCondition(Class<? extends Condition> condition) {
		return conditions.hasCondition(condition);
	}
	
	public void cureCondition(Class<? extends Condition> condition) {
		conditions.removeAll(condition);
	}
	
	public BuffCollection getBuffs() {
		return buffs;
	}
	
	public ConditionCollection getConditions() {
		return conditions;
	}
	
	public VulnerabilityCollection getVulnerabilities() {
		return vulnerabilities;
	}

	public boolean isVulnerableTo(Class<? extends Descriptor> descriptor) {
		return vulnerabilities.isVulnerableTo(descriptor);
	}

	public void apply(Effect effect) {
		effect.applyTo(this);
	}
	
	public void remove(Effect effect) {
		effect.removeFrom(this);
	}

	public Character getCharacter() {
		return character;
	}
}
