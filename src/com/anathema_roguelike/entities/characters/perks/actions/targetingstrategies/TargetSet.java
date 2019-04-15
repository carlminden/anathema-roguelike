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

package com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

import com.anathema_roguelike.main.utilities.datastructures.CircularArrayList;
import com.anathema_roguelike.main.utilities.position.Point;
import com.google.common.collect.TreeBasedTable;

public class TargetSet<T extends Targetable> {
	
	private TreeBasedTable<Integer, Integer, ArrayList<T>> targetPositions;
	private CircularArrayList<T> targetList;
	private int index = 0;
	private int tmpIndex = 0;
	
	public TargetSet(Collection<T> targets) {
		targetPositions = TreeBasedTable.create();
		
		for(T target : targets) {
			ArrayList<T> positionTargets = targetPositions.get(target.getX(), target.getY());
			if(positionTargets == null) {
				targetPositions.put(target.getY(), target.getX(), new ArrayList<>());
				targetPositions.get(target.getY(), target.getX()).add(target);
			}
		}
		
		targetList = new CircularArrayList<>(this.targetPositions.values().stream().flatMap(t -> t.stream()).collect(Collectors.toList()));

		Collections.sort(targetList,Comparator.comparingInt(p -> p.getPosition().squareDistance(new Point(0, 0))));
	}
	
	public T inDirection(int direction) {
		ArrayList<T> currentPosition = get(current().getPosition());
		
		switch (direction) {
			case Direction.UP:
			case Direction.RIGHT:
			case Direction.UP_RIGHT:
			case Direction.UP_LEFT:
				tmpIndex++;
				break;
			case Direction.DOWN:
			case Direction.LEFT:
			case Direction.DOWN_LEFT:
			case Direction.DOWN_RIGHT:
			default:
				tmpIndex--;
		}
		
		if(tmpIndex >= 0 && tmpIndex < currentPosition.size()) {
			return target(new ArrayList<>(currentPosition).get(tmpIndex));
		} else {
			tmpIndex = 0;
			
			CircularArrayList<ArrayList<T>> line = getLine(direction);
			
			switch (direction) {
				case Direction.UP_RIGHT:
				case Direction.UP_LEFT:
				case Direction.DOWN_LEFT:
				case Direction.DOWN_RIGHT:
					ArrayList<T> newTarget = get(Direction.offset(currentPosition.get(0), direction));
					if(newTarget != null) {
						return target(newTarget.get(0));
					} else {
						return next();
					}
				case Direction.UP:
				case Direction.LEFT:
					return target(line.get(line.indexOf(currentPosition) - 1).get(0));
				case Direction.DOWN:
				case Direction.RIGHT:
				default:
					return target(line.get(line.indexOf(currentPosition) + 1).get(0));
			}
		}
	}
	
	public T next() {
		return next(1);
	}
	
	public T next(int amount) {
		index = index + amount;
		
		return targetList.get(index);
	}
	
	public T prev() {
		return next(-1);
	}
	
	public T prev(int amount) {
		return next(-amount);
	}
	
	public T current() {
		return targetList.get(index);
	}
	
	public Collection<T> getTargets() {
		return targetList;
	}
	
	private ArrayList<T> get(Point position) {
		return targetPositions.get(position.getY(), position.getX());
	}
	
	private T target(T target) {
		
		if(target == null) {
			return next();
		}
		
		T oldTarget = current();
		index = targetList.indexOf(target);
		
		if(!current().equals(oldTarget)) {
			return target;
		} else {
			return next();
		}
	}
	
	private CircularArrayList<ArrayList<T>> getLine(int direction) {
		switch (direction) {
			case Direction.UP:
			case Direction.UP_RIGHT:
			case Direction.UP_LEFT:
				return new CircularArrayList<>(targetPositions.column(current().getX()).values());
			case Direction.DOWN:
			case Direction.DOWN_LEFT:
			case Direction.DOWN_RIGHT:
				return new CircularArrayList<>(targetPositions.column(current().getX()).values());
			case Direction.RIGHT:
				return new CircularArrayList<>(targetPositions.row(current().getY()).values());
			case Direction.LEFT:
				return new CircularArrayList<>(targetPositions.row(current().getY()).values());
			default:
				return new CircularArrayList<>(targetPositions.column(current().getX()).values());
		}
	}
	
	/*private T getOpposite(int direction) {
		CircularArrayList<ArrayList<T>> line = getLine(direction);
		Entry<Integer, ArrayList<T>> entry = null;
		
		switch (direction) {
		
		case Direction.DOWN:
		case Direction.DOWN_LEFT:
		case Direction.DOWN_RIGHT:
		case Direction.RIGHT:
			entry = line.get(0);
			break;
		case Direction.UP:
		case Direction.UP_RIGHT:
		case Direction.UP_LEFT:
		case Direction.LEFT:
		default:
			entry = line.get(line.size());
	}
		
		
		if(entry != null && !entry.getValue().get(0).equals(current())) {
			T opposite = entry.getValue().get(0);
			
			if(opposite != null) {
				index = targetList.indexOf(opposite);
				return opposite;
			}
		}
		
		return next();
	}*/
}
