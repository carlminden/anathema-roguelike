package com.anathema_roguelike.entities.characters.stimuli;

import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.stats.characterstats.secondarystats.detection.defenses.Attenuation;

public class AttenuatedSound extends Sound {

	public AttenuatedSound(int magnitude, Character owner) {
		super((int) (magnitude - owner.getStatAmount(Attenuation.class)), owner);
	}
}
