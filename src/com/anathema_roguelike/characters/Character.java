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
package com.anathema_roguelike.characters;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

import com.anathema_roguelike.characters.actions.Action;
import com.anathema_roguelike.characters.attacks.BasicAttackAbility;
import com.anathema_roguelike.characters.events.MoveEvent;
import com.anathema_roguelike.characters.events.ResourceChangedEvent;
import com.anathema_roguelike.characters.events.TurnEvent;
import com.anathema_roguelike.characters.foes.ai.AIPathFinder;
import com.anathema_roguelike.characters.foes.ai.Faction;
import com.anathema_roguelike.characters.inventory.Inventory;
import com.anathema_roguelike.characters.inventory.PrimaryWeapon;
import com.anathema_roguelike.characters.perks.Perk;
import com.anathema_roguelike.characters.perks.PerkSet;
import com.anathema_roguelike.characters.player.Player;
import com.anathema_roguelike.characters.player.perks.abilities.Ability;
import com.anathema_roguelike.characters.player.perks.specializations.AbilitySpecializationSet;
import com.anathema_roguelike.environment.Direction;
import com.anathema_roguelike.environment.Environment;
import com.anathema_roguelike.environment.Location;
import com.anathema_roguelike.environment.Point;
import com.anathema_roguelike.environment.features.Doorway;
import com.anathema_roguelike.environment.terrain.grounds.Stairs;
import com.anathema_roguelike.main.Config;
import com.anathema_roguelike.main.Entity;
import com.anathema_roguelike.main.Game;
import com.anathema_roguelike.main.display.BufferMask;
import com.anathema_roguelike.main.display.Color;
import com.anathema_roguelike.main.display.VisualRepresentation;
import com.anathema_roguelike.main.ui.messages.Message;
import com.anathema_roguelike.main.utilities.pathfinding.Path;
import com.anathema_roguelike.stats.HasStats;
import com.anathema_roguelike.stats.StatSet;
import com.anathema_roguelike.stats.characterstats.CharacterStat;
import com.anathema_roguelike.stats.characterstats.CharacterStatSet;
import com.anathema_roguelike.stats.characterstats.attributes.Attribute;
import com.anathema_roguelike.stats.characterstats.resources.BoundedResource;
import com.anathema_roguelike.stats.characterstats.resources.Resource;
import com.anathema_roguelike.stats.characterstats.secondarystats.LightEmission;
import com.anathema_roguelike.stats.characterstats.secondarystats.detection.Visibility;
import com.anathema_roguelike.stats.characterstats.secondarystats.detection.VisibilityLevel;
import com.anathema_roguelike.stats.effects.Effect;
import com.anathema_roguelike.stats.effects.HasEffect;
import com.anathema_roguelike.stimuli.PercievedStimulus;
import com.anathema_roguelike.stimuli.Sight;
import com.anathema_roguelike.stimuli.Stimulus;
import com.anathema_roguelike.stimuli.StimulusEvent;
import com.google.common.base.Predicate;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public abstract class Character extends Entity implements HasStats<Character, CharacterStat> {
	
	private int faction;
	
	private int level = 0;
	
	private boolean actionRemaining = false;
	private long turn = 0;
	
	private PerkSet perks = new PerkSet();
	private AbilitySpecializationSet abilitySpecializations = new AbilitySpecializationSet();
	private EventBus eventBus = new EventBus();
	
	private Inventory inventory = new Inventory(this, eventBus);
	
	private boolean alive = true;
	
	private double facing = Direction.UP;
	BufferMask currentVisibility;
	
	private CharacterStatSet stats = new CharacterStatSet(this, eventBus);
	private LinkedList<PercievedStimulus> percievedStimuli = new LinkedList<>();
	
	public abstract void onDeath();
	
	protected abstract void onTurn();
	
	public Character(Optional<VisualRepresentation> representation) {
		super(representation);
		
		Game.getInstance().getEventBus().register(this);
		eventBus.register(this);
		
		new BasicAttackAbility().grant(this);
	}
	
	public void takeTurn() {
		setActionRemaining(true);
		pruneStimuli();
		onTurn();
	}
	
	public EventBus getEventBus() {
		return eventBus;
	}
	
	public void registerHandler(Object obj) {
		eventBus.register(obj);
	}
	
	public void unregisterHandler(Object obj) {
		eventBus.unregister(obj);
	}
	
	public void postEvent(Object obj) {
		eventBus.post(obj);
	}
	
	public void levelUp() {
		
		level++;
	}
	
	@Override
	public StatSet<Character, CharacterStat> getStatSet() {
		return stats;
	}
	
	public boolean getActionRemaining() {
		return actionRemaining;
	}
	
	public void setActionRemaining(boolean actionRemaining) {
		this.actionRemaining = actionRemaining;
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
		return perks.get(superclass, predicate);
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
		getPerks(BasicAttackAbility.class).iterator().next().applyToTarget(target);
	}
	
	@Subscribe
	public void handleResourceChangedEvent(ResourceChangedEvent event) {
		//TODO not sure if i need to do anything in this case but figure its a good thing to have an event for
	}
	
	@Subscribe
	public void handleTurnEvent(TurnEvent e) {
		turn++;
	}
	
	public long getTurn() {
		return turn;
	}

	public boolean moveCharacterBy(int x,  int y) {
		return moveCharacterTo(new Point(getX() + x, getY() + y), false);
	}
	
	public boolean move(int direction) {
		return moveCharacterTo(Direction.offset(getPosition(), direction), false);
	}
	
	public void takeAction(Action action) {
		action.take(this);
	}
	
	public boolean moveCharacterTo(Point point, boolean teleport) {
		return moveCharacterTo(getEnvironment().getLocation(point), teleport);
	}
	
	public boolean moveCharacterTo(Location location, boolean teleport) {
		
		if(!location.isPassable()) {
			return false;
		} else if(location.getTerrain() instanceof Doorway && this instanceof Player) {
			Doorway door = (Doorway)location.getTerrain();
			door.open();
			
			return true;
		} else {
			
			if(teleport) { 
				
				Collection<Character> characters = location.getEntities(Character.class);
				
				if(characters.size() > 0) {
					return false;
				} else {
					getEnvironment().moveEntityTo(this, location);
					
					return true;
				}
			} else {
				
				AIPathFinder pathfinder = new AIPathFinder(this);
				Path path = pathfinder.getPath(getPosition(), location.getPosition());
				
				//shouldnt try to move into the space they are already in
				path.remove(0);
				
				for(Point p : path) {
					Collection<Character> characters = location.getEntities(Character.class);
					
					if(characters.size() > 0) {
						Character character = location.getEntities(Character.class).iterator().next();
						
						if(!Faction.friendly(character, this)) {
							basicAttack(character);
						}
						
						return false;
					}
					
					//need to decide how/if to not pay cost when the character cant actually move
					getEnvironment().getEventBus().post(new MoveEvent(this, location));
					
					getEnvironment().getLocation(p).generateStimulus(new Sight((int) getStatAmount(Visibility.class), this));
					
					getEnvironment().moveEntityTo(this, p);
				}
				
				return true;
			}
		}
	}
	
	public boolean moveCharacterTo(Point point) {
		return moveCharacterTo(point, false);
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
	
	public void setResource(Character initiator, HasEffect<? extends Effect<Character, ?>> source, Class<? extends Resource> stat, int amount) {
		stats.getStat(stat).set(initiator, source, amount);
	}
	
	public void modifyResource(Character initiator, HasEffect<? extends Effect<Character, ?>> source, Class<? extends Resource> stat, int amount) {
		Resource resource = stats.getStat(stat);
		resource.modify(initiator, source, amount);
	}
	
	public boolean takeStairs(int direction) {
		
		int zOffset = 0;
		int newStairDirection = 0;
		
		if(getEnvironment().getLocation(getPosition()).getTerrain() instanceof Stairs) {
			Stairs stairs = (Stairs) getEnvironment().getLocation(getPosition()).getTerrain();
			if(stairs.takeStairs(direction)) {
				if(direction == Direction.UP) {
					zOffset = -1;
					newStairDirection = Direction.DOWN;
				} else if(direction == Direction.DOWN) {
					zOffset = 1;
					newStairDirection = Direction.UP;
				}
				
				int newDepth = getEnvironment().getDepth() + zOffset;
				
				if(newDepth < 0 || newDepth >= Config.DUNGEON_DEPTH) {
					Game.getInstance().getUserInterface().addMessage(new Message("No!", Color.ERROR));
					return false;
				}
				
				Environment newEnvironment = Game.getInstance().getState().getEnvironment(newDepth);
				Location stairsLocation = newEnvironment.getStairs(newStairDirection).getLocation();
				
				getEnvironment().removeEntity(this);
				newEnvironment.addEntity(this, stairsLocation);
				
				
				return true;
			}
		}
		
		return false;
	}
	
	public VisibilityLevel getVisibilityOf(Character character) {
		if(character == null) {
			return VisibilityLevel.IMPERCEPTIBLE;
		}
		
		if(getCurrentVisibility().get(character.getX(), character.getY())) {
			if(getPosition().isAdjacentTo(character.getPosition())) {
				return VisibilityLevel.EXPOSED;
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
	
	public boolean hasLineOfSightTo(Location location) {
		return getCurrentVisibility().get(location.getX(), location.getY());
	}
	
	public boolean hasLineOfEffectTo(Location location) {
		return getLocation().getEnvironment().lineOfEffectBetween(getLocation(), location);
	}
	
	public void computeVisibility() {
		getEnvironment().getLightLevels().recomputeLightLevels();
		currentVisibility = getEnvironment().getLitFOVProcessor().computeLitFOVMask(this);
	}

	public double getFacing() {
		return facing;
	}
	
	public void setFacing(double facing) {
		this.facing = facing;
	}
	
	@Override
	public double getLightEmission() {
		return getStatAmount(LightEmission.class);
	}
	
	@Subscribe
	public void percieveStimulus(StimulusEvent e) {
		Stimulus stimulus = e.getStimulus();
		
		if(stimulus.getSource().isPresent() && Faction.friendly(this, stimulus.getSource().get())) {
			return;
		}
		
		Optional<PercievedStimulus> percieved = e.getPercievedStimulus(this);
		
		percieved.ifPresent(p -> percievedStimuli.add(p));
	}
	
	protected void pruneStimuli() {
		percievedStimuli.removeIf(s -> s.getMagnitude() <= 0 || s.getLocation().equals(getLocation()));
	}
	
	public LinkedList<PercievedStimulus> getPercievedStimuli() {
		return percievedStimuli;
	}
}
