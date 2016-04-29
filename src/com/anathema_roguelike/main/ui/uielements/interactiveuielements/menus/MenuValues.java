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
package com.anathema_roguelike.main.ui.uielements.interactiveuielements.menus;

import java.util.ArrayList;

import com.anathema_roguelike.main.ui.messages.Message;
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.TextBox;
import com.google.common.base.Function;

public class MenuValues<T> extends TextBox {
	
	private Menu<T> menu;
	private Function<T, Message> calculator;
	
	private int start;
	private int length;

	public MenuValues(Menu<T> menu, int xOffset, int start, int length, Function<T, Message> calculator, float background) {
		super(menu.getX() + xOffset, menu.getY(), 5, menu.getHeight(), null, background);
		
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
			values.add(calculator.apply((T) menu.getItems().get(i + start).getItem()));
		}
		
		setMessages(values);
	}
}
