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
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

import com.anathema_roguelike.actors.Actor;
import com.anathema_roguelike.actors.TimeElapsedEvent;
import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.entities.characters.inventory.PrimaryWeapon;
import com.anathema_roguelike.entities.characters.player.Player;
import com.anathema_roguelike.environment.Environment;
import com.anathema_roguelike.environment.generation.CaveDungeonGenerator;
import com.anathema_roguelike.environment.generation.DungeonGenerator;
import com.anathema_roguelike.main.display.Renderable;
import com.anathema_roguelike.main.ui.charactercreation.CharacterCreationUI;
import com.anathema_roguelike.main.utilities.position.Direction;
import com.anathema_roguelike.main.utilities.position.Point;
import com.anathema_roguelike.stats.characterstats.secondarystats.AttackSpeed;
import com.anathema_roguelike.stats.itemstats.WeaponSpeed;

public class State implements Renderable {
	
	private double elapsedTime = 0;
	double currentSegmentTime = 1;
	private Player player;
	private volatile ArrayList<Environment> dungeonLevels;
//	private DungeonGenerator dungeonLevelFactory = new DefaultDungeonLevelGenerator();
	private DungeonGenerator dungeonLevelFactory = new CaveDungeonGenerator();
//	private DungeonGenerator dungeonLevelFactory = new BigRoomDungeonGenerator();
	
	private PriorityQueue<Actor> actors = new PriorityQueue<Actor>(Collections.reverseOrder(new Comparator<Actor>() {

		@Override
		public int compare(Actor o1, Actor o2) {
			return Double.compare(o1.getEnergyLevel(), o2.getEnergyLevel());
		}
		
	}));
	
	public State() {
		
	}
	
	public void computeNextState() {
		
		Actor currentActor = actors.remove();
		
		while(!(currentActor instanceof Player)) {
			
			if(currentActor.getDuration().isExpired()) {
				currentActor = actors.remove();
				
				continue;
			}
			
			handleActor(currentActor);
			
			actors.add(currentActor);
			
			currentActor = actors.remove();
		}
		
		//Player CharacterAction
		handleActor(currentActor);
		actors.add(currentActor);
		
	}
	
	private void handleActor(Actor actor) {
		double startTime = actor.getEnergyLevel();
		
		if(actor.getEnergyLevel() <= 0) {
			actors.forEach(a -> a.energize());
		} else {
			actor.act();

			double timePassed = startTime - actors.peek().getEnergyLevel();
			
			elapsedTime += timePassed;
			currentSegmentTime -= timePassed;
			
			Game.getInstance().getEventBus().post(new TimeElapsedEvent(elapsedTime));
		}
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

		//TODO: WTF is this? is it to wait for another thread or something? seems like we could do better with a concurrency object
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
	
	public double getElapsedTime() {
		return elapsedTime;
	}
	
	public void init() {
		dungeonLevels = new ArrayList<Environment>();
		generateDungeonLevels();
		
		player = CharacterCreationUI.createCharacter();
		
		Point upstairs = getEnvironment(0).getStairs(Direction.UP).getPosition();
		
		dungeonLevels.get(0).addEntity(player, upstairs);
	}
	
	public void registerActor(Actor actor) {
		actors.add(actor);
	}
	
	public void removeActor(Actor actor) {
		actors.remove(actor);
	}
}
