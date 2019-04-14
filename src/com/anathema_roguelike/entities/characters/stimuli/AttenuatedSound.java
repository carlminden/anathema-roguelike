package com.anathema_roguelike.entities.characters.stimuli;

public class AttenuatedSound extends Sound {

	public AttenuatedSound(double magnitude, Character owner) {
		super(magnitude - owner.getStatAmount(Attenuation.class), owner);
	}
}
