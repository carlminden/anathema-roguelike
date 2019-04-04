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
package com.anathema_roguelike.entities.characters.perks;

import java.util.ArrayList;
import java.util.Collection;

import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.entities.characters.perks.requirements.PerkRequirement;

public abstract class Perk {
	private Character character;
	private Collection<PerkRequirement> requirements = new ArrayList<>();
	
	private String name = "UNNAMED PERK";
	
	public Perk(String name) {
		
		this.name = name;
	}
	
	public Perk(String name, Object source, PerkRequirement condition) {
		this(name);
		
		requirements.add(condition);
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public void grant(Character character) {
		this.character = character;
		character.addPerk(this);
		character.getEventBus().register(this);
	}
	
	public void remove(Character character) {
		character.removePerk(this);
		character.getEventBus().unregister(this);		
		this.character = null;
	}
	
	public void addRequirement(PerkRequirement requirement) {
		requirement.setPerk(this);
		requirements.add(requirement);
	}
	
	public Character getCharacter() {
		return character;
	}
	
	protected void setCharacter(Character character) {
		this.character = character;
	}
	
	public boolean requirementsMet() {
		return requirements.stream().map((r) -> r.getCondition().get()).reduce(true, (a, b) -> a && b);
	}
	
	public void printUnmetConditionMessages() {
		for(PerkRequirement requirement : requirements) {
			if(!requirement.getCondition().get()) {
				requirement.printUnmetConditionMessage();
			}
		}
	}
	
	public Collection<PerkRequirement> getPerkRequirements() {
		return requirements;
	}
	
	public void setPerkConditions(Collection<PerkRequirement> requirements) {
		this.requirements = requirements;
	}
}