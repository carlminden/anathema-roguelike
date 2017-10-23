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
package com.anathema_roguelike.entities.characters.player.perks.abilities.spells;

import java.util.Collection;

import com.anathema_roguelike.entities.characters.perks.PassthroughPerk;
import com.anathema_roguelike.entities.characters.perks.actions.ActionPerk;
import com.anathema_roguelike.entities.characters.player.classes.PlayerClass;
import com.anathema_roguelike.entities.characters.player.perks.abilities.Ability;
import com.anathema_roguelike.main.utilities.AutoClassToInstanceMap;
import com.google.common.collect.Collections2;

public abstract class Spell<T extends ActionPerk<?>> extends PassthroughPerk<T> implements Ability {
	
	@SuppressWarnings("rawtypes")
	private static AutoClassToInstanceMap<Spell> spells = new AutoClassToInstanceMap<>(Spell.class);
	
	@SuppressWarnings("rawtypes")
	public static Collection<Spell> findSpells(int spellLevel, Class<? extends PlayerClass> casterClass) {
		return Collections2.filter(spells.getValues(), (s) -> s.getSpellLevel() == spellLevel && s.getCasterClass() == casterClass);
	}
	
	private int spellLevel;
	private Class<? extends PlayerClass> casterClass;
	
	public Spell(int spellLevel, Class<? extends PlayerClass> casterClass) {
		super();
		
		this.spellLevel = spellLevel;
		this.casterClass = casterClass;
	}
	
	public int getSpellLevel() {
		return spellLevel;
	}
	
	public Class<? extends PlayerClass> getCasterClass() {
		return casterClass;
	}
}
