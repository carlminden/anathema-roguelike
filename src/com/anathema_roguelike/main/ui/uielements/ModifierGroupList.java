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
package com.anathema_roguelike.main.ui.uielements;

import com.anathema_roguelike.characters.effects.Duration;
import com.anathema_roguelike.characters.effects.Effect;
import com.anathema_roguelike.characters.effects.EffectCollection;
import com.anathema_roguelike.characters.effects.modifiers.ModifierGroup;
import com.anathema_roguelike.main.display.Display.DisplayLayer;
import com.anathema_roguelike.main.utilities.Utils;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import squidpony.squidgrid.gui.gdx.SColor;

public class ModifierGroupList extends UIElement {
	
	
	private Predicate<ModifierGroup> predicate;
	private EffectCollection<? extends ModifierGroup> elements;
	private SColor color;

	public ModifierGroupList(int x, int y, int width, int height, String title, SColor color, EffectCollection<? extends ModifierGroup> elements, Predicate<ModifierGroup> predicate) {
		super(x, y, width, height, title, 1f);
		
		this.predicate = predicate;
		this.color = color;
		this.elements = elements;
		
		
	}
	
	public ModifierGroupList(int x, int y, int width, int height, String title, SColor color, EffectCollection<? extends ModifierGroup> elements) {
		super(x, y, width, height, title, 1f);
		
		this.predicate = null;
		this.color = color;
		this.elements = elements;		
	}

	@Override
	protected void renderContent() {
		int pos = 0;
		
		Iterable<? extends ModifierGroup> temp;
		
		if(predicate != null) {
			temp = Iterables.filter(elements, predicate);
		} else {
			temp = elements;
		}
		
		for(Effect effect : temp) {
			
			if(effect.getDuration().getType() != Duration.PERMANENT && !(effect.getSource() instanceof Effect)) {
				renderString(DisplayLayer.UI_FOREGROUND, 0, pos++, Utils.getName(effect) + " (" + effect.getDuration().getRemaining() + ")", color);
			}
			
			if(pos > getHeight()) {
				break;
			}
		}
	}
}
