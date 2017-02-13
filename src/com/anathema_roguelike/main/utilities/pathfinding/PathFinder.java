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
package com.anathema_roguelike.main.utilities.pathfinding;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

import com.anathema_roguelike.environment.Direction;
import com.anathema_roguelike.environment.Point;

public abstract class PathFinder {
	
	protected abstract boolean isPassable(Point p, int direction);
	protected abstract int getExtraCost(Point p, int direction, int previousDirection);

	public Path getPath(final Point src, final Point dst) {
		
		Comparator<Node> f = new Comparator<Node>() {

			@Override
			public int compare(Node o1, Node o2) {
				int df = o1.getF() - o2.getF();
				if(df == 0){
					return o1.getPosition().compareTo(o2.getPosition());
				} else {
					return df;
				}
			}
		};
		
		HashMap<Point, Node> closed = new HashMap<>();
		
		HashMap<Point, Node> openMap = new HashMap<>();
		PriorityQueue<Node> openQueue = new PriorityQueue<Node>(25, f);
		
		Node startingNode = new Node(null, dst, 0, 0, distance(dst, src));
		
		
		openQueue.add(startingNode);
		openMap.put(startingNode.getPosition(), startingNode);
		
		int[] directions = getValidDirections();
		
		while(!openQueue.isEmpty() && !closed.containsKey(src)) {
			
			Node current = openQueue.poll();
			openMap.remove(current.getPosition());
			
			closed.put(current.getPosition(), current);
			
			for(int i = 0; i < directions.length; i++) {
				
				int direction = directions[i];
				final Point neighbor = Direction.offset(current.getPosition(), direction);
				
				if(!closed.containsKey(neighbor) && isPassable(neighbor, direction)) {
					
					int cost = getBaseCost(neighbor, direction, current.getDirection()) + getExtraCost(neighbor, direction, current.getDirection());
					
					Node neighborNode = new Node(current, neighbor, direction, current.getG() + cost, distance(neighbor, src));
					
					if(!openMap.containsKey(neighbor)) {
						openQueue.add(neighborNode);
						openMap.put(neighborNode.getPosition(), neighborNode);
					} else if(openMap.get(neighbor).getG() > (current.getG() + cost)) {
						
						Node old = openMap.get(neighbor);
						openQueue.remove(old);
						
						old.setParent(current);
						old.setG(current.getG() + cost);
						openQueue.add(old);
					}
				}
			}
		}
		
		if(openQueue.isEmpty()) {
			return null;
		}
		
		Path path = new Path();
		
		Node node = closed.get(src);
		
		while(node != null) {
			path.add(node.getPosition());
			node = node.getParent();
		}
		
		return path;
	}
	
	protected int[] getValidDirections() {
		return Direction.DIRECTIONS_8;
	}
	
	private static int distance(Point src, Point dst) {
		
		return src.manhattanDistance(dst) * 10;
	}
	
	protected int getBaseCost(Point point, int direction, int previousDirection) {
		/*if(Arrays.binarySearch(Direction.DIRECTIONS_4, direction) >= 0) {
			return 10;
		} else {
			return 14;
		}*/
		//I don't think this makes sense because diagonals are no more expensive than non-diagonals
		return 10;
	}
}
