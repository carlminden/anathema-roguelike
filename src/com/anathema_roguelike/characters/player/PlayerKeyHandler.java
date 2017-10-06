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
package com.anathema_roguelike.characters.player;

import com.anathema_roguelike.characters.actions.MoveAction;
import com.anathema_roguelike.characters.perks.ActivatedPerk;
import com.anathema_roguelike.environment.Direction;
import com.anathema_roguelike.main.Game;
import com.anathema_roguelike.main.ui.UIConfig;
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.SelectionScreen;

import squidpony.squidgrid.gui.gdx.SquidInput;
import squidpony.squidgrid.gui.gdx.SquidInput.KeyHandler;

public class PlayerKeyHandler implements KeyHandler {
	
	Player player;
	
	public PlayerKeyHandler(Player player) {
		this.player = player;
	}

	@Override
	public void handle(char key, boolean alt, boolean ctrl, boolean shift) {
		
		switch (key) {
	        case '.':
	        	if(shift) {
	        		player.takeStairs(Direction.DOWN);
	        		return;
	        	}
	        case ',':
	        	if(shift) {
	        		player.takeStairs(Direction.UP);
	        		return;
	        	}
	        case 'j':
	        case SquidInput.DOWN_ARROW:
	        	player.takeAction(new MoveAction(Direction.DOWN));
	        	return;
	        case 'k':
	        case SquidInput.UP_ARROW:
	        	player.takeAction(new MoveAction(Direction.UP));
	        	return;
	        case 'h':
	        case SquidInput.LEFT_ARROW:
	        	player.takeAction(new MoveAction(Direction.LEFT));
	        	return;
	        case 'l':
	        case SquidInput.RIGHT_ARROW:
	        	player.takeAction(new MoveAction(Direction.RIGHT));
	        	return;
	        case 'y':
	        case SquidInput.UP_LEFT_ARROW:
	        	player.takeAction(new MoveAction(Direction.UP_LEFT));
	        	return;
	        case 'u':
	        case SquidInput.UP_RIGHT_ARROW:
	        	player.takeAction(new MoveAction(Direction.UP_RIGHT));
	        	return;
	        case 'b':
	        case SquidInput.DOWN_LEFT_ARROW:
	        	player.takeAction(new MoveAction(Direction.DOWN_LEFT));
	        	return;
	        case 'n':
	        case SquidInput.DOWN_RIGHT_ARROW:
	        	player.takeAction(new MoveAction(Direction.DOWN_RIGHT));
	        	return;
	        case SquidInput.CENTER_ARROW:
	        	player.setActionRemaining(false);
	        	return;
	        case 'a':
	        	ActivatedPerk ability = new SelectionScreen<ActivatedPerk>(
        			0, 0, UIConfig.DUNGEON_MAP_WIDTH + 2, UIConfig.DUNGEON_MAP_HEIGHT + 3,
        			"Activate an Ability", true, 0f, .5f, player.getPerks(ActivatedPerk.class)
	        	).run();
	        	
	        	if(ability != null) {
	        		ability.actviate();
	        		player.setActionRemaining(false);
	        	}
	        	return;
	        case 'q':
	        case SquidInput.ESCAPE:
	            Game.getInstance().quit();
	        	return;
	        case ' ':
	        	player.takeStairs(Direction.DOWN);
	        	return;
	        default:
	        	return;
        }
	}

}
