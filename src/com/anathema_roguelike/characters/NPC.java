/*******************************************************************************
 * This file is part of AnathemaRL.
 *
 *     AnathemaRL is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     AnathemaRL is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with AnathemaRL.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package com.anathema_roguelike.characters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import com.anathema_roguelike.characters.ai.AI;
import com.anathema_roguelike.characters.ai.Faction;
import com.anathema_roguelike.characters.events.MoveEvent;
import com.anathema_roguelike.dungeon.DungeonLevel;
import com.anathema_roguelike.dungeon.Point;
import com.anathema_roguelike.main.Game;
import com.anathema_roguelike.main.display.VisualRepresentation;
import com.anathema_roguelike.main.display.DungeonMap.Layer;
import com.google.common.eventbus.Subscribe;

public abstract class NPC extends Character {
	
	protected AI ai;
	
	private ArrayList<Character> visibleCharacters;
	
	private ArrayList<Map.Entry<Character, Point>> sortedLastKnown;
	private HashMap<Character, Point> lastKnownPositions;
	
	private Comparator<Map.Entry<Character, Point>> distanceMap = new Comparator<Map.Entry<Character, Point>>() {

		@Override
		public int compare(Map.Entry<Character, Point> o1, Map.Entry<Character, Point> o2) {
			
			int x1 = getX() - o1.getValue().getX();
			int y1 = getY() - o1.getValue().getY();
			int d1 = x1*x1 + y1*y1;
			
			int x2 = getX() - o2.getValue().getX();
			int y2 = getY() - o2.getValue().getY();
			int d2 = x2*x2 + y2*y2;
			
			
			if(d1 > d2) {
				return 1;
			} else if(d2 > d1) {
				return -1;
			} else {
				return o1.getValue().compareTo(o2.getValue());
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

	public NPC(VisualRepresentation representation) {
		super(representation);
		
		ai = new AI(this);
		
		visibleCharacters = new ArrayList<>();
		lastKnownPositions = new HashMap<>();
	}

	@Override
	public void onDeath() {
		DungeonLevel level = Game.getInstance().getState().getDungeonLevel(getDepth());
		
		level.removeEntity(this);
		
		Game.getInstance().getEventBus().unregister(this);
		unregisterHandler(this);
	}
	
	@Override
	public void killedBy(Character attacker) {
		if(attacker instanceof Player) {
			//TODO calculate real experience
			((Player)attacker).grantExperience(1);
		}
	}
	
	@Override
	public void onTurn() {
		observeSurroundings();
		ai.performAction();
		observeSurroundings();
	}

	public AI getAI() {
		return ai;
	}
	
	public Stream<Character> getVisibleCharacters() {
		return visibleCharacters.stream();
	}
	
	public Stream<Character> getVisibleEnemies() {
		return visibleCharacters.stream().filter(c -> !Faction.friendly(this, c));
	}
	
	public void addVisibleCharacters(Character character) {
		visibleCharacters.add(character);
	}

	public Map.Entry<Character, Point> getLastSeenNearestEnemy() {
		for(Map.Entry<Character, Point> lastKnown : sortedLastKnown) {
			if(!Faction.friendly(this, lastKnown.getKey())) {
				return lastKnown;
			}
		}

		return null;
	}
	
	public void putLastKnownPosition(Character character, Point p) {
		lastKnownPositions.put(character, p);
	}
	
	public void forgetLastKnownPosition(Character character) {
		lastKnownPositions.remove(character);
	}
	
	public void observeSurroundings() {
		DungeonLevel level = getDungeonLevel();
		
		computeVisibility();
		
		visibleCharacters.clear();
		
		for(Character character : level.getEntities(Character.class)) {
			if(canSee(character)) {
				addVisibleCharacters(character);
				putLastKnownPosition(character, character.getPosition());
			}
		}
		
		sortedLastKnown = new ArrayList<Map.Entry<Character, Point>>(lastKnownPositions.entrySet());
		Collections.sort(sortedLastKnown, distanceMap);
		
		Collections.sort(visibleCharacters, distanceList);
	}

	@Override
	protected void renderThis() {
		Game.getInstance().getMap().renderEntity(Layer.NPCS, this);
	}
	
	@Subscribe
	public void processMoveEvent(MoveEvent e) {
		if(canSee(e.getCharacter())) {
			putLastKnownPosition(e.getCharacter(), e.getPoint());
		}
	}
}
