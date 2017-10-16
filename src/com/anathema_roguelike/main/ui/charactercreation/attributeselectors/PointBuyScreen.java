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
package com.anathema_roguelike.main.ui.charactercreation.attributeselectors;

import java.util.Collection;

import javax.annotation.Nullable;

import com.anathema_roguelike.characters.player.Player;
import com.anathema_roguelike.main.display.Color;
import com.anathema_roguelike.main.ui.messages.Message;
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.MenuScreen;
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.TextBox;
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.menus.MenuValues;
import com.anathema_roguelike.main.utilities.Utils;
import com.anathema_roguelike.main.utilities.position.Point;
import com.anathema_roguelike.stats.characterstats.attributes.Attribute;
import com.google.common.base.Function;

public class PointBuyScreen extends MenuScreen<Class<? extends Attribute>, PointBuyMenu> {
	
	private static Message instructionsMessage;
	private static Collection<Class<? extends Attribute>> attributes = Utils.getSubclasses(Attribute.class);
	
	private PointBuyMenu menu;
	private Player player;
	
	
	static {
		instructionsMessage = new Message("You have ");
		instructionsMessage.appendMessage("15", Color.GREEN);
		instructionsMessage.appendMessage(" remaining pointSet to allocate to your Ability scores");
	}
	

	public PointBuyScreen(String title, Player player) {
		super(title, attributes, instructionsMessage, false);
		
		this.player = player;
	}
	
	protected void updateCurrentValues() {
		
		Message instructionsMessage = new Message("You have ");
		instructionsMessage.appendMessage(Integer.toString(menu.getPoints()), Color.GREEN);
		instructionsMessage.appendMessage(" remaining pointSet to allocate to your Ability scores");
		
		setInstructionsMessage(instructionsMessage);
	}
	
	@Override
	public void processKeyEvent(char key, boolean alt, boolean ctrl, boolean shift) {
		super.processKeyEvent(key, alt, ctrl, shift);
		updateCurrentValues();
	}

	@Override
	protected PointBuyMenu createMenu(Point position, int width, int height,
			Collection<? extends Class<? extends Attribute>> choices, boolean cancellable, float background) {
		menu = new PointBuyMenu(position, width, height, cancellable, 1, player);
		
		TextBox currentValues = new MenuValues<Class<? extends Attribute>>(menu, 18, 0, attributes.size(), new Function<Class<? extends Attribute>, Message>() {

			@Override
			public Message apply(@Nullable Class<? extends Attribute> attribute) {
				int current = (int) menu.getPlayer().getStatAmount(attribute);
				
				return new Message(Integer.toString(current), Color.GREEN);
			}
		}, 1.0f);
		
		addUIElement(currentValues);

		return menu;
	}
}
