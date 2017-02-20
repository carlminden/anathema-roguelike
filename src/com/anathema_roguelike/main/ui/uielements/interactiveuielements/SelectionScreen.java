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
package com.anathema_roguelike.main.ui.uielements.interactiveuielements;

import com.anathema_roguelike.main.ui.messages.Message;
import com.anathema_roguelike.main.ui.uielements.Screen;
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.menus.MenuDescription;
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.menus.SelectionMenu;
import com.anathema_roguelike.main.utilities.Utils;
import com.google.common.collect.Iterables;

public class SelectionScreen<T> extends Screen<T> {
	
	private SelectionMenu<T> menu;
	private MenuDescription<T> description;
	private TextBox instructions;
	
	public SelectionScreen(String title, Iterable<T> choices, boolean cancellable) {
		super(title, cancellable);
		init(choices, null, cancellable, 1f);
	}
	
	public SelectionScreen(String title, Iterable<T> choices, Message instructionsMessage, boolean cancellable) {
		super(title, cancellable);
		init(choices, instructionsMessage, cancellable, 1f);
	}

	public SelectionScreen(int x, int y, int width, int height, String title, Iterable<T> choices, Message instructionsMessage, boolean cancellable, float background, float contentBackground) {
		super(x, y, width, height, title, cancellable, background);
		init(choices, instructionsMessage, cancellable, contentBackground);
	}
	
	public SelectionScreen(int x, int y, int width, int height, String title, Iterable<T> choices, boolean cancellable, float background, float contentBackground) {
		super(x, y, width, height, title, cancellable, background);
		init(choices, null, cancellable, contentBackground);
	}
	
	private void init(Iterable<T> choices, Message instructionsMessage, boolean cancellable, float contentBackground) {
		int x = getX() + (int)(getWidth() * .05);
		int y = getY() + (int)(getHeight() * .2);
		menu = new SelectionMenu<T>(x, y, (int)(getWidth() * .3), getHeight()/2, false, 1, choices, cancellable, contentBackground);
		
		description = new MenuDescription<T>(menu, Iterables.isEmpty(choices) ? null : Utils.getSuperclass(Iterables.get(choices, 0)));
		
		if(instructionsMessage != null) {
			instructions = new TextBox(x, y - 2, menu.getWidth() + description.getWidth(), 1, instructionsMessage, contentBackground);
			addUIElement(instructions);
		}
		
		addUIElement(description);
		setFocusedUIElement(menu);
	}
	
	@Override
	public T run() {
		
		if(menu.getItems().isEmpty()) {
			return null;
		} else if(menu.getItems().size() == 1) {
			return menu.getItems().get(0).getItem();
		}
		
		return super.run();
	}

	public SelectionMenu<T> getMenu() {
		return menu;
	}
}
