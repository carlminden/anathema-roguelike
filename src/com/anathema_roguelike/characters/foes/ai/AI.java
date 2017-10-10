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
package com.anathema_roguelike.characters.foes.ai;

import java.security.SecureRandom;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.actions.MoveAction;
import com.anathema_roguelike.characters.actions.TurnAction;
import com.anathema_roguelike.characters.foes.Foe;
import com.anathema_roguelike.environment.Direction;
import com.anathema_roguelike.environment.Location;
import com.anathema_roguelike.environment.Point;
import com.anathema_roguelike.main.utilities.pathfinding.Path;

public class AI {
	private Foe npc;
	
	private AIPathFinder pathfinder;
	
	public AI(Foe npc) {
		this.npc = npc;
		this.pathfinder = new AIPathFinder(npc);
	}
	
	public void performAction() {
		Character enemy = null;
		Location target = null;
		
		if(npc.getVisibleEnemies().count() > 0) {
			enemy = npc.getVisibleEnemies().findFirst().get();
			target = enemy.getLocation();
		} else if(npc.getMostInterestingStimulus() != null) {
			target = npc.getMostInterestingStimulus().getLocation();
		}
		
		if(target != null) {
			Path path = pathfinder.getPath(npc, target);
			
			if(path != null && !npc.getPosition().equals(target)) {
				
				if(npc.getFacing() != Direction.angleOf(npc, target)) {
					npc.takeAction(new TurnAction(Direction.angleOf(npc, target)));
				} else {
					npc.takeAction(new MoveAction(Direction.of(npc.getPosition(), path.get(1))));
				}
			} else {
				randomStep();
				
				if(npc.getPosition().equals(target) && target == npc.getMostInterestingStimulus().getLocation()) {
					npc.getPercievedStimuli().remove(npc.getMostInterestingStimulus());
				}
			}
		} else {
			randomStep();
		}
		
	}
	
	private void randomStep() {
		
		SecureRandom rand = new SecureRandom();
		double action = rand.nextDouble();
		
		if(action < 2) {
			//continue walking
			Point continueWalking = Direction.offset(npc.getPosition(), Direction.angleToDirection(npc.getFacing()));
			
			if(npc.getEnvironment().isPassable(continueWalking)) {
				npc.takeAction(new MoveAction(Direction.angleToDirection(npc.getFacing())));
			} else {
				//turn around
				
				npc.takeAction(new TurnAction((npc.getFacing() + 180) % 360));
			}
		} else if (action < .9) {
			//do nothing
		} else {
			//random turn
			int angle = rand.nextInt(180) - 90;
			
			npc.takeAction(new TurnAction(((npc.getFacing() + angle) + 360) % 360));
		}
	}
}
