package com.anathema_roguelike.entities.characters.stimuli;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;

import com.anathema_roguelike.entities.characters.Character;
import com.anathema_roguelike.entities.characters.events.TurnEvent;
import com.anathema_roguelike.entities.characters.foes.ai.Faction;
import com.google.common.collect.TreeMultiset;
import com.google.common.eventbus.Subscribe;

public class PerceivedStimuli implements Collection<PerceivedStimulus>{
	
	private Character character;
	
	private Comparator<PerceivedStimulus> stimuliComparator = new Comparator<PerceivedStimulus>() {
		
		@Override
		public int compare(PerceivedStimulus o1, PerceivedStimulus o2) {
			
			double stimulus1 = o1.getRemainingMagnitude();
			double stimulus2 = o2.getRemainingMagnitude();
			
			if(o1.getLocation().isPresent() && !character.getPosition().equals(o1.getLocation().get())) {
				stimulus1 += 20/o1.getLocation().get().squareDistance(character);
			}
			
			if(o2.getLocation().isPresent() && !character.getPosition().equals(o2.getLocation().get())) {
				stimulus2 += 20/o2.getLocation().get().squareDistance(character);
			}
			
			if(stimulus1 > stimulus2) {
				return 1;
			} else if(stimulus2 > stimulus1) {
				return -1;
			} else {
				return 0;
			}
		}
	};
	
	TreeMultiset<PerceivedStimulus> set = TreeMultiset.create(stimuliComparator);
	
	
	public PerceivedStimuli(Character character) {
		this.character = character;
		
		character.getEventBus().register(this);
	}
	
	protected void prune() {
		set.removeIf(s ->{
			boolean ret = s.getRemainingMagnitude() <= 0 ||
			(s.getLocation().isPresent() && (character.isAdjacentTo(s.getLocation().get()) || !s.getLocation().get().isPassable())) ||
			(s.getStimulus().getOwner().isPresent() ? s.getStimulus().getOwner().get().isVisibleTo(character) : false);
			
			return ret;
		});
	}
	
	@Subscribe
	public void perceiveStimulus(StimulusEvent e) {
		Stimulus stimulus = e.getStimulus();
		
		if(stimulus.getOwner().isPresent() && Faction.friendly(character, stimulus.getOwner().get())) {
			return;
		}
		
		Optional<PerceivedStimulus> perceived = e.getPercievedStimulus(character);
		
		//TODO should override existing stimuli at location if magnitude is greater (including dissipation)
		perceived.ifPresent(p -> set.add(p));
	}
	
	@Subscribe
	public void handleTurnEvent(TurnEvent t) {
		prune();
	}

	public Optional<PerceivedStimulus> mostInterestingStimulus() {
		if(set.size() > 0) {
			return Optional.of(Collections.max(set, stimuliComparator));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public int size() {
		return set.size();
	}

	@Override
	public boolean isEmpty() {
		return set.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return set.contains(o);
	}

	@Override
	public Iterator<PerceivedStimulus> iterator() {
		return set.iterator();
	}

	@Override
	public Object[] toArray() {
		return set.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return set.toArray(a);
	}

	@Override
	public boolean add(PerceivedStimulus e) {
		return set.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return set.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return set.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends PerceivedStimulus> c) {
		return set.addAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return this.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return set.retainAll(c);
	}

	@Override
	public void clear() {
		set.clear();
	}
}
