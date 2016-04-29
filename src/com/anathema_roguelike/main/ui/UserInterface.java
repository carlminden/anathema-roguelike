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
package com.anathema_roguelike.main.ui;

import com.anathema_roguelike.main.State;
import com.anathema_roguelike.main.display.Color;
import com.anathema_roguelike.main.display.DungeonMap;
import com.anathema_roguelike.main.display.Display.DisplayLayer;
import com.anathema_roguelike.main.ui.messages.Message;
import com.anathema_roguelike.main.ui.messages.MessageBox;
import com.anathema_roguelike.main.ui.uielements.Placeholder;
import com.anathema_roguelike.main.ui.uielements.PlayerStats;
import com.anathema_roguelike.main.ui.uielements.UIElement;

public class UserInterface extends UIElement {
	
	private MessageBox messageBox;
	private Placeholder mapPlaceholder;
	private DungeonMap map;
	private PlayerStats statsUI;
	
	public UserInterface() {
		super(0, 0, UIConfig.TERM_WIDTH, UIConfig.TERM_HEIGHT, 0f);
	}
	
	public void init(State state) {
		mapPlaceholder = new Placeholder(0, UIConfig.MAP_START_Y, UIConfig.DUNGEON_MAP_WIDTH + 2, UIConfig.DUNGEON_MAP_HEIGHT + 3, "Dungeon Map");
		
		map = new DungeonMap(mapPlaceholder.getX(), mapPlaceholder.getY(), state);
		
		messageBox = new MessageBox(mapPlaceholder.getOuterWidth(), 0, 50, 20, "Messages");
		
		statsUI = new PlayerStats(mapPlaceholder.getOuterWidth(), messageBox.getOuterHeight(),
				messageBox.getOuterWidth(), mapPlaceholder.getOuterHeight() - messageBox.getOuterHeight(), state.getPlayer());
		
		addUIElement(mapPlaceholder);
		addUIElement(messageBox);
		addUIElement(statsUI);
	}
	
	public void addMessage(Message message) {
		messageBox.addMessage(message);
	}
	
	public void addMessage(String message) {
		messageBox.addMessage(new Message(message));
	}
	
	public void newLine() {
		messageBox.newLine();
	}

	public MessageBox getMessageBox() {
		return messageBox;
	}

	public DungeonMap getMap() {
		return map;
	}
	
	public Placeholder getMapPlaceholder() {
		return mapPlaceholder;
	}

	public PlayerStats getStatsUI() {
		return statsUI;
	}

	@Override
	protected void renderContent() {
		for(int i = 0; i < getWidth(); i++) {
			for(int j = 0; j < getHeight(); j++) {
				renderChar(DisplayLayer.UI_BACKGROUND, i, j, ' ', Color.BLACK);
				renderChar(DisplayLayer.UI_FOREGROUND, i, j, ' ', Color.BLACK);
			}
		}
		
		if(map != null) {
			map.render();
		}
	}
}
