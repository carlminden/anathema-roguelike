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
import java.util.stream.Collectors;

import com.anathema_roguelike.characters.perks.targetingstrategies.TargetSet;
import com.anathema_roguelike.characters.perks.targetingstrategies.Targetable;
import com.anathema_roguelike.main.Game;
import com.anathema_roguelike.main.animations.Blink;
import com.anathema_roguelike.main.display.Color;
import com.anathema_roguelike.main.display.Display.DisplayLayer;
import com.anathema_roguelike.main.display.DungeonMap.DungeonLayer;
import com.anathema_roguelike.main.display.PointsOutline;
import com.anathema_roguelike.main.display.VisualRepresentation;
import com.anathema_roguelike.main.ui.UIConfig;
import com.anathema_roguelike.main.ui.messages.Message;
import com.anathema_roguelike.main.ui.uielements.Border;
import com.anathema_roguelike.main.utilities.position.Direction;
import com.anathema_roguelike.main.utilities.position.Point;

import squidpony.squidgrid.gui.gdx.SquidInput;

public class GetTargetInterface<T extends Targetable> extends InteractiveUIElement<T> {
	
	private Border mapBorder;
	private TargetSet<T> targets;
	
	//TODO: the whole animation system needs to be rebuilt
	private Blink animation = new Blink(Optional.of(new VisualRepresentation('X', Color.RED)), DungeonLayer.FOREGROUND);
	
	public GetTargetInterface(Collection<T> potentialTargets, String instructions) {
		super(new Point(0, UIConfig.MAP_START_Y), UIConfig.DUNGEON_MAP_WIDTH, UIConfig.DUNGEON_MAP_HEIGHT, true, 0f);
		
		
		this.targets = new TargetSet<>(potentialTargets);
		
		mapBorder = new Border(Game.getInstance().getUserInterface().getMapPlaceholder(), getTitleString());
		
		Game.getInstance().getUserInterface().addMessage(new Message(instructions, Color.INSTRUCTIONS));
	}

	@Override
	public void processKeyEvent(char key, boolean alt, boolean ctrl, boolean shift) {
		switch(key) {
        case SquidInput.ENTER:
        case ' ':
        	setResult(targets.current());
        	finish();
        	return;
        case 'j':
        case SquidInput.DOWN_ARROW:
        	targets.inDirection((Direction.DOWN));
        	return;
        case 'k':
        case SquidInput.UP_ARROW:
        	targets.inDirection((Direction.UP));
        	return;
        case 'h':
        case SquidInput.LEFT_ARROW:
        	targets.inDirection((Direction.LEFT));
        	return;
        case 'l':
        case SquidInput.RIGHT_ARROW:
        	targets.inDirection((Direction.RIGHT));
        	return;
        case 'y':
        case SquidInput.UP_LEFT_ARROW:
        	targets.inDirection((Direction.UP_LEFT));
        	return;
        case 'u':
        case SquidInput.UP_RIGHT_ARROW:
        	targets.inDirection((Direction.UP_RIGHT));
        	return;
        case 'b':
        case SquidInput.DOWN_LEFT_ARROW:
        	targets.inDirection((Direction.DOWN_LEFT));
        	return;
        case 'n':
        case SquidInput.DOWN_RIGHT_ARROW:
        	targets.inDirection((Direction.DOWN_RIGHT));
        	return;
        default:
        	return;
		}
	}
	
	@Override
	public void finish() {
		Game.getInstance().getState().getCurrentEnvironment().removeEntity(animation);
		super.finish();
	}
	
	@Override
	public boolean processScrollEvent(int amount) {
		targets.next(amount);
		animation.reset();
		return true;
	}
	
	private String getTitleString() {
		return "Current target: " + currentlyTargeted();
	}

	@Override
	protected void renderContent() {
		
		mapBorder.setTitle(getTitleString());
		mapBorder.render();
		
		
		Game.getInstance().getDisplay().renderOutline(DisplayLayer.DUNGEON_OVERLAY, new PointsOutline(new Point(1,3), targets.getTargets().stream().map(t -> t.getPosition()).collect(Collectors.toList()), Color.ERROR));
		Game.getInstance().getState().getCurrentEnvironment().moveEntityTo(animation, currentlyTargeted().getLocation());
	}
	
	private T currentlyTargeted() {
		return targets.current();
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
