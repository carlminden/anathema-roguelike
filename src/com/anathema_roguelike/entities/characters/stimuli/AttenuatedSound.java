package com.anathema_roguelike.entities.characters.stimuli;

import com.anathema_roguelike.stats.characterstats.secondarystats.detection.defenses.Attenuation;

public class AttenuatedSound extends Sound {

	public AttenuatedSound(double magnitude, Character owner) {
		super(magnitude - owner.getStatAmount(Attenuation.class), owner);
	}
}
