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
package com.anathema_roguelike.dungeon.generation;

import java.util.ArrayList;

import com.anathema_roguelike.dungeon.Point;

public class FeatureGroup<T extends Feature> extends ArrayList<T> {
	
	public FeatureGroup<T> intersections(Point point) {
		
		FeatureGroup<T> ret = new FeatureGroup<>();
		for(T feature : this) {	
			if(feature.intersects(point)) {
				if(!ret.contains(feature)) {
					ret.add(feature);
				}
			}
		}
		
		return ret;
	}

	public FeatureGroup<T> intersections(Feature targetFeature) {
		
		FeatureGroup<T> ret = new FeatureGroup<>();
		
		for(T feature : this) {
			if(!ret.contains(feature) && feature != targetFeature && feature.intersects(targetFeature)) {
				ret.add(feature);
			}
		}
		
		return ret;
	}
	
	
}
