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
package com.anathema_roguelike.main.ui.charactercreation.attributeselectors;

import java.util.Collection;

import com.anathema_roguelike.characters.Player;
import com.anathema_roguelike.main.display.Color;
import com.anathema_roguelike.main.ui.messages.Message;
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.SelectionScreen;
import com.anathema_roguelike.main.utilities.Utils;
import com.anathema_roguelike.stats.characterstats.attributes.Attribute;

public class AttributeArray extends AttributeSelector {
	
	public static int[] array = new int[] {16, 14, 14, 12, 10};

	@Override
	public void selectScores(Player player) {
		
		Collection<Class<? extends Attribute>> attributes = Utils.getSubclasses(Attribute.class);
		
		for(int i = 0; i < array.length; i++) {
			
			Message instructions = new Message("Choose The Attribute to assign ");
			instructions.appendMessage(Integer.toString(array[i]), Color.GREEN);
			instructions.appendMessage(" points");
		
			SelectionScreen<Class<? extends Attribute>> selectorScreen = new SelectionScreen<Class<? extends Attribute>>("Select your Ability Scores", attributes, instructions, false);
			
			Class<? extends Attribute> attribute = selectorScreen.run();
			
			player.setAttributeScore(attribute, array[i]);
			attributes.remove(attribute);
		}
	}
}