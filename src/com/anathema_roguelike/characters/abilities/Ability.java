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
package com.anathema_roguelike.characters.abilities;

import java.util.ArrayList;
import java.util.Collection;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.abilities.conditions.AbilityRequirement;
import com.anathema_roguelike.characters.abilities.conditions.CooldownRequirement;
import com.anathema_roguelike.characters.abilities.costs.AbilityCost;
import com.anathema_roguelike.characters.abilities.costs.CooldownCost;
import com.anathema_roguelike.characters.events.TurnEvent;
import com.anathema_roguelike.main.utilities.BooleanCondition;
import com.anathema_roguelike.stats.effects.Duration;
import com.google.common.eventbus.Subscribe;

public abstract class Ability {
	private Object source;
	private Duration cooldown;
	private Character character;
	private Collection<AbilityRequirement> requirements = new ArrayList<>();
	private Collection<AbilityCost> costs = new ArrayList<>();
	
	public void processResults(AbilityResults results) {
		results.applyResults();
	}
	
	public Ability(Object source) {
		initialize();
		
		this.source = source;
	}
	
	public Ability(Object source, AbilityRequirement condition) {
		initialize();
		
		requirements.add(condition);
		this.source = source;
	}
	
	public Ability(Object source, Duration cooldown) {
		this.cooldown = Duration.copy(cooldown);
		this.source = source;
	}
	
	private void initialize() {
		addRequirement(new CooldownRequirement(this));
		
		addCost(new CooldownCost(this));
	}
	
	public void grant(Character character) {
		this.character = character;
		character.addAbility(this);
		character.registerHandler(this);
	}
	
	public void remove(Character character) {
		character.removeAbility(this);
		character.unregisterHandler(this);
		
		this.character = null;
	}
	
	@Subscribe
	public void handleTurnEvent(TurnEvent event) {
		if(cooldown != null) {
			cooldown.decrement();
		}
	}
	
	public void activateCooldown() {
		if(cooldown != null) {
			cooldown.activate();
		}
	}
	
	public boolean isOnCooldown() {
		if(cooldown == null) {
			return false;
		} else {
			return !cooldown.isExpired();
		}
	}
	
	public void addRequirement(AbilityRequirement requirement) {
		requirements.add(requirement);
	}
	
	public Object getSource() {
		return source;
	}
	
	public void setCooldown(Duration cooldown) {
		this.cooldown = Duration.copy(cooldown);
	}
	
	public Duration getCooldown() {
		return cooldown;
	}
	
	public Character getCharacter() {
		return character;
	}
	
	public boolean requirementsMet() {
		return getBooleanCondition().isTrue();
	}
	
	//not a great name
	public boolean checkRequirementsAndExpendCostsIfTrue() {
		if(getBooleanCondition().isTrue()) {
			payAbilityCosts();
			return true;
		} else {
			return false;
		}
	}
	
	public void payAbilityCosts() {
		for(AbilityCost cost : costs) {
			cost.pay();
		}
	}
	
	public void printUnmetConditionMessages() {
		for(AbilityRequirement condition : requirements) {
			if(!condition.getCondition().isTrue()) {
				condition.printUnmetConditionMessage();
			}
		}
	}
	
	public BooleanCondition getBooleanCondition() {
		BooleanCondition ret = BooleanCondition.alwaysTrue();
		
		for(AbilityRequirement requirement : requirements) {
			ret = BooleanCondition.and(requirement.getCondition(), ret);
		}
		
		return ret;
	}
	
	public Collection<AbilityRequirement> getAbilityRequirements() {
		return requirements;
	}
	
	public void setAbilityConditions(Collection<AbilityRequirement> requirements) {
		this.requirements = requirements;
	}

	public Collection<AbilityCost> getCosts() {
		return costs;
	}

	public void addCost(AbilityCost cost) {
		this.costs.add(cost);
	}
}
