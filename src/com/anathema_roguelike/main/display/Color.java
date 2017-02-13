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
package com.anathema_roguelike.main.display;

import com.google.common.collect.HashBasedTable;

import squidpony.squidgrid.gui.gdx.SColor;
import squidpony.squidgrid.gui.gdx.SColorFactory;

public class Color {
	
	public static final SColorFactory factory = new SColorFactory();

	public static final SColor RED = SColor.RED;
	public static final SColor DARK_GRAY = factory.dim(factory.dimmer(SColor.GRAY));
	public static final SColor GREEN = SColor.KELLY_GREEN;
	public static final SColor BROWN = SColor.AMBER_DYE;
	public static final SColor ABILITY = factory.light(SColor.SAFETY_ORANGE);
	public static final SColor ERROR = SColor.AMETHYST;
	public static final SColor WHITE = SColor.WHITE;
	public static final SColor BLACK = SColor.BLACK;
	public static final SColor FAILURE = SColor.CARMINE;
	public static final SColor LIGHT_BROWN = factory.lighter(SColor.BROWN);;
	public static final SColor LIGHT_RED = factory.lighter(SColor.RED);
	public static final SColor DARK_GREEN = SColor.ISLAMIC_GREEN;
	public static final SColor CRIT = SColor.YELLOW;
	public static final SColor MANA = SColor.BLUE;
	public static final SColor SPIRIT = SColor.TURQUOISE;
	public static final SColor INSTRUCTIONS = SColor.GOLDEN;
	public static final SColor TORCHLIGHT = factory.lightest(SColor.LEMON);
	public static final SColor FOG_OF_WAR_GROUND = new SColor(0x2534A6);
	public static final SColor NO_LIGHT_BACKGROUND = new SColor(0x080913);
	public static final SColor FULL_LIGHT_BACKGROUND = new SColor(0x171E4E);
	public static final SColor NO_LIGHT_PLAYER = new SColor(0x4F4F4F);
	public static final SColor UNAWARE = new SColor(64, 127, 127);
	public static final SColor ALERTED = new SColor(128, 125, 21);
	public static final SColor DETECTED = new SColor(85, 0, 0);
	
	public static final HashBasedTable<SColor, Float, SColor> opacityCache = HashBasedTable.create();
	
	public static SColor opacity(SColor color, float opacity) {
		
		SColor cached = opacityCache.get(color, opacity);
		
		if(cached != null) {
			return cached;
		} else {
		
			SColor temp = new SColor(color);
		
			temp.set(color.r, color.g, color.b, opacity);
			
			opacityCache.put(color, opacity, temp);
			
			return temp;
		}
	}
}