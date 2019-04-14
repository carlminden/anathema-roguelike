/*******************************************************************************
 * Copyright (C) 2017 Carl Minden
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
package com.anathema_roguelike.entities.characters.foes;

import com.anathema_roguelike.entities.characters.foes.ai.AI;
import com.anathema_roguelike.entities.characters.foes.ai.Faction;
import com.anathema_roguelike.entities.characters.foes.roles.Role;
import com.anathema_roguelike.entities.characters.stimuli.PerceivedStimulus;
import com.anathema_roguelike.main.Game;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;
import com.anathema_roguelike.entities.characters.Character;

public abstract class Foe extends Character {
	
	private AI ai;
	
	private Optional<PerceivedStimulus> mostInterestingStimulus = Optional.empty();
	
	private Comparator<Character> distanceComparator = (Character o1, Character o2) -> {
		int d1 = getPosition().squareDistance(o1);
		int d2 = getPosition().squareDistance(o2);


		if(d1 > d2) {
			return 1;
		} else if(d2 > d1) {
			return -1;
		} else {
			return o1.getPosition().compareTo(o2.getPosition());
		}
	};
	
	private Role role;
	private Corruption corruption;

	public Foe(Role role, Corruption corruption, Trait<?> ...traits) {
		setFaction(Faction.FOES);
		
		this.role = role;
		this.corruption = corruption;
		
		ai = new AI(this);
		
		new BaseFoeStats().grant(this);
	}

	@Override
	public void onDeath() {
		getEnvironment().removeEntity(this);
		
		Game.getInstance().getState().removeActor(this);
		Game.getInstance().getEventBus().unregister(this);
		getEventBus().unregister(this);
	}
	
	@Override
	public void setNextPendingAction() {
		mostInterestingStimulus = getPercievedStimuli().mostInterestingStimulus();
		
		ai.addNextPendingAction();
	}
	
	public AI getAI() {
		return ai;
	}
	
	public Stream<Character> getVisibleEnemies() {
		ArrayList<Character> characters = new ArrayList<>(getCurrentlyVisibleCharacters());

		characters.sort(distanceComparator);
		
		return characters.stream().filter(c -> {
			return !Faction.friendly(this, c);	
		});
	}

	public Optional<PerceivedStimulus> getMostInterestingStimulus() {
		return mostInterestingStimulus;
	}

	@Override
	protected void renderThis() {
		Game.getInstance().getMap().renderEntity(DungeonLayer.NPCS, this);
	}
}
