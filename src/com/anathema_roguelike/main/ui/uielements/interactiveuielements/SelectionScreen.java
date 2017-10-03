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
package com.anathema_roguelike.main.ui.uielements.interactiveuielements;

import java.util.Collection;

import com.anathema_roguelike.main.ui.messages.Message;
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.menus.SelectionMenu;

public class SelectionScreen<T> extends MenuScreen<T, SelectionMenu<T>> {
	
	public SelectionScreen(String title, Collection<T> choices, Message instructionsMessage, boolean cancellable) {
		super(title, choices, instructionsMessage, cancellable);
	}
	
	public SelectionScreen(String title, Collection<T> choices, boolean cancellable) {
		super(title, choices, false);
	}
	
	public SelectionScreen(int x, int y, int width, int height, String title, Message instructionsMessage,
			boolean cancellable, float background, float contentBackground, Collection<T> choices) {
		super(x, y, width, height, title, instructionsMessage, cancellable, background, contentBackground, choices);
	}
	
	public SelectionScreen(int x, int y, int width, int height, String title, boolean cancellable, float background,
			float contentBackground, Collection<T> choices) {
		super(x, y, width, height, title, cancellable, background, contentBackground, choices);
	}

	@Override
	public T run() {
		
		if(getChoices().isEmpty()) {
			return null;
		} else if(getChoices().size() == 1) {
			return getChoices().iterator().next();
		}
		
		return super.run();
	} 
	@Override
	protected SelectionMenu<T> createMenu(int x, int y, int width, int height, Collection<T> choices, boolean cancellable, float background) {
		return new SelectionMenu<T>(x, y, width, height, false, 1, choices, cancellable, background);
	}
}
