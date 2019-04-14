/*******************************************************************************
 * Copyright (c) 2019. Carl Minden
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
package com.anathema_roguelike.fov;

import com.anathema_roguelike.main.display.BufferMask;

import squidpony.squidgrid.FOV;

public class LOSMask extends FOVProcessor {
	
	private BufferMask mask;
	
	public LOSMask(int width, int height, double[][] resistances) {
		super(width, height, resistances);
		
		mask = new BufferMask(width, height);
	}

	@Override
	protected void visit(Entity entity, int x, int y, double vision) {
		mask.set(x, y, vision > 0);
	}
	
	public BufferMask getFOVMask(Character character) {
		
		int visionDistance = (int) character.getStatAmount(Vision.class);
		
		int angle;
		
		if(character instanceof Player) {
			angle = 360;
		} else {
			angle = visionDistance * 10;
		}
		
		doFOV(character, visionDistance, character.getFacing(), angle, new FOV(FOV.RIPPLE_TIGHT));
		
		return mask;
	}

}
