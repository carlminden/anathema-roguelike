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
package com.anathema_roguelike.main.input;

import com.anathema_roguelike.characters.actions.MoveAction;
import com.anathema_roguelike.characters.perks.ActivatedPerk;
import com.anathema_roguelike.characters.player.Player;
import com.anathema_roguelike.main.Game;
import com.anathema_roguelike.main.ui.UIConfig;
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.SelectionScreen;
import com.anathema_roguelike.main.utilities.position.Direction;
import com.anathema_roguelike.main.utilities.position.Point;

import squidpony.squidgrid.gui.gdx.SquidInput;

public class PlayerKeyHandler extends DirectionalKeyHandler {
	
	Player player;
	
	public PlayerKeyHandler(Player player) {
		this.player = player;
	}

	@Override
	public void up(boolean alt, boolean ctrl, boolean shift) {
		player.takeAction(new MoveAction(Direction.UP));
	}

	@Override
	public void down(boolean alt, boolean ctrl, boolean shift) {
		player.takeAction(new MoveAction(Direction.DOWN));
	}

	@Override
	public void left(boolean alt, boolean ctrl, boolean shift) {
		player.takeAction(new MoveAction(Direction.LEFT));
	}

	@Override
	public void right(boolean alt, boolean ctrl, boolean shift) {
		player.takeAction(new MoveAction(Direction.RIGHT));
	}

	@Override
	public void upRight(boolean alt, boolean ctrl, boolean shift) {
		player.takeAction(new MoveAction(Direction.UP_RIGHT));
	}

	@Override
	public void upLeft(boolean alt, boolean ctrl, boolean shift) {
		player.takeAction(new MoveAction(Direction.UP_LEFT));
	}

	@Override
	public void downRight(boolean alt, boolean ctrl, boolean shift) {
		player.takeAction(new MoveAction(Direction.DOWN_RIGHT));
	}

	@Override
	public void downLeft(boolean alt, boolean ctrl, boolean shift) {
		player.takeAction(new MoveAction(Direction.DOWN_LEFT));
	}

	@Override
	public void handleKey(char key, boolean alt, boolean ctrl, boolean shift) {
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
	        case SquidInput.INSERT:
	        case SquidInput.VERTICAL_ARROW:
	        	player.setActionRemaining(false);
	        	return;
	        case 'a':
	        	ActivatedPerk ability = new SelectionScreen<ActivatedPerk>(
	    			new Point(0, 0), UIConfig.DUNGEON_MAP_WIDTH + 2, UIConfig.DUNGEON_MAP_HEIGHT + 3,
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
	        case 'L':
	        	player.levelUp();
	        	return;
	        default:
	        	return;
	    }
	}

}
