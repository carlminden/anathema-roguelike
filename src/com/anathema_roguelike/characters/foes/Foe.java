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
package com.anathema_roguelike.characters.foes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.foes.ai.AI;
import com.anathema_roguelike.characters.foes.ai.Faction;
import com.anathema_roguelike.characters.foes.corruptions.Corruption;
import com.anathema_roguelike.characters.foes.roles.Role;
import com.anathema_roguelike.characters.foes.traits.Trait;
import com.anathema_roguelike.environment.Environment;
import com.anathema_roguelike.main.Game;
import com.anathema_roguelike.main.display.DungeonMap.DungeonLayer;
import com.anathema_roguelike.main.display.VisualRepresentation;
import com.anathema_roguelike.stimuli.PercievedStimulus;

public abstract class Foe extends Character {
	
	protected AI ai;
	
	private ArrayList<Character> visibleCharacters;
	
	private PercievedStimulus mostInterestingStimulus;
	
	private Comparator<PercievedStimulus> stimuliComparator = new Comparator<PercievedStimulus>() {

		@Override
		public int compare(PercievedStimulus o1, PercievedStimulus o2) {
			 
			int x1 = getX() - o1.getPosition().getX();
			int y1 = getY() - o1.getPosition().getY();
			int d1 = x1*x1 + y1*y1;
			
			int x2 = getX() - o2.getPosition().getX();
			int y2 = getY() - o2.getPosition().getY();
			int d2 = x2*x2 + y2*y2;
			
			
			int stimulus1 = o1.getMagnitude() + 20/d1;
			int stimulus2 = o2.getMagnitude() + 20/d2;
			
			if(stimulus1 > stimulus2) {
				return 1;
			} else if(stimulus2 > stimulus1) {
				return -1;
			} else {
				return 0;
			}
		}
	};
	
	private Comparator<Character> distanceList = new Comparator<Character>() {

		@Override
		public int compare(Character o1, Character o2) {
			
			int x1 = getX() - o1.getX();
			int y1 = getY() - o1.getY();
			int d1 = x1*x1 + y1*y1;
			
			int x2 = getX() - o2.getX();
			int y2 = getY() - o2.getY();
			int d2 = x2*x2 + y2*y2;
			
			
			if(d1 > d2) {
				return 1;
			} else if(d2 > d1) {
				return -1;
			} else {
				return o1.getPosition().compareTo(o2.getPosition());
			}
		}
	};
	
	private Role role;
	private Corruption corruption;

	public Foe(Optional<VisualRepresentation> representation, Role role, Corruption corruption, Trait<?> ... traits) {
		super(representation);
		
		setFaction(Faction.FOES);
		
		ai = new AI(this);
		
		visibleCharacters = new ArrayList<>();
		
		new BaseFoeStats().grant(this);
	}

	@Override
	public void onDeath() {
		Environment level = Game.getInstance().getState().getEnvironment(getDepth());
		
		level.removeEntity(this);
		
		Game.getInstance().getEventBus().unregister(this);
		unregisterHandler(this);
	}
	
	@Override
	public void onTurn() {
		if(getPercievedStimuli().size() > 0) {
			mostInterestingStimulus = Collections.max(getPercievedStimuli(), stimuliComparator);
		} else {
			mostInterestingStimulus = null;
		}
		
		while(getActionRemaining()) {
			observeSurroundings();
			ai.performAction();
		}
		
		observeSurroundings();
	}

	public AI getAI() {
		return ai;
	}
	
	public Stream<Character> getVisibleCharacters() {
		return visibleCharacters.stream();
	}
	
	public Stream<Character> getVisibleEnemies() {
		return visibleCharacters.stream().filter(c -> {
			return !Faction.friendly(this, c);	
		});
	}
	
	public void addVisibleCharacters(Character character) {
		visibleCharacters.add(character);
	}

	public PercievedStimulus getMostInterestingStimulus() {
		return mostInterestingStimulus;
	}
	
	public void observeSurroundings() {
		Environment level = getEnvironment();
		
		computeVisibility();
		
		visibleCharacters.clear();
		
		for(Character character : level.getEntities(Character.class)) {
			if(character.isVisibleTo(this)) {
				addVisibleCharacters(character);
			}
		}
		
		Collections.sort(visibleCharacters, distanceList);
	}

	@Override
	protected void renderThis() {
		Game.getInstance().getMap().renderEntity(DungeonLayer.NPCS, this);
	}
}
