/*******************************************************************************
 * Copyright (c) 2019. Carl Minden
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package com.anathema_roguelike.entities.characters.perks.actions;

import com.anathema_roguelike.actors.Action;
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
