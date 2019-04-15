/*******************************************************************************
 * Copyright (c) 2019. Carl Minden
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
package com.anathema_roguelike.main.ui.messages;

import java.util.ArrayList;

import com.anathema_roguelike.main.Game;
import com.anathema_roguelike.main.display.Color;
import com.anathema_roguelike.main.display.Display.DisplayLayer;

import squidpony.squidgrid.gui.gdx.SColor;

public class Message {
	
	private long turn;
	
	private ArrayList<MessagePart> message = new ArrayList<>();
	private int repeating = 1;
	
	public Message() {
		this.turn = Game.getInstance().getTurn();
	}
	
	public Message(String message, SColor color) {
		appendMessage(message, color);
		
		this.turn = Game.getInstance().getTurn();
	}
	
	public Message(String message) {
		appendMessage(message, Color.WHITE);
		
		this.turn = Game.getInstance().getTurn();
	}
	
	public Message(Message m) {
		for(MessagePart part : m.getMessage()) {
			appendMessagePart(part);
		}
		
		this.turn = Game.getInstance().getTurn();
	}
	
	protected Message(long turn) {
		this.turn = turn;
	}
	
	protected Message(String message, SColor color, long turn) {
		appendMessage(message, color);
		
		this.turn = turn;
	}
	
	protected Message(String message, long turn) {
		appendMessage(message, Color.WHITE);
		
		this.turn = turn;
	}
	
	protected Message(Message m, long turn) {
		for(MessagePart part : m.getMessage()) {
			appendMessagePart(part);
		}
		
		this.turn = turn;
	}
	
	public void appendMessagePart(MessagePart part) {
		this.message.add(part);
	}
	
	public void appendMessage(String message, SColor color) {
		this.message.add(new MessagePart(message, color));
	}
	
	public void appendMessage(String message) {
		appendMessage(message, Color.WHITE);
	}
	
	public ArrayList<MessagePart> getMessage() {
		return message;
	}
	
	public long getTurn() {
		return turn;
	}
	
	public void repeat() {
		repeating++;
	}
	
	public String toString() {
		StringBuilder ret = new StringBuilder();
		
		for(MessagePart part : message) {
			ret.append(part);
		}
		
		return ret.toString();
	}
	
	public static ArrayList<Message> split(Message longMessage, int width) {
		
		ArrayList<Message> ret = new ArrayList<>();
		
		Message message = new Message(longMessage.getTurn());
		int position = 0;
		
		for(MessagePart part : longMessage.getMessage()) {
			String str = part.toString();
			while(str.length() > 0) {
			
				int lastNewLine = str.indexOf("\n");
				if(lastNewLine == -1) {
					int length = Math.min(str.length(), width + 1);
					
					if(position + length >= width) {
						length = str.lastIndexOf(" ", width - position);
						
						if(length == -1) {
							if(str.length() > width) {
								message.appendMessage(str.substring(0, width - position), part.getColor());
								str = str.substring(width - position);
							}
						} else {
							message.appendMessage(str.substring(0, length), part.getColor());
							str = str.substring(length + 1);
						}
						
						ret.add(message);
						message = new Message(longMessage.getTurn());
						
						position = 0;
					} else {
						position += length;
						message.appendMessage(str.substring(0, length), part.getColor());
						str = str.substring(length);
					}
				} else {
					int length = lastNewLine;
					
					if(position + length > width) {
						length = str.lastIndexOf(" ", width);
						
						if(length == -1) {
							message.appendMessage(str.substring(0, width - position), part.getColor());
							str = str.substring(width - position);
						} else {
							message.appendMessage(str.substring(0, length), part.getColor());
							str = str.substring(length + 1);
						}
						
						ret.add(message);
						message = new Message(longMessage.getTurn());
					} else {
						position = 0;
						message.appendMessage(str.substring(0, length), part.getColor());
						str = str.substring(length + 1);
						
						ret.add(message);
						ret.add(new Message("", longMessage.getTurn()));
						message = new Message(longMessage.getTurn());
					}
				}
			}
		}
		ret.add(message);
		message = new Message(longMessage.getTurn());
		
		return ret;
	}
	
	public void render(int x, int y) {
		
		int position = 0;
		
		SColor color = Color.WHITE;
		
		for(MessagePart part : message) {
			
			color = part.getColor();
			
			if(Game.getInstance().getTurn() - getTurn() > 1) {
				color = Color.factory.dimmest(color);
			}
			
			Game.getInstance().getDisplay().renderString(DisplayLayer.UI_FOREGROUND, x + position, y, part.toString(), color);
			position += part.toString().length();
		}
		
		if(repeating > 1 && !toString().equals("")) {
			Game.getInstance().getDisplay().renderString(DisplayLayer.UI_FOREGROUND, x + position, y, " x" + repeating, color);
		}
	}
}
