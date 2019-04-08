package com.anathema_roguelike.entities.characters.perks;

import java.util.Collection;

import com.anathema_roguelike.main.ui.uielements.interactiveuielements.SelectionScreen;

public abstract class PerkChoice extends Perk {
	
	private Perk choice;
	private String title;
	
	public PerkChoice(String title) {
		super(title);
		this.title = title;
	}
	
	public abstract Collection<? extends Perk> getChoices(Character character);
	
	@Override
	public void grant(Character character) {
		choice = new SelectionScreen<Perk>(title, getChoices(character), false).run();
		choice.grant(character);
	}
	
	@Override
	public void remove(Character character) {
		throw new RuntimeException("Perk Choices should never be granted and therefore shouldnt be able to be removed");
	}
	
	@Override
	public Character getCharacter() {
		throw new RuntimeException("Perk Choices should never be granted and therefore will never have a Character set");
	}
}
