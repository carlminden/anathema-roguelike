package com.anathema_roguelike.entities.characters.actions;

import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.entities.characters.actions.costs.EnergyCost;
import com.anathema_roguelike.environment.Environment;
import com.anathema_roguelike.environment.Location;
import com.anathema_roguelike.environment.terrain.grounds.Stairs;
import com.anathema_roguelike.main.Config;
import com.anathema_roguelike.main.Game;
import com.anathema_roguelike.main.utilities.position.Direction;

public class TakeStairsAction extends CharacterAction {
	
	private Stairs stairs;
	
	public TakeStairsAction(Character character, Stairs stairs) {
		super(character, EnergyCost.STANDARD(character));

		this.stairs = stairs;
	}
	
	@Override
	protected void onTake() {
		int zOffset = 0;
		int newStairDirection = 0;
		
		if(stairs.getDirection() == Direction.UP) {
			zOffset = -1;
			newStairDirection = Direction.DOWN;
		} else if(stairs.getDirection() == Direction.DOWN) {
			zOffset = 1;
			newStairDirection = Direction.UP;
		}
		
		int newDepth = getActor().getEnvironment().getDepth() + zOffset;
		
		if(newDepth < 0 || newDepth >= Config.DUNGEON_DEPTH) {
			throw new RuntimeException("Dungeon is not deep enough, these stairs shouldn't have been made");
		}
		
		Environment newEnvironment = Game.getInstance().getState().getEnvironment(newDepth);
		Location stairsLocation = newEnvironment.getStairs(newStairDirection).getLocation();
		
		newEnvironment.addEntity(getActor(), stairsLocation);
	}
}
