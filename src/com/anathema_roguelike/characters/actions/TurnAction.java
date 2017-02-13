package com.anathema_roguelike.characters.actions;

import com.anathema_roguelike.characters.Character;

public class TurnAction extends Action {
	private double angle;

	public TurnAction(double angle) {
		super(new NoCost());
		
		this.angle = angle;
	}

	@Override
	protected void perform(Character character) {
		character.setFacing(angle);
	}
}
