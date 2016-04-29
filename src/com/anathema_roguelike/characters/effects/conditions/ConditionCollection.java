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
package com.anathema_roguelike.characters.effects.conditions;

import java.util.Iterator;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.effects.EffectTreeMultimap;
import com.anathema_roguelike.main.utilities.Listed;

@Listed
public class ConditionCollection extends EffectTreeMultimap<Class<? extends Condition>, Condition> {
	
	
	
	public ConditionCollection(Character character) {
		super(character);
	}

	public void cureCondition() {
		
		Iterator<Condition> iterator = iterator();
		
		while(iterator.hasNext()) {
			Condition condition = iterator.next();
			if(condition.isCurable()) {
				removeAll(condition.getClass());
			}
		}
	}
	
	public boolean hasCondition(Class<? extends Condition> condition) {
		
		return containsUnexpired(condition);
	}

	@Override
	protected Class<? extends Condition> getKey(Condition condition) {
		return condition.getClass();
	}
	
	public Condition pollFirst(Class<? extends Condition> condition) {
		return getTreeMultimap().get(condition).pollFirst();
	}
}