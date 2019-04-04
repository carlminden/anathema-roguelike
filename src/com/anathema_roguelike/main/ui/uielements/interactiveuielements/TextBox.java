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

import java.util.ArrayList;

import com.anathema_roguelike.main.display.Display.DisplayLayer;
import com.anathema_roguelike.main.ui.messages.Message;
import com.anathema_roguelike.main.utilities.Utils;
import com.anathema_roguelike.main.utilities.position.Point;

import squidpony.squidgrid.gui.gdx.SquidInput;

public class TextBox extends InteractiveUIElement<Void> {
	
	private ArrayList<Message> text = new ArrayList<>();
	private int position = 0;

	public TextBox(Point position, int width, int height, String title, Message text, float background) {
		super(position, width, height, title, true, background);
		
		if(text == null) {
			text = new Message();
		}
		setMessage(text);
	}
	
	public TextBox(Point position, int width, int height, Message text, float background) {
		super(position, width, height, true, background);
		
		if(text == null) {
			text = new Message();
		}
		setMessage(text);
	}

	@Override
	protected void renderContent() {
		
		for(int i = 0; i < Math.min(getHeight(), text.size() - position); i++) {
			text.get(i + position).render(getX(), getY() + i);
		}
		
		if(text.size() - 1 > getHeight() - 1 + position) {
			renderString(DisplayLayer.UI_FOREGROUND, getWidth() - 1, getHeight() - 1, "\u25bc");
		}
		
		if(position > 0) {
			renderString(DisplayLayer.UI_FOREGROUND, getWidth() - 1, 0, "\u25b2");
		}
		
		if(text.size() > getHeight()) {
			renderString(DisplayLayer.UI_FOREGROUND, getWidth() - 1, (int) (((float)position/((float)text.size() - (float)getHeight())) * (float)(getHeight() - 3) + 1), "\u2588");
		}
		
	}
	
	public void setPosition(int position) {
		this.position = position;
	}

	public void setMessage(Message text) {
		this.text = new ArrayList<>(Message.split(text, getWidth() - 1));
	}
	
	public void setMessages(ArrayList<Message> messages) {
		ArrayList<Message> temp = new ArrayList<Message>();
		
		for(Message message : messages) {
			temp.addAll(Message.split(message, getWidth() - 1));
		}
		
		this.text = temp;
	}

	@Override
	public void processKeyEvent(char key, boolean alt, boolean ctrl, boolean shift) {
		
		switch(key) {
			case SquidInput.PAGE_UP:
				if(text.size() > getHeight()) {
					position = Utils.clamp(position - 5, 0, text.size() - getHeight());
				}
				return;
			case SquidInput.PAGE_DOWN:
				if(text.size() > getHeight()) {
					position = Utils.clamp(position + 5, 0, text.size() - getHeight());
				}
				return;
			default:
		}
	}
	
	@Override
	public boolean processScrollEvent(int amount) {
		if(text.size() > getHeight()) {
			position = Utils.clamp(position + amount, 0, text.size() - getHeight());
		}
		
		return true;
	}

	@Override
	public void registerMouseCallbacks() {
		
	}
}
