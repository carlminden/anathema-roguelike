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
package com.anathema_roguelike.main.ui.charactercreation;

import java.util.Collection;

import com.anathema_roguelike.characters.Player;
import com.anathema_roguelike.characters.classes.CharacterClass;
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.SelectionScreen;
import com.anathema_roguelike.main.utilities.Utils;

public class ClassSelection {
	public static void selectClass(Player player) {
		
		Collection<Class<? extends CharacterClass>> classes = Utils.getListedSubclasses(CharacterClass.class);
		
		SelectionScreen<Class<? extends CharacterClass>> classSelectionScreen = new SelectionScreen<Class<? extends CharacterClass>>("Select your Class", classes, false);
		
		CharacterClass charClass = null;
		
		try {
			charClass = classSelectionScreen.run().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		charClass.chooseVariableProperties();
		
		charClass.apply(player);
		
	}
}
