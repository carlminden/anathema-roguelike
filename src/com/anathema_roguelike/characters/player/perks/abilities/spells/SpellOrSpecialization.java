package com.anathema_roguelike.characters.player.perks.abilities.spells;

import java.util.ArrayList;
import java.util.Collection;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.perks.Perk;
import com.anathema_roguelike.characters.perks.PerkChoice;
import com.anathema_roguelike.characters.player.classes.CharacterClass;
import com.anathema_roguelike.main.utilities.Utils;
import com.google.common.collect.Collections2;

public class SpellOrSpecialization extends PerkChoice {
	
	private int spellLevel;
	private Class<? extends CharacterClass> casterClass;

	public SpellOrSpecialization(int spellLevel, Class<? extends CharacterClass> casterClass) {
		super("Level " + spellLevel + " " + Utils.getName(casterClass) + " Spell");
		
		this.spellLevel = spellLevel;
		this.casterClass = casterClass;
	}

	@Override
	public Collection<? extends Perk> getChoices(Character character) {
		
		Collection<Perk> choices = new ArrayList<>(new SpellSpecializationChoice(spellLevel, casterClass).getChoices(character)); 
		
		Collection<Perk> unlearnedSpells = new ArrayList<>(Collections2.filter(Spell.findSpells(spellLevel, casterClass), s -> {
			return !getCharacter().hasPerk(s.getClass());
		}));
		
		choices.addAll(unlearnedSpells);
		
		return choices;
	}
}
