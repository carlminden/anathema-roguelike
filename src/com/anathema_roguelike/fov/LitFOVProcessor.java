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
package com.anathema_roguelike.fov;

import com.anathema_roguelike.characters.Character;
import com.anathema_roguelike.characters.Player;
import com.anathema_roguelike.environment.Direction;
import com.anathema_roguelike.environment.Point;
import com.anathema_roguelike.main.Entity;
import com.anathema_roguelike.main.display.BufferMask;
import com.anathema_roguelike.stats.characterstats.secondarystats.Vision;

import squidpony.squidgrid.FOV;

public class LitFOVProcessor extends FOVProcessor {
	
	private LightLevels lightLevels;
	private BufferMask mask;
	
	private Character character;
	private int visionDistance;
	
	public LitFOVProcessor(int width, int height, double[][] resistances, LightLevels lightLevels) {
		super(width, height, resistances);
		
		this.lightLevels = lightLevels;
	}

	@Override
	protected void visit(Entity entity, int x, int y, double light) {
		
		double lightFromSource = LitFOVProcessor.lightFromCharactersPerspective(character, visionDistance, new Point(x, y), lightLevels);
		
		mask.set(x, y, lightFromSource > .2 && light > 0);
	}
	
	public BufferMask computeLitFOVMask(Character character) {
		
		this.character = character;
		this.visionDistance = (int) character.getStatAmount(Vision.class);
		
		mask = new BufferMask(width, height);
		
		int angle;
		
		if(character instanceof Player) {
			angle = 360;
		} else {
			angle = visionDistance * 10;
		}
		
		doFOV(character, visionDistance, character.getFacing(), angle, new FOV(FOV.RIPPLE_TIGHT));
		
		return mask;
	}
	
	public static double lightFromCharactersPerspective(Character character, double visionDistance, Point lightSource, LightLevels lightLevels) {
		
		int directionToLight = Direction.approximationOf(character.getPosition(), lightSource);
		double lightFromSource = lightLevels.get(lightSource, directionToLight) + .3;
		
		if(lightFromSource > 0) {
			int distanceToCharacter = character.getPosition().squareDistance(lightSource);
			
			if(distanceToCharacter != 0) {
				lightFromSource *= Math.min(1, (visionDistance / (distanceToCharacter))); 
			}
		}
		
		return lightFromSource;
	}
}
