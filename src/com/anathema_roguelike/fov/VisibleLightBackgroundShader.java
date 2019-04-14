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
package com.anathema_roguelike.fov;

import com.anathema_roguelike.main.display.Color;
import com.anathema_roguelike.main.display.DisplayBuffer;
import com.anathema_roguelike.main.display.DisplayCell;

import squidpony.squidgrid.gui.gdx.SColor;
import squidpony.squidmath.PerlinNoise;

public class VisibleLightBackgroundShader extends LightLevelShader {
	
	public VisibleLightBackgroundShader(LightLevels lightLevels) {
		super(lightLevels);
	}

	@Override
	public DisplayCell compute(DisplayBuffer buffer, int x, int y, char string, SColor color, boolean display) {
		
		double light = Math.min(getLightLevels().get(x, y) + PerlinNoise.noise(x, y) * .15, 1.3);
		
		DisplayCell cell = buffer.get(x, y);

		SColor backgroundColor = cell.getColor();
		
		light = LightLevels.anitmateLight(light, x, y);
		
		backgroundColor = Color.factory.blend(Color.NO_LIGHT_BACKGROUND, Color.FULL_LIGHT_BACKGROUND, light);
		
        return new DisplayCell(cell.getChar(), backgroundColor, true);
	}
}
