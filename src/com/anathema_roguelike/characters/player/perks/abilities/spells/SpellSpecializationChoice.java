package com.anathema_roguelike.characters.player.perks.abilities.spells;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.player.classes.PlayerClass;
import com.anathema_roguelike.characters.player.perks.abilities.Ability;
import com.anathema_roguelike.characters.player.perks.specializations.AbilitySpecializationChoice;

public class SpellSpecializationChoice extends AbilitySpecializationChoice {
	
	private int spellLevel;
	private Class<? extends PlayerClass> casterClass;
	
	public SpellSpecializationChoice(int spellLevel, Class<? extends PlayerClass> casterClass) {
		super(Spell.class);
		
		this.spellLevel = spellLevel;
		this.casterClass = casterClass;
	}
	
	@Override
	public boolean validAbility(Character character, Ability a) {
		return super.validAbility(character, a) && ((Spell<?>)a).getCasterClass() == casterClass && ((Spell<?>)a).getSpellLevel() == spellLevel;
	}
}
