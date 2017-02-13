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
package com.anathema_roguelike.characters.effects.modifiers;

import java.util.ArrayList;
import java.util.Collection;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.effects.Duration;
import com.anathema_roguelike.characters.effects.Effect;
import com.anathema_roguelike.characters.stats.resources.Resource;

public abstract class ModifierGroup extends Effect {
	private ArrayList<Modifier> modifiers = new ArrayList<>();
	private ArrayList<Modifier> resourceModifiers = new ArrayList<>();
	
	public ModifierGroup(Object source, Duration duration) {
		super(source, duration);
	}

	public ModifierGroup(Object source) {
		super(source);
	}
	
	public ArrayList<Modifier> getModifiers() {
		return modifiers;
	}
	
	public void addModifier(Modifier modifier) {
		if(Resource.class.isAssignableFrom(modifier.getAffectedStat())) {
			resourceModifiers.add(modifier);
		} else {
			modifiers.add(modifier);
		}
		
		modifier.setGroup(this);
	}
	
	public void addAllModifiers(Collection<Modifier> modifiers) {
		for(Modifier modifier : modifiers) {
			addModifier(modifier);
		}
	}
	
	@Override
	public void activate(Character affectedCharacter) {
		super.activate(affectedCharacter);
		
		for(Modifier modifier : resourceModifiers) {
			affectedCharacter.modifyResource(getSource(), (Class<? extends Resource>) modifier.getAffectedStat(), modifier.getStaticAmount());
		}
		
	}

}
