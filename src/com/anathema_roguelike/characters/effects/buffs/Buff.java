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
package com.anathema_roguelike.characters.effects.buffs;

import java.util.ArrayList;

import com.anathema_roguelike.characters.effects.Duration;
import com.anathema_roguelike.characters.effects.EffectSet;
import com.anathema_roguelike.characters.effects.conditions.Condition;
import com.anathema_roguelike.characters.effects.descriptors.Vulnerability;
import com.anathema_roguelike.characters.effects.modifiers.ModifierGroup;


public class Buff extends ModifierGroup {
	
	private ArrayList<Condition> conditions = new ArrayList<>();
	private ArrayList<Vulnerability> vulnerabilities = new ArrayList<>();
	private boolean beneficial = true;
	
	public Buff(Object source) {
		super(source);
	}
	
	public Buff(Object source, Duration duration) {
		super(source, duration);
	}

	public Buff(Object source, Duration duration, boolean beneficial) {
		super(source, duration);
		
		this.beneficial = beneficial;
	}

	public Buff(Object source, boolean beneficial) {
		super(source);
		
		this.beneficial = beneficial;
	}

	public ArrayList<Condition> getConditions(){
		return conditions;
	}
	
	public ArrayList<Vulnerability> getVulnerabilities(){
		return vulnerabilities;
	}
	
	public boolean isBeneficial() {
		return beneficial;
	}

	public void addCondition(Condition condition) {
		conditions.add(condition);
	}
	
	public void addVulnerability(Vulnerability vulnerability) {
		vulnerabilities.add(vulnerability);
	}

	@Override
	public void applyThis(EffectSet effectSet) {		
		for(Condition condition : getConditions()) {
			condition.applyTo(effectSet);
		}
		
		for(Vulnerability vulnerability : getVulnerabilities()) {
			vulnerability.applyTo(effectSet);
		}
		
		
		effectSet.getBuffs().addEffect(this);
	}

	@Override
	public void removeThis(EffectSet effectSet) {
		for(Condition condition : getConditions()) {
			condition.removeFrom(effectSet);
		}
		
		for(Vulnerability vulnerability : getVulnerabilities()) {
			vulnerability.removeFrom(effectSet);
		}
		
		effectSet.getBuffs().removeEffect(this);
	}
}
