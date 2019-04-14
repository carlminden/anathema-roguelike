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
package com.anathema_roguelike.main.ui.uielements.interactiveuielements;

import com.anathema_roguelike.main.Game;
import com.anathema_roguelike.main.display.Display.DisplayLayer;
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.menus.AbstractMenu;
import com.anathema_roguelike.main.utilities.position.Point;

import java.awt.event.KeyEvent;
import java.util.Arrays;

public class SplashScreen extends Screen<String> {
	
	public SplashScreen() {
		super(null, false);
		
		final AbstractMenu<String> menu = new AbstractMenu<String>(new Point(1, 11), getWidth() - 1, 30, true, 2, false, 1f, Arrays.asList("New Game", "Exit"));
		
		menu.setOnSelectListener("New Game", (String obj) -> {
			menu.finish();
		});
		
		menu.setOnSelectListener("Exit", (String obj) -> {
			Game.getInstance().quit();
		});
		
        setFocusedUIElement(menu);
	}
	
	@Override
	protected void renderContent() {
		
		super.renderContent();
		
        renderStringCentered(DisplayLayer.UI_FOREGROUND, 0, "Anathema");
        renderStringCentered(DisplayLayer.UI_FOREGROUND, 1, "A stealth roguelike");
        renderStringCentered(DisplayLayer.UI_FOREGROUND, 3, "Copyright (C) 2017 Carl Minden");
        renderStringCentered(DisplayLayer.UI_FOREGROUND, 6, "This program comes with ABSOLUTELY NO WARRANTY, This is free software, and you are welcome to redistribute it under certain conditions.");
        renderStringCentered(DisplayLayer.UI_FOREGROUND, 8, "------------------------------");
	}
	
	@Override
	public void processKeyEvent(char key, boolean alt, boolean ctrl, boolean shift) {
		
		switch(key) {
	        case 'Q':
	        case KeyEvent.VK_ESCAPE:
	        	Game.getInstance().quit();
		}
		
		super.processKeyEvent(key, alt, ctrl, shift);
	}
        
    private void showLegalNotices() {
    	//TODO do this or some other thing to make shit legal
    }
}
