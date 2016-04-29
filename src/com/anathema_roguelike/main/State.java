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
package com.anathema_roguelike.main;

import java.util.ArrayList;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.Player;
import com.anathema_roguelike.characters.events.TurnEvent;
import com.anathema_roguelike.dungeon.Direction;
import com.anathema_roguelike.dungeon.DungeonLevel;
import com.anathema_roguelike.dungeon.Point;
import com.anathema_roguelike.dungeon.generation.CaveDungeonGenerator;
import com.anathema_roguelike.dungeon.generation.DungeonGenerator;
import com.anathema_roguelike.main.display.Renderable;
import com.anathema_roguelike.main.ui.charactercreation.CharacterCreationUI;

public class State implements Renderable {
	
	private Player player;
	private volatile ArrayList<DungeonLevel> dungeonLevels;
//	private DungeonGenerator dungeonLevelFactory = new DefaultDungeonLevelGenerator();
	private DungeonGenerator dungeonLevelFactory = new CaveDungeonGenerator();
//	private DungeonGenerator dungeonLevelFactory = new BigRoomDungeonGenerator();
	
	public State() {
		
	}
	
	public void computeNextState() {
		
		DungeonLevel level = getCurrentLevel();
		
		for(Character character : new ArrayList<>(level.getEntities(Character.class))) {
			if(character.isAlive() || character instanceof Player) {
					character.postEvent(new TurnEvent());
					character.takeTurn();
			}
		}
	}
	
	public void render() {
		
		dungeonLevels.get(player.getDepth()).render();
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public DungeonLevel getCurrentLevel() {
		return getDungeonLevel(player.getDepth());
	}

	public DungeonLevel getDungeonLevel(int z) {
		if(z < 0 || z >= Config.DUNGEON_DEPTH) {
			return null;
		}
		
		while(dungeonLevels.size() <= z) {}
		
		return dungeonLevels.get(z);
	}
	
	public void generateDungeonLevels() {
		for(int i = 0; i < Config.DUNGEON_DEPTH; i++) {
			
			DungeonLevel newLevel = dungeonLevelFactory.createLevel(i);
			
			dungeonLevels.add(newLevel);
			
			for(Character character : newLevel.getEntities(Character.class)) {
				character.computeVisibility();
			}
		}
	}

	public void init() {
		dungeonLevels = new ArrayList<DungeonLevel>();
		generateDungeonLevels();
		
		player = CharacterCreationUI.createCharacter();
		
		Point upstairs = getDungeonLevel(0).getStairs(Direction.UP).getPosition();
		
		dungeonLevels.get(0).addEntity(player, upstairs);
	}
}
