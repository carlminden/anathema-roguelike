package com.anathema_roguelike.stimuli;

import java.util.Optional;

import com.anathema_roguelike.characters.Character;

public class StimulusEvent {
	
	private Stimulus stimulus;
	
	public StimulusEvent(Stimulus stimulus) {
		this.stimulus = stimulus;
	}
	
	public Optional<PercievedStimulus> getPercievedStimulus(Character character) {
		return stimulus.computePercievedStimulus(character);
	}
	
	public Stimulus getStimulus() {
		return stimulus;
	}
}
