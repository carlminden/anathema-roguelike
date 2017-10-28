package com.anathema_roguelike.entities.characters.actions.costs;

import com.anathema_roguelike.entities.characters.Character;

public abstract class CharacterActionCost extends ActionCost {
	
	private Character character;
	
	public CharacterActionCost(Character character) {
		super(character);

		this.character = character;
	}
	
	public CharacterActionCost(Character character, boolean after) {
		super(character, after);

		this.character = character;
	}
	
	public Character getCharacter() {
		return character;
	}

}
