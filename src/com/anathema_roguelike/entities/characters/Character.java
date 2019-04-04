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
package com.anathema_roguelike.entities.characters;

import com.anathema_roguelike.actors.Action;
import com.anathema_roguelike.entities.Entity;
import com.anathema_roguelike.entities.characters.actions.BasicMovementAction;
import com.anathema_roguelike.entities.characters.actions.OpenDoorAction;
import com.anathema_roguelike.entities.characters.actions.TakeStairsAction;
import com.anathema_roguelike.entities.characters.actions.attacks.BasicAttack;
import com.anathema_roguelike.entities.characters.actions.attacks.BasicAttackPerk;
import com.anathema_roguelike.entities.characters.events.ResourceChangedEvent;
import com.anathema_roguelike.entities.characters.events.TurnEndEvent;
import com.anathema_roguelike.entities.characters.events.TurnStartEvent;
import com.anathema_roguelike.entities.characters.foes.ai.Faction;
import com.anathema_roguelike.entities.characters.inventory.Inventory;
import com.anathema_roguelike.entities.characters.inventory.PrimaryWeapon;
import com.anathema_roguelike.entities.characters.perks.Perk;
import com.anathema_roguelike.entities.characters.perks.PerkSet;
import com.anathema_roguelike.entities.characters.player.Player;
import com.anathema_roguelike.entities.characters.player.perks.abilities.Ability;
import com.anathema_roguelike.entities.characters.player.perks.specializations.AbilitySpecializationSet;
import com.anathema_roguelike.entities.characters.stimuli.PerceivedStimuli;
import com.anathema_roguelike.entities.events.EnvironmentChangedEvent;
import com.anathema_roguelike.environment.HasLocation;
import com.anathema_roguelike.environment.Location;
import com.anathema_roguelike.environment.features.Doorway;
import com.anathema_roguelike.environment.terrain.grounds.Stairs;
import com.anathema_roguelike.main.display.BufferMask;
import com.anathema_roguelike.main.utilities.position.Direction;
import com.anathema_roguelike.stats.HasStats;
import com.anathema_roguelike.stats.StatSet;
import com.anathema_roguelike.stats.characterstats.CharacterStat;
import com.anathema_roguelike.stats.characterstats.CharacterStats;
import com.anathema_roguelike.stats.characterstats.attributes.Attribute;
import com.anathema_roguelike.stats.characterstats.resources.BoundedResource;
import com.anathema_roguelike.stats.characterstats.resources.Resource;
import com.anathema_roguelike.stats.characterstats.secondarystats.LightEmission;
import com.anathema_roguelike.stats.characterstats.secondarystats.detection.Visibility;
import com.anathema_roguelike.stats.characterstats.secondarystats.detection.VisibilityLevel;
import com.anathema_roguelike.stats.effects.Effect;
import com.anathema_roguelike.stats.effects.HasEffect;
import com.google.common.eventbus.Subscribe;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class Character extends Entity implements HasStats<Character, CharacterStat> {
	
	private int faction;
	
	private int level = 0;
	
	private long turn = 0;
	
	private PerkSet perks = new PerkSet();
	private AbilitySpecializationSet abilitySpecializations = new AbilitySpecializationSet();
	
	private Inventory inventory = new Inventory(this);
	
	private boolean alive = true;
	
	private double facing = Direction.UP;
	private BufferMask currentVisibility;
	
	private CharacterStats stats = new CharacterStats(this, getEventBus());
	private PerceivedStimuli percievedStimuli = new PerceivedStimuli(this);
	private HashMap<Character, Location> lastSeenCharacterLocations = new HashMap<>();
	
	private LinkedList<Action<Character>> pendingActions = new LinkedList<>();
	
	public abstract void onDeath();
	
	protected abstract void setNextPendingAction();
	
	public Character() {
		new BasicAttackPerk().grant(this);
	}
	
	public void levelUp() {
		level++;
	}
	
	@Override
	public StatSet<Character, CharacterStat> getStatSet() {
		return stats;
	}
	
	public Inventory getInventory() {
		return inventory;
	}

	public int getFaction() {
		return faction;
	}
	
	public int getLevel() {
		return level;
	}
	
	public <T extends Perk> Collection<T> getPerks(Class<T> superclass) {
		return perks.get(superclass);
	}
	
	public <T extends Perk> Collection<T> getPerks(Class<T> superclass, Predicate<T> predicate) {
		return perks.get(superclass, predicate::test);
	}
	
	public void addPerk(Perk perk) {
		perks.add(perk);
	}
	
	public void removePerk(Perk perk) {
		perks.remove(perk);
	}
	
	public boolean hasPerk(Class<? extends Perk> perk) {
		return !getPerks(perk).isEmpty();
	}
	
	public int getSpecialization(Class<? extends Ability> ability) {
		return abilitySpecializations.getSpecializationLevel(ability);
	}
	
	public void specialize(Class<? extends Ability> ability, int amount) {
		abilitySpecializations.specialize(ability, amount);
	}
	
	protected void setFaction(int faction) {
		this.faction = faction;
	}

	public void basicAttack(Character target) {
		addPendingAction(new BasicAttack(this, target));
	}
	
	@Subscribe
	public void handleResourceChangedEvent(ResourceChangedEvent event) {
		//TODO not sure if i need to do anything in this case but figure its a good thing to have an event for
	}
	
	@Override
	public void act() {
		computeVisibility();
		
		super.act();
		
		computeVisibility();
		
		lastSeenCharacterLocations.clear();
		getCurrentlyVisibleCharacters().forEach(c -> lastSeenCharacterLocations.put(c, c.getLocation()));
	}
	
	@Override
	public Optional<Action<?>> getNextAction() {
		
		getEventBus().post(new TurnStartEvent());
		
		if(!hasPendingActions()) {
			turn++;
			
			setNextPendingAction();
		}
		
		getEventBus().post(new TurnEndEvent());
		
		return Optional.of(pendingActions.pop());
	}
	
	@Subscribe
	public void handleEnvironmentChangedEvent(EnvironmentChangedEvent e) {
		e.reRegister(percievedStimuli);
	}
	
	public long getTurn() {
		return turn;
	}
	
	public void move(int direction) {
		
		Location location = getEnvironment().getLocation(Direction.offset(getPosition(), direction));
		
		if(!location.isPassable()) {
			return;
		} else if(location.getTerrain() instanceof Doorway && this instanceof Player) {
			Doorway door = (Doorway)location.getTerrain();
			
			addPendingAction(new OpenDoorAction(this, door));
		} else {
			
			Collection<Character> characters = location.getEntities(Character.class);
			
			if(characters.size() > 0) {
				Character character = location.getEntities(Character.class).iterator().next();
				
				if(!Faction.friendly(character, this)) {
					basicAttack(character);
				}
			} else {
				addPendingAction(new BasicMovementAction(this, location));
			}
		}
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public void kill() {
		alive = false;
	}
	
	public int getPrimaryWeaponDamage() {
		return inventory.getSlot(PrimaryWeapon.class).getEquippedItem().getWeaponDamage();
	}
	
	public int getResourceMax(Class<? extends BoundedResource> resource) {
		return getStat(resource).getMaximum();
	}
	
	public void setAttributeScore(Class<? extends Attribute> attribute, int amount) {
		stats.getStat(attribute).setScore(amount);
	}
	
	public void setResource(Optional<Character> initiator, Optional<HasEffect<? extends Effect<Character, ?>>> source, Class<? extends Resource> stat, int amount) {
		stats.getStat(stat).set(initiator, source, amount);
	}
	
	public void modifyResource(Optional<Character> initiator, Optional<HasEffect<? extends Effect<Character, ?>>> source, Class<? extends Resource> stat, int amount) {
		Resource resource = stats.getStat(stat);
		resource.modify(initiator, source, amount);
	}
	
	public void takeStairs(int direction) {
		
		if(getEnvironment().getLocation(getPosition()).getTerrain() instanceof Stairs) {
			Stairs stairs = (Stairs) getEnvironment().getLocation(getPosition()).getTerrain();
			if(stairs.getDirection() == direction) {
				addPendingAction(new TakeStairsAction(this, stairs));
			}
		}
	}
	
	public VisibilityLevel getVisibilityOf(Character character) {
		if(character == null) {
			return VisibilityLevel.IMPERCEPTIBLE;
		}
		
		if(character == this) {
			return VisibilityLevel.EXPOSED;
		}
		
		VisibilityLevel visibility = character.getStat(Visibility.class).getVisibilityLevel();
		
		if(getCurrentVisibility().get(character.getX(), character.getY())) {
			if(getPosition().isAdjacentTo(character.getPosition())) {
				return VisibilityLevel.EXPOSED;
			} else if(getPosition().isAdjacentTo(character) && visibility.ordinal() < 4){
				return VisibilityLevel.VISIBLE;
			} else if(getPosition().distance(character) <= 2 && (visibility == VisibilityLevel.CONCEALED || visibility == VisibilityLevel.PARTIALLYCONCEALED)){
				return VisibilityLevel.VISIBLE;
			}
			
			return character.getStat(Visibility.class).getVisibilityLevel();
		} else {
			return VisibilityLevel.IMPERCEPTIBLE;
		}
	}
	
	@Override
	public boolean isVisibleTo(Character character) {
		return character.getVisibilityOf(this).ordinal() >= 3;
	}
	
	public BufferMask getCurrentVisibility() {
		if(currentVisibility == null) {
			computeVisibility();
		}
		
		return currentVisibility;
	}
	
	public boolean hasLineOfSightTo(HasLocation location) {
		return getCurrentVisibility().get(location.getX(), location.getY());
	}
	
	public boolean hasLineOfEffectTo(HasLocation location) {
		return getLocation().getEnvironment().lineOfEffectBetween(getLocation(), location);
	}
	
	public void computeVisibility() {
		getEnvironment().getLightLevels().recomputeLightLevels();
		currentVisibility = getEnvironment().getLitFOVProcessor().computeLitFOVMask(this);
	}
	
	public ArrayList<Character> getCurrentlyVisibleCharacters() {
		return getEnvironment().getEntities(Character.class).stream().filter(c -> c.isVisibleTo(this)).collect(Collectors.toCollection(ArrayList::new));
	}
	
	public HashMap<Character, Location> getLastSeenCharacterLocations() {
		return lastSeenCharacterLocations;
	}

	public double getFacing() {
		return facing;
	}
	
	public void setFacing(double facing) {
		this.facing = facing;
	}
	
	public PerceivedStimuli getPercievedStimuli() {
		return percievedStimuli;
	}
	
	@Override
	public double getLightEmission() {
		return getStatAmount(LightEmission.class);
	}
	
	public void addPendingAction(Action<Character> pendingAction) {
		this.pendingActions.add(pendingAction);
	}
	
	public void addPendingAction(Optional<Action<Character>> pendingAction) {
		pendingAction.ifPresent(pa -> this.pendingActions.add(pa));
	}
	
	public void addPendingActions(Action<Character> ...pendingActions) {
		this.pendingActions.addAll(Arrays.asList(pendingActions));
	}
	
	public void addPendingActions(Collection<? extends Action<Character>> pendingActions) {
		this.pendingActions.addAll(pendingActions);
	}
	
	public boolean hasPendingActions() {
		return !this.pendingActions.isEmpty();
	}
	
}
