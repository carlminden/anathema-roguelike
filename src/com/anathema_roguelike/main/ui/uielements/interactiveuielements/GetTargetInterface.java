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
import java.util.stream.Collectors;

import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.TargetSet;
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable;
import com.anathema_roguelike.main.Game;
import com.anathema_roguelike.main.display.Color;
import com.anathema_roguelike.main.display.Display.DisplayLayer;
import com.anathema_roguelike.main.display.animations.Blink;
import com.anathema_roguelike.main.display.PointsOutline;
import com.anathema_roguelike.main.display.VisualRepresentation;
import com.anathema_roguelike.main.input.DirectionalKeyHandler;
import com.anathema_roguelike.main.ui.messages.Message;
import com.anathema_roguelike.main.ui.uielements.Border;
import com.anathema_roguelike.main.utilities.position.Point;

import squidpony.squidgrid.gui.gdx.SquidInput;

public class GetTargetInterface<T extends Targetable> extends InteractiveUIElement<T> {
	
	private Border mapBorder;
	private TargetSet<T> targets;
	
	private Blink animation = new Blink(new VisualRepresentation('X', Color.RED), new Point(0, 0), 1f);;
	
	public GetTargetInterface(Collection<T> potentialTargets, String instructions) {
		super(new Point(0, UIConfig.MAP_START_Y), UIConfig.DUNGEON_MAP_WIDTH, UIConfig.DUNGEON_MAP_HEIGHT, true, 0f);
		
		
		this.targets = new TargetSet<>(potentialTargets);
		
		mapBorder = new Border(Game.getInstance().getUserInterface().getMapPlaceholder(), getTitleString());
		
		Game.getInstance().getUserInterface().addMessage(new Message(instructions, Color.INSTRUCTIONS));
	}

	@Override
	public void processKeyEvent(char key, boolean alt, boolean ctrl, boolean shift) {
		
		new DirectionalKeyHandler() {
			
			@Override
			protected void upRight(boolean alt, boolean ctrl, boolean shift) {
				targets.inDirection((Direction.UP_RIGHT));
			}
			
			@Override
			protected void upLeft(boolean alt, boolean ctrl, boolean shift) {
				targets.inDirection((Direction.UP_LEFT));
			}
			
			@Override
			protected void up(boolean alt, boolean ctrl, boolean shift) {
				targets.inDirection((Direction.UP));
			}
			
			@Override
			protected void right(boolean alt, boolean ctrl, boolean shift) {
				targets.inDirection((Direction.RIGHT));
			}
			
			@Override
			protected void left(boolean alt, boolean ctrl, boolean shift) {
				targets.inDirection((Direction.LEFT));
			}
			
			@Override
			protected void downRight(boolean alt, boolean ctrl, boolean shift) {
				targets.inDirection((Direction.DOWN_RIGHT));
			}
			
			@Override
			protected void downLeft(boolean alt, boolean ctrl, boolean shift) {
				targets.inDirection((Direction.DOWN_LEFT));
			}
			
			@Override
			protected void down(boolean alt, boolean ctrl, boolean shift) {
				targets.inDirection((Direction.DOWN));
			}
			
			@Override
			protected void handleKey(char key, boolean alt, boolean ctrl, boolean shift) {
				switch(key) {
			        case SquidInput.ENTER:
			        case ' ':
			        	setResult(targets.current());
			        	finish();
			        	return;
			        default:
				}
			}
		}.handle(key, alt, ctrl, shift);
	}
	
	@Override
	public void finish() {
		animation.finish();
		super.finish();
	}
	
	@Override
	public boolean processScrollEvent(int amount) {
		targets.next(amount);
		return true;
	}
	
	private String getTitleString() {
		return "Current target: " + currentlyTargeted();
	}

	@Override
	protected void renderContent() {
		
		mapBorder.setTitle(getTitleString());
		mapBorder.render();
		
		
		Game.getInstance().getDisplay().renderOutline(
			DisplayLayer.DUNGEON_OVERLAY,
			new PointsOutline(UIConfig.DUNGEON_OFFSET,targets.getTargets().stream().map(t -> t.getPosition()).collect(Collectors.toList()),Color.ERROR)
		);
	}
	
	private T currentlyTargeted() {
		animation.moveTo(targets.current().getPosition());
		return targets.current();
	}
	
	@Override
	public T run() {
		
		animation.moveTo(currentlyTargeted().getPosition());
		animation.create(DisplayLayer.DUNGEON_OVERLAY, UIConfig.DUNGEON_OFFSET);
		return super.run();
	}

	@Override
	public void registerMouseCallbacks() {
		// TODO Auto-generated method stub
	}
}
