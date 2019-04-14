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

package com.anathema_roguelike.main.display.animations;

import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.shapes.Ring;
import com.anathema_roguelike.main.display.Color;
import com.anathema_roguelike.main.utilities.position.HasPosition;

public class Ripple extends Animation {
	
	private double radius;
	
	public Ripple(HasPosition position, double radius, float duration) {
		super(position, duration);
		
		this.radius = radius;
	}

	@Override
	protected void update(float percent) {
		
		double currentRadius = radius * percent;
		
		drawRing(currentRadius, 0.05f);
		drawRing(currentRadius + 1, 0.025f);
		
		if(currentRadius > 1) {
			drawRing(currentRadius - 1, 0.025f);
		}
		
	}
	
	private void drawRing(double radius, float opacity) {
		new Ring(getPosition(), () -> radius).getPoints().forEach(p -> {
			renderChar(p, '\u2588', Color.opacity(Color.WHITE, opacity));
		});
	}

}
