package com.anathema_roguelike.main.input;

import squidpony.squidgrid.gui.gdx.SquidInput;
import squidpony.squidgrid.gui.gdx.SquidInput.KeyHandler;

public abstract class DirectionalKeyHandler implements KeyHandler {
	
	protected abstract void up(boolean alt, boolean ctrl, boolean shift);
	protected abstract void down(boolean alt, boolean ctrl, boolean shift);
	protected abstract void left(boolean alt, boolean ctrl, boolean shift);
	protected abstract void right(boolean alt, boolean ctrl, boolean shift);
	protected abstract void upRight(boolean alt, boolean ctrl, boolean shift);
	protected abstract void upLeft(boolean alt, boolean ctrl, boolean shift);
	protected abstract void downRight(boolean alt, boolean ctrl, boolean shift);
	protected abstract void downLeft(boolean alt, boolean ctrl, boolean shift);
	protected abstract void handleKey(char key, boolean alt, boolean ctrl, boolean shift);
	
	public final void handle(char key, boolean alt, boolean ctrl, boolean shift) {
		switch (key) {
	        case 'j':
	        case SquidInput.DOWN_ARROW:
	        	down(alt, ctrl, shift);
	        	return;
	        case 'k':
	        case SquidInput.UP_ARROW:
	        	up(alt, ctrl, shift);
	        	return;
	        case 'h':
	        case SquidInput.LEFT_ARROW:
	        	left(alt, ctrl, shift);
	        	return;
	        case 'l':
	        case SquidInput.RIGHT_ARROW:
	        	right(alt, ctrl, shift);
	        	return;
	        case 'y':
	        case SquidInput.UP_LEFT_ARROW:
	        case SquidInput.HOME:
	        	upLeft(alt, ctrl, shift);
	        	return;
	        case 'u':
	        case SquidInput.UP_RIGHT_ARROW:
	        case SquidInput.PAGE_UP:
	        	upRight(alt, ctrl, shift);
	        	return;
	        case 'b':
	        case SquidInput.DOWN_LEFT_ARROW:
	        case SquidInput.END:
	        	downLeft(alt, ctrl, shift);
	        	return;
	        case 'n':
	        case SquidInput.DOWN_RIGHT_ARROW:
	        case SquidInput.PAGE_DOWN:
	        	downRight(alt, ctrl, shift);
	        	return;
	        default:
	        	handleKey(key, alt, ctrl, shift);
		}
		
	}
}
