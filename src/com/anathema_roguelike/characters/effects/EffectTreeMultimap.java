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
package com.anathema_roguelike.characters.effects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;

import com.anathema_roguelike.characters.Character;
import com.google.common.collect.TreeMultimap;

public abstract class EffectTreeMultimap<K, E extends Effect> extends EffectCollection<E> {
	
	private Comparator<K> keyComparator = new Comparator<K>() {
		
		@Override
		public int compare(K o1, K o2) {
			if(o1.equals(o2)) {
				return 0;
			} else {
				return o1.hashCode() - o2.hashCode();
			}
		}
	};
	
	private Comparator<E> valueComparator = new Comparator<E>() {

		@Override
		public int compare(E o1, E o2) {
			if(o1.equals(o2)) {
				return 0;
			} else if(o1.getDuration().compareTo(o2.getDuration()) == 0){
				return 1;
			} else {
				return -o1.getDuration().compareTo(o2.getDuration());
			}
		}
	};
	
	private TreeMultimap<K, E> effects = TreeMultimap.create(keyComparator, valueComparator);
	
	public EffectTreeMultimap(Character character) {
		super(character);
	}
	
	protected abstract K getKey(E effect);
	
	@Override
	protected void add(E effect) {
		effects.put(getKey(effect), effect);
	}
	
	public E get(K key) {
		if(effects.containsKey(key)) {
			return effects.get(key).first();
		} else {
			return null;
		}
	}
	
	public Collection<E> getValues() {
		ArrayList<E> ret = new ArrayList<>();
		
		for(K key : effects.keySet()) {
			ret.add(effects.get(key).first());
		}
		
		return ret;
	}
	
	protected TreeMultimap<K, E> getTreeMultimap() {
		return effects;
	}
	
	protected boolean containsUnexpired(K key) {
		if(effects.get(key).isEmpty() || effects.get(key).iterator().next().getDuration().isExpired()) {
			return false;
		} else {
			return true;
		}
	}
	
	public void removeAll(K key) {
		effects.removeAll(key);
	}
	
	@Override
	public void remove(E effect) {
		effects.remove(getKey(effect), effect);
	}
	
	@Override
	public Iterator<E> iterator() {
		return getValues().iterator();
	}
	
	
	@Override
	public void decrement() {
		for(E effect : effects.values()) {
			effect.getDuration().decrement();
		}
	}
	
	@Override
	public void removeExpired() {
		
		HashSet<E> expired = new HashSet<>();
		
		for(E effect : effects.values()) {
			if(effect.getDuration().isExpired()) {
				expired.add(effect);
			}
		}
		
		for(E effect : expired) {
			removeEffect(effect);
		}
	}
}
