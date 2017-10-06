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
package com.anathema_roguelike.characters.player.perks.abilities.spells;

import java.util.Collection;

import com.anathema_roguelike.characters.perks.ActivatedPerk;
import com.anathema_roguelike.characters.perks.PassthroughPerk;
import com.anathema_roguelike.characters.player.classes.CharacterClass;
import com.anathema_roguelike.characters.player.perks.abilities.Ability;
import com.anathema_roguelike.main.utilities.AutoClassToInstanceMap;
import com.google.common.collect.Collections2;

public abstract class Spell<T extends ActivatedPerk> extends PassthroughPerk<T> implements Ability {
	
	private static AutoClassToInstanceMap<Spell> spells = new AutoClassToInstanceMap<>(Spell.class);
	
	public static Collection<Spell> findSpells(int spellLevel, Class<? extends CharacterClass> casterClass) {
		return Collections2.filter(spells.getValues(), (s) -> s.getSpellLevel() == spellLevel && s.getCasterClass() == casterClass);
	}
	
	private int spellLevel;
	private Class<? extends CharacterClass> casterClass;
	
	public Spell(T spell, int spellLevel, Class<? extends CharacterClass> casterClass) {
		super(spell);
		
		this.spellLevel = spellLevel;
		this.casterClass = casterClass;
	}
	
	public int getSpellLevel() {
		return spellLevel;
	}
	
	public Class<? extends CharacterClass> getCasterClass() {
		return casterClass;
	}
}
