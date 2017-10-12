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
package com.anathema_roguelike.main;

import java.util.ArrayList;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.events.TurnEvent;
import com.anathema_roguelike.characters.player.Player;
import com.anathema_roguelike.environment.Environment;
import com.anathema_roguelike.environment.generation.CaveDungeonGenerator;
import com.anathema_roguelike.environment.generation.DungeonGenerator;
import com.anathema_roguelike.main.display.Renderable;
import com.anathema_roguelike.main.ui.charactercreation.CharacterCreationUI;
import com.anathema_roguelike.main.utilities.position.Direction;
import com.anathema_roguelike.main.utilities.position.Point;

public class State implements Renderable {
	
	private long elapsedTime = 0;
	private Player player;
	private volatile ArrayList<Environment> dungeonLevels;
//	private DungeonGenerator dungeonLevelFactory = new DefaultDungeonLevelGenerator();
	private DungeonGenerator dungeonLevelFactory = new CaveDungeonGenerator();
//	private DungeonGenerator dungeonLevelFactory = new BigRoomDungeonGenerator();
	
	public State() {
		
	}
	
	public void computeNextState() {
		
		Environment level = getCurrentEnvironment();
		
		for(Character character : new ArrayList<>(level.getEntities(Character.class))) {
			if(character.isAlive() || character instanceof Player) {
					character.postEvent(new TurnEvent());
					character.takeTurn();
			}
		}
		
		elapsedTime += 1000;
	}
	
	public void render() {
		getCurrentEnvironment().render();
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Environment getCurrentEnvironment() {
		return player.getEnvironment();
	}

	public Environment getEnvironment(int z) {
		if(z < 0 || z >= Config.DUNGEON_DEPTH) {
			return null;
		}
		
		while(dungeonLevels.size() <= z) {}
		
		return dungeonLevels.get(z);
	}
	
	public void generateDungeonLevels() {
		for(int i = 0; i < Config.DUNGEON_DEPTH; i++) {
			
			Environment newLevel = dungeonLevelFactory.createLevel(i);
			
			dungeonLevels.add(newLevel);
			
			for(Character character : newLevel.getEntities(Character.class)) {
				character.computeVisibility();
			}
		}
	}
	
	public long getElapsedTime() {
		return elapsedTime;
	}
	
	public void init() {
		dungeonLevels = new ArrayList<Environment>();
		generateDungeonLevels();
		
		player = CharacterCreationUI.createCharacter();
		
		Point upstairs = getEnvironment(0).getStairs(Direction.UP).getPosition();
		
		dungeonLevels.get(0).addEntity(player, upstairs);
	}
}
