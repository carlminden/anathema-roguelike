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

import java.util.ArrayList;
import java.util.function.Function;

import com.anathema_roguelike.main.ui.messages.Message;
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.TextBox;
import com.anathema_roguelike.main.utilities.position.Direction;

public class MenuValues<T> extends TextBox {
	
	private Menu<T> menu;
	private Function<T, Message> calculator;
	
	private int start;
	private int length;

	public MenuValues(Menu<T> menu, int xOffset, int start, int length, Function<T, Message> calculator, float background) {
		super(Direction.offset(menu.getPosition(), Direction.RIGHT, xOffset), 5, menu.getHeight(), null, background);
		
		this.menu = menu;
		this.calculator = calculator;
		this.start = start;
		this.length = length;
		
		updateValues();
	}
	
	@Override
	public void renderContent() {
		
		updateValues();
		
		super.renderContent();
	}
	
	private void updateValues() {
		ArrayList<Message> values = new ArrayList<>();
		
		for(int i = 0; i < length; i++) {
			values.add(calculator.apply(menu.getItem(i + start)));
		}
		
		setMessages(values);
	}
}
