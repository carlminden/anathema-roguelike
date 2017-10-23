package com.anathema_roguelike.entities.events;


import java.util.Optional;

import com.anathema_roguelike.environment.Environment;

public class EnvironmentChangedEvent {
	
	private Optional<Environment> oldEnvironment;
	private Environment newEnvironment;
	
	public EnvironmentChangedEvent(Optional<Environment> oldEnvironment, Environment newEnvironment) {
		this.oldEnvironment = oldEnvironment;
		this.newEnvironment = newEnvironment;
	}
	
	public Environment getNewEnvironment() {
		return newEnvironment;
	}
	
	public Optional<Environment> getOldEnvironment() {
		return oldEnvironment;
	}
	
	public void reRegister(Object o) {
		getOldEnvironment().ifPresent(old -> old.getEventBus().unregister(o));
		getNewEnvironment().getEventBus().register(o);
	}
}
