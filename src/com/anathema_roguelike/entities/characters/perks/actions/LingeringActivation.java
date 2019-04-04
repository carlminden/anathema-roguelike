package com.anathema_roguelike.entities.characters.perks.actions;

import com.anathema_roguelike.actors.Action;
import com.anathema_roguelike.actors.Actor;
import com.anathema_roguelike.actors.Duration;
import com.anathema_roguelike.actors.Energy;
import com.anathema_roguelike.main.Game;

import java.util.Optional;

public abstract class LingeringActivation implements Actor {
	
	private Energy energy = new Energy();
	private Duration duration;
	
	public LingeringActivation(Duration duration) {
		this.duration = Duration.copy(duration);
		
		Game.getInstance().getState().registerActor(this);
	}
	
	protected abstract Optional<Action<?>> createLingeringAction();

	@Override
	public Duration getDuration() {
		return duration;
	}
	
	@Override
	public Energy getEnergy() {
		return energy;
	}

	@Override
	public Optional<Action<?>> getNextAction() {
		return createLingeringAction();
	}
	
}
