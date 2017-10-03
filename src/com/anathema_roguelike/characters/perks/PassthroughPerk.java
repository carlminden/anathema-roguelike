package com.anathema_roguelike.characters.perks;

import com.anathema_roguelike.characters.Character;

public class PassthroughPerk<T extends Perk> extends Perk {
	
	private T perk;
	
	public PassthroughPerk(T perk) {
		this.perk = perk;
	}
	
	@Override
	public void grant(Character character) {
		super.grant(character);
		perk.grant(character);
	}
	
	@Override
	public void remove(Character character) {
		super.remove(character);
		perk.remove(character);
	}
}
