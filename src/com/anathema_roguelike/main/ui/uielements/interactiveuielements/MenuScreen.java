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
import com.anathema_roguelike.main.ui.uielements.Screen;
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.menus.Menu;
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.menus.MenuDescription;
import com.anathema_roguelike.main.utilities.Utils;
import com.google.common.collect.Iterables;

public abstract class MenuScreen<T, M extends Menu<T>> extends Screen<T> {
	
	private M menu;
	private MenuDescription<T> description;
	private TextBox instructions;
	
	private Collection<T> choices;
	private Message instructionsMessage;
	private boolean cancellable;
	private float contentBackground;
	
	public MenuScreen(String title, Collection<T> choices, boolean cancellable) {
		super(title, cancellable);
		init(choices, null, cancellable, 1f);
	}
	
	public MenuScreen(String title, Collection<T> choices, Message instructionsMessage, boolean cancellable) {
		super(title, cancellable);
		init(choices, instructionsMessage, cancellable, 1f);
	}

	public MenuScreen(int x, int y, int width, int height, String title, Message instructionsMessage,
			boolean cancellable, float background, float contentBackground, Collection<T> choices) {
		
		super(x, y, width, height, title, cancellable, background);
		init(choices, instructionsMessage, cancellable, contentBackground);
	}
	
	public MenuScreen(int x, int y, int width, int height, String title, boolean cancellable, float background,
			float contentBackground, Collection<T> choices) {
		
		super(x, y, width, height, title, cancellable, background);
		init(choices, null, cancellable, contentBackground);
	}
	
	@Override
	public T run() {
		int x = getX() + (int)(getWidth() * .05);
		int y = getY() + (int)(getHeight() * .2);
		
		menu = createMenu(x, y, (int)(getWidth() * .3), getHeight()/2, choices, cancellable, contentBackground);
		description = new MenuDescription<T>(menu, choices.isEmpty() ? null : Utils.getSuperclass(Iterables.get(choices, 0)));
		
		if(instructionsMessage != null) {
			instructions = new TextBox(x, y - 2, menu.getWidth() + description.getWidth(), 1, instructionsMessage, contentBackground);
			addUIElement(instructions);
		}
		
		addUIElement(description);
		setFocusedUIElement(menu);
		
		return super.run();
	}
	private void init(Collection<T> choices, Message instructionsMessage, boolean cancellable, float contentBackground) {
		this.choices = choices;
		this.instructionsMessage = instructionsMessage;
		this.cancellable = cancellable;
		this.contentBackground = contentBackground;
	}
	
	public Collection<T> getChoices() {
		return choices;
	}
	
	protected void setInstructionsMessage(Message message) {
		instructions.setMessage(message);
	}
	
	public M getMenu() {
		return menu;
	}
	
	protected abstract M createMenu(int x, int y, int width, int height, Collection<T> choices, boolean cancellable, float background);
}
