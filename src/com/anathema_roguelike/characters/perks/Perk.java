package com.anathema_roguelike.characters.perks;

import java.util.ArrayList;
import java.util.Collection;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.events.TurnEvent;
import com.anathema_roguelike.characters.perks.conditions.CooldownRequirement;
import com.anathema_roguelike.characters.perks.conditions.PerkRequirement;
import com.anathema_roguelike.characters.perks.costs.CooldownCost;
import com.anathema_roguelike.characters.perks.costs.PerkCost;
import com.anathema_roguelike.main.utilities.BooleanCondition;
import com.anathema_roguelike.stats.effects.Duration;
import com.google.common.eventbus.Subscribe;

public abstract class Perk {
	private Duration cooldown;
	private Character character;
	private Collection<PerkRequirement> requirements = new ArrayList<>();
	private Collection<PerkCost> costs = new ArrayList<>();
	
	public Perk() {
		initialize();
	}
	
	public Perk(Object source, PerkRequirement condition) {
		initialize();
		
		requirements.add(condition);
	}
	
	public Perk(Object source, Duration cooldown) {
		this.cooldown = Duration.copy(cooldown);
	}
	
	private void initialize() {
		addRequirement(new CooldownRequirement(this));
		
		addCost(new CooldownCost(this));
	}
	
	public void grant(Character character) {
		this.character = character;
		character.addPerk(this);
		character.registerHandler(this);
	}
	
	public void remove(Character character) {
		character.removePerk(this);
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
	
	public void addRequirement(PerkRequirement requirement) {
		requirements.add(requirement);
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
			payPerkCosts();
			return true;
		} else {
			return false;
		}
	}
	
	public void payPerkCosts() {
		for(PerkCost cost : costs) {
			cost.pay();
		}
	}
	
	public void printUnmetConditionMessages() {
		for(PerkRequirement condition : requirements) {
			if(!condition.getCondition().isTrue()) {
				condition.printUnmetConditionMessage();
			}
		}
	}
	
	public BooleanCondition getBooleanCondition() {
		BooleanCondition ret = BooleanCondition.alwaysTrue();
		
		for(PerkRequirement requirement : requirements) {
			ret = BooleanCondition.and(requirement.getCondition(), ret);
		}
		
		return ret;
	}
	
	public Collection<PerkRequirement> getPerkRequirements() {
		return requirements;
	}
	
	public void setPerkConditions(Collection<PerkRequirement> requirements) {
		this.requirements = requirements;
	}

	public Collection<PerkCost> getCosts() {
		return costs;
	}

	public void addCost(PerkCost cost) {
		this.costs.add(cost);
	}
}
