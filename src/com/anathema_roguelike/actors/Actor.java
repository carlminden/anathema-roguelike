package com.anathema_roguelike.actors;

import com.anathema_roguelike.entities.characters.actions.costs.EnergyCost;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface Actor {
	
	Duration getDuration();
	Energy getEnergy();
	Optional<Action<?>> getNextAction();

	default Action<Actor> getDefaultAction() {
		return new Action<>(this, EnergyCost.STANDARD(this)) {
			@Override
			protected void onTake() {

			}
		};
	}
	
	default void energize() {
		getEnergy().energize();
	}
	
	default double getEnergyLevel() {
		return getEnergy().getEnergyLevel();
	}
	
	default void act() {
		getNextAction().ifPresentOrElse(a -> a.take(), () -> getDefaultAction().take());
	}
}
