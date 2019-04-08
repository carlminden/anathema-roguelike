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
package com.anathema_roguelike.entities.characters.foes.ai;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import com.anathema_roguelike.entities.characters.actions.TurnAction;
import com.anathema_roguelike.entities.characters.actions.WaitAction;
import com.anathema_roguelike.entities.characters.foes.Foe;
import com.anathema_roguelike.environment.Location;
import com.anathema_roguelike.main.utilities.pathfinding.Path;
import com.anathema_roguelike.main.utilities.position.Direction;
import com.anathema_roguelike.main.utilities.position.Point;

public class AI {
	private Foe npc;
	
	private AIPathFinder pathfinder;
	
	public AI(Foe npc) {
		this.npc = npc;
		this.pathfinder = new AIPathFinder(npc);
	}
	
	public void addNextPendingAction() {
		Optional<Character> enemy = Optional.empty();
		Optional<Location> target = Optional.empty();
		Optional<PerceivedStimulus> mostInterestingPerceivedStimulus = npc.getMostInterestingStimulus();
		
		if(npc.getVisibleEnemies().count() > 0) {
			enemy = Optional.of(npc.getVisibleEnemies().findFirst().get());
			target = Optional.of(enemy.get().getLocation());
		} else if(mostInterestingPerceivedStimulus.isPresent()) {
			PerceivedStimulus ps = mostInterestingPerceivedStimulus.get();
			if(ps.getLocation().isPresent()) {
			
				npc.getPercievedStimuli().forEach(s -> System.out.println(s));
				
				target = ps.getLocation();
			}
		}
		
		if(target.isPresent()) {
			Path path = pathfinder.getPath(npc, target.get());
			
			if(path != null && !npc.getLocation().equals(target.get())) {
				
				if(npc.getFacing() != Direction.angleOf(npc, target.get())) {
					npc.addPendingAction(new TurnAction(npc, Direction.angleOf(npc, target.get())));
				} else {
					npc.move(Direction.of(npc.getLocation(), path.get(1)));
				}
			} else {
				randomStep();
			}
		} else {
			randomStep();
		}
		
		if(!npc.hasPendingActions()) {
			System.out.println(npc + " did not compute a new action!");
			npc.addPendingAction(new WaitAction(npc));
		}
		
	}
	
	private void randomStep() {
		
		SecureRandom rand = new SecureRandom();
		double action = rand.nextDouble();
		
		if(action < .8) {
			//continue walking
			Point continueWalking = Direction.offset(npc.getPosition(), Direction.angleToDirection(npc.getFacing()));
			
			if(npc.getEnvironment().isPassable(continueWalking)) {
				npc.move(Direction.angleToDirection(npc.getFacing()));
			} else {
				//turn around
				Point reverse = Direction.offset(npc.getPosition(), Direction.angleToDirection((npc.getFacing() + 180) % 360));
				if(npc.getEnvironment().isPassable(reverse)) {
					npc.addPendingAction(new TurnAction(npc, (npc.getFacing() + 180) % 360));
				} else {
					List<Integer> passableDirections = Arrays.stream(Direction.DIRECTIONS_8)
						.filter(d -> npc.getEnvironment().isPassable(Direction.offset(npc.getPosition(), d)))
						.collect(Collectors.toList());
					
					if(passableDirections.isEmpty()) {
						throw new RuntimeException(npc + " is stuck at " + npc.getPosition());
					}
					
					npc.move(passableDirections.get(new Random().nextInt(passableDirections.size())));
				}
			}
		} else if (action < .9) {
			npc.addPendingAction(new WaitAction(npc));
		} else {
			//random turn
			int angle = rand.nextInt(180) - 90;
			
			npc.addPendingAction(new TurnAction(npc, ((npc.getFacing() + angle) + 360) % 360));
		}
	}
}
