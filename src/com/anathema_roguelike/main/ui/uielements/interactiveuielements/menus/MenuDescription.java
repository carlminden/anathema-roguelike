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
package com.anathema_roguelike.main.ui.uielements.interactiveuielements.menus;

import com.anathema_roguelike.main.ui.messages.Message;
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.TextBox;
import com.anathema_roguelike.main.utilities.Utils;

public class MenuDescription<T> extends TextBox {
	
	private Menu<T> menu;
	
	public MenuDescription(final Menu<T> menu, Object type) {
		super(menu.getX() + menu.getWidth(), menu.getY() - 1, menu.getWidth() * 2, menu.getHeight(), ((type != null) ? Utils.getName(type) : null) + " Description", null, menu.getBackground());
		
		this.menu = menu;
		
		Message initialDescription;
		T initialItem = menu.getFocusedItem();
		
		if(initialItem != null) {
			if(initialItem instanceof Class) {
				initialDescription = new Message(Utils.getDescription((Class<?>) initialItem));
			} else {
				initialDescription = new Message(Utils.getDescription(initialItem.getClass()));
			}
			
			setMessage(initialDescription);
		}
		
		menu.addOnSelectionChangedListener(new OnFocusChangedListener() {

			@Override
			public void onChanged() {
				T focusedItem = menu.getFocusedItem();
				
				if(focusedItem == null) {
					return;
				}
				
				Class<?> cls;
				if(focusedItem instanceof Class<?>) {
					cls = (Class<?>) focusedItem;
				} else {
					cls = focusedItem.getClass();
				}
				
				setMessage(new Message(Utils.getDescription(cls)));
				setPosition(0);
			}
		});
	}
}
