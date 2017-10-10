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

import java.util.Collection;
import java.util.Optional;

import com.anathema_roguelike.characters.perks.targetingstrategies.Targetable;
import com.anathema_roguelike.environment.Point;
import com.anathema_roguelike.main.Game;
import com.anathema_roguelike.main.animations.Blink;
import com.anathema_roguelike.main.display.Color;
import com.anathema_roguelike.main.display.DungeonMap.DungeonLayer;
import com.anathema_roguelike.main.display.VisualRepresentation;
import com.anathema_roguelike.main.ui.UIConfig;
import com.anathema_roguelike.main.ui.messages.Message;
import com.anathema_roguelike.main.ui.uielements.Border;
import com.anathema_roguelike.main.utilities.datastructures.CircularArrayList;

import squidpony.squidgrid.gui.gdx.SquidInput;

public class GetTargetInterface<T extends Targetable> extends InteractiveUIElement<T> {
	
	private Border mapBorder;
	private CircularArrayList<T> potentialTargets;
	private int index = 0;
	
	//TODO: the whole animation system needs to be rebuilt
	private Blink animation = new Blink(Optional.of(new VisualRepresentation('X', Color.RED)), DungeonLayer.FOREGROUND);
	
	public GetTargetInterface(Collection<T> potentialTargets, String instructions) {
		super(0, UIConfig.MAP_START_Y, UIConfig.DUNGEON_MAP_WIDTH, UIConfig.DUNGEON_MAP_HEIGHT, true, 0f);
		
		this.potentialTargets = new CircularArrayList<T>(potentialTargets);
		
		mapBorder = new Border(Game.getInstance().getUserInterface().getMapPlaceholder(), getTitleString());
		
		Game.getInstance().getUserInterface().addMessage(new Message(instructions, Color.INSTRUCTIONS));
	}

	@Override
	public void processKeyEvent(char key, boolean alt, boolean ctrl, boolean shift) {
		switch(key) {
        case SquidInput.ENTER:
        case ' ':
        	setResult(potentialTargets.get(index));
        	Game.getInstance().getState().getCurrentEnvironment().removeEntity(animation);
        	finish();
        	return;
        case 'j':
        case SquidInput.DOWN_ARROW:
        case 'h':
        case SquidInput.LEFT_ARROW:
        	index++;
        	animation.reset();
        	return;
        case 'k':
        case SquidInput.UP_ARROW:
        case 'l':
        case SquidInput.RIGHT_ARROW:
        	index--;
        	animation.reset();
            return;
        default:
        	return;
		}
	}
	
	@Override
	public boolean processScrollEvent(int amount) {
		index += amount;
		animation.reset();
		return true;
	}
	
	private String getTitleString() {
		return "Current target: " + potentialTargets.get(index);
	}

	@Override
	protected void renderContent() {
		
		T currentlyTargeted = potentialTargets.get(index);
		
		mapBorder.setTitle(getTitleString());
		mapBorder.render();
		
		Game.getInstance().getState().getCurrentEnvironment().moveEntityTo(animation, currentlyTargeted.getLocation());
	}
	
	@Override
	public T run() {
		
		Game.getInstance().getState().getCurrentEnvironment().addEntity(animation, new Point(0, 0));
		
		return super.run();
	}

	@Override
	public void registerMouseCallbacks() {
		// TODO Auto-generated method stub
	}
}
