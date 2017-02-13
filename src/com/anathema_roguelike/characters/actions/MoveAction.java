package com.anathema_roguelike.characters.actions;

import com.anathema_roguelike.characters.Character;

public class MoveAction extends Action {
	
	private int direction;

	public MoveAction(int direction) {
		super(new TimeCost());
		
		this.direction = direction;
	}

	@Override
	protected void perform(Character character) {
		character.move(direction);
	}
}
