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
package com.anathema_roguelike.main.ui.messages;

import com.anathema_roguelike.main.ui.uielements.UIElement;

public class MessageBox extends UIElement {
	
	private MessageBuffer messages;
	private boolean pendingNewLine = false;
	
	public MessageBox(int x, int y, int width, int height, String title) {
		super(x, y, width, height, title, 1f);
		
		messages = new MessageBuffer(getWidth(), getHeight());
	}

	@Override
	protected void renderContent() {
		int pos = Math.max(0, getHeight() - messages.size());
		
		for(Message message : messages.getVisibleMessages()) {
			if(message != null) {
				message.render(getX(), getY() + pos);
			}
			
			pos++;
		}
	}
	
	public void addMessage(Message message) {
		if(pendingNewLine) {
			pendingNewLine = false;
			addMessage(new Message("", message.getTurn()));
		}
		messages.addMessage(message);
	}
	
	public void newLine() {
		
		if(pendingNewLine) {
			pendingNewLine = false;
			addMessage(new Message(""));
		}
		
		pendingNewLine = true;
	}
}
