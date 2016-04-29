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
package com.anathema_roguelike.characters.ai;

import java.security.SecureRandom;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.NPC;
import com.anathema_roguelike.dungeon.Direction;
import com.anathema_roguelike.dungeon.Point;
import com.anathema_roguelike.main.utilities.pathfinding.Path;

public class AI {
	private NPC npc;
	
	private AIPathFinder pathfinder;
	
	public AI(NPC npc) {
		this.npc = npc;
		this.pathfinder = new AIPathFinder(npc);
	}
	
	public void performAction() {
		Character enemy = null;
		Point target = null;
		
		if(npc.getVisibleEnemies().count() > 0) {
			enemy = npc.getVisibleEnemies().findFirst().get();
			target = enemy.getPosition();
		} else if(npc.getLastSeenNearestEnemy() != null) {
			enemy = npc.getLastSeenNearestEnemy().getKey();
			target = npc.getLastSeenNearestEnemy().getValue();
		}
		
		if(enemy != null) {
			Path path = pathfinder.getPath(npc.getPosition(), target);
			
			if(path != null && !npc.getPosition().equals(target)) {
				npc.moveCharacterTo(path.get(1));
				
				if(!npc.getPosition().equals(target)) {
					npc.setFacing(Direction.angleOf(npc.getPosition(), target));
				}
			} else {
				if(npc.getPosition().equals(target)) {
					npc.forgetLastKnownPosition(enemy);
				}
				randomStep();
			}
		} else {
			randomStep();
		}
		
	}
	
	private void randomStep() {
		
		SecureRandom rand = new SecureRandom();
		double action = rand.nextDouble();
		
		Point step = null;
		
		if(action < .6) {
			//continue walking
			Point continueWalking = Direction.offset(npc.getPosition(), Direction.angleToDirection(npc.getFacing()));
			
			if(npc.getDungeonLevel().isPassable(continueWalking)) {
				step = continueWalking;
			} else {
				//turn around
				step = Direction.offset(npc.getPosition(), Direction.angleToDirection((npc.getFacing() + 180) % 360));
			}
		} else if (action < .9) {
			//do nothing
		} else {
			//random turn
			int angle = rand.nextInt(180) - 90;
			
			step = Direction.offset(npc.getPosition(), Direction.angleToDirection(((npc.getFacing() + angle) + 360) % 360));
		}
		
		if(step != null) {
			int angle = 0;
			
			while(!npc.getDungeonLevel().isPassable(step) && angle <= 360) {
				
				
				step = Direction.offset(npc.getPosition(), Direction.angleToDirection((npc.getFacing() + angle) % 360));
				
				angle += 45;
			}
			
			npc.moveCharacterTo(step);
		}
		
	}
}
