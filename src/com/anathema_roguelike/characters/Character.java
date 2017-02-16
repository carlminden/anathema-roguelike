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
package com.anathema_roguelike.characters;
import java.util.Collection;
import java.util.LinkedList;

import com.anathema_roguelike.characters.abilities.Ability;
import com.anathema_roguelike.characters.abilities.AbilitySet;
import com.anathema_roguelike.characters.actions.Action;
import com.anathema_roguelike.characters.ai.AIPathFinder;
import com.anathema_roguelike.characters.ai.Faction;
import com.anathema_roguelike.characters.attacks.BasicAttackAbility;
import com.anathema_roguelike.characters.classes.CharacterClass;
import com.anathema_roguelike.characters.events.MoveEvent;
import com.anathema_roguelike.characters.events.ResourceChangedEvent;
import com.anathema_roguelike.characters.events.TurnEvent;
import com.anathema_roguelike.characters.inventory.Inventory;
import com.anathema_roguelike.characters.inventory.PrimaryWeapon;
import com.anathema_roguelike.environment.Direction;
import com.anathema_roguelike.environment.Environment;
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
import com.anathema_roguelike.stats.characterstats.resources.CurrentHealth;
import com.anathema_roguelike.stats.characterstats.resources.Resource;
import com.anathema_roguelike.stats.characterstats.secondarystats.Concealment;
import com.anathema_roguelike.stats.characterstats.secondarystats.Health;
import com.anathema_roguelike.stimuli.PercievedStimulus;
import com.anathema_roguelike.stimuli.Stimulus;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public abstract class Character extends Entity implements HasStats<Character, CharacterStat> {
	
	private int faction;
	
	private int level;
	private CharacterClass charClass;
	
	private boolean actionRemaining = false;
	private long turn = 0;
	
	private AbilitySet abilities = new AbilitySet();
	
	private Inventory inventory = new Inventory(this);
	
	private EventBus eventBus = new EventBus();
	
	private boolean alive = true;
	
	private double facing = Direction.UP;
	BufferMask currentVisibility;
	
	private CharacterStatSet stats = new CharacterStatSet(this, eventBus);
	private LinkedList<PercievedStimulus> percievedStimuli = new LinkedList<>();
	
	public abstract void onDeath();
	
	protected abstract void onTurn();
	
	public Character(VisualRepresentation representation) {
		super(representation);
		level = 0;
		
		Game.getInstance().getEventBus().register(this);
		eventBus.register(this);
		
		new BasicAttackAbility(this).grant(this);
	}
	
	public void takeTurn() {
		setActionRemaining(true);
		pruneStimuli();
		onTurn();
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
		
		getStat(CurrentHealth.class).set(this, (int) getStatAmount(Health.class));
		
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
	
	public CharacterClass getCharClass() {
		return charClass;
	}
	
	public int getLevel() {
		return level;
	}
	
	public <T extends Ability> Iterable<T> getAbilities(Class<T> superclass) {
		return abilities.get(superclass);
	}
	
	public <T extends Ability> Iterable<T> getAbilities(Class<T> superclass, Predicate<T> predicate) {
		return abilities.get(superclass, predicate);
	}
	
	public void addAbility(Ability ability) {
		abilities.add(ability);
	}
	
	public void removeAbility(Ability ability) {
		abilities.remove(ability);
	}
	
	public boolean hasAbility(Class<? extends Ability> ability) {
		return !Iterables.isEmpty(getAbilities(ability));
	}
	
	protected void setFaction(int faction) {
		this.faction = faction;
	}

	public void setClass(CharacterClass charClass) {
		this.charClass = charClass;
	}

	public void basicAttack(Point target) {
		getAbilities(BasicAttackAbility.class).iterator().next().activate(target);
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
		
		Environment level = Game.getInstance().getState().getEnvironment(getDepth());
		
		if(!level.isPassable(point)) {
			return false;
		} else if(level.getLocation(point).getTerrain() instanceof Doorway && this instanceof Player) {
			Doorway door = (Doorway)level.getLocation(point).getTerrain();
			door.open();
			
			return true;
		} else {
			
			if(teleport) { 
				
				Collection<Character> characters = level.getEntitiesAt(point, Character.class);
				
				if(characters.size() > 0) {
					return false;
				} else {
					getEnvironment().moveEntityTo(this, point);
					
					return true;
				}
			} else {
				
				AIPathFinder pathfinder = new AIPathFinder(this);
				Path path = pathfinder.getPath(getPosition(), point);
				
				//shouldnt try to move into the space they are already in
				path.remove(0);
				
				for(Point p : path) {
					Collection<Character> characters = level.getEntitiesAt(p, Character.class);
					
					if(characters.size() > 0) {
						Character character = level.getEntitiesAt(p, Character.class).iterator().next();
						
						if(!Faction.friendly(character, this)) {
							basicAttack(character.getPosition());
						}
						
						return false;
					}
					
					//need to decide how/if to not pay cost when the character cant actually move
					getEnvironment().getEventBus().post(new MoveEvent(this, p));
					
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
		return inventory.getEquipedItem(PrimaryWeapon.class).getWeaponDamage(this);
	}
	
	public int getResourceMax(Class<? extends BoundedResource> resource) {
		return getStat(resource).getMaximum();
	}
	
	public void setAbilityScore(Class<? extends Attribute> ability, int amount) {
		stats.getStat(ability).setScore(amount);
	}
	
	public void setResource(Object source, Class<? extends Resource> stat, int amount) {
		stats.getStat(stat).set(source, amount);
	}
	
	public void modifyResource(Object source, Class<? extends Resource> stat, int amount) {
		Resource resource = stats.getStat(stat);
		resource.modify(source, amount);
	}
	
	public boolean takeStairs(int direction) {
		Environment level = Game.getInstance().getState().getEnvironment(getDepth());
		
		int zOffset = 0;
		int newStairDirection = 0;
		
		if(level.getLocation(getPosition()).getTerrain() instanceof Stairs) {
			Stairs stairs = (Stairs) level.getLocation(getPosition()).getTerrain();
			if(stairs.takeStairs(direction)) {
				if(direction == Direction.UP) {
					zOffset = -1;
					newStairDirection = Direction.DOWN;
				} else if(direction == Direction.DOWN) {
					zOffset = 1;
					newStairDirection = Direction.UP;
				}
				
				if((getDepth() + zOffset) < 0 || (getDepth() + zOffset) >= Config.DUNGEON_DEPTH) {
					Game.getInstance().getUserInterface().addMessage(new Message("No!", Color.ERROR));
					return false;
				}
				
				Environment newLevel = Game.getInstance().getState().getEnvironment(getDepth() + zOffset);
				Point stairsPosition = newLevel.getStairs(newStairDirection).getPosition();
				
				setDepth(getDepth() + zOffset);
				
				level.removeEntity(this);
				newLevel.addEntity(this, stairsPosition);
				
				
				return true;
			}
		}
		
		return false;
	}
	
	public Environment getEnvironment() {
		return Game.getInstance().getState().getEnvironment(getDepth());
	}
	
	public boolean canSee(Character character) {	
		return visibilityOf(character) >= 1;
	}
	
	public double visibilityOf(Character character) {
		
		if(character == null) {
			return 0;
		}
		
		if(currentVisibility == null) {
			computeVisibility();
		}
		
		if(currentVisibility.get(character.getX(), character.getY())) {
			double concealment = character.getStatAmount(Concealment.class) / 100.0;
			
			double light = getEnvironment().getLightLevels().get(character.getPosition());
			
			double distance = getPosition().distance(character.getPosition());
			
			if(distance < 2) {
				return 1;
			}
			
			if(this instanceof Player) {
//				System.out.println("CONCEALMENT: " + concealment + ", LIGHT: " + light + "DISTANCE: " + distance + " = " + (concealment * light * (8 / distance)));
			}
			
			return Math.min(1, light * (8 / distance));
		} else {
			return 0;
		}
	}
	
	@Override
	public boolean isVisibleTo(Character character) {
		return character.canSee(this);
	}
	
	public BufferMask getCurrentVisibility() {
		return currentVisibility;
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
	
	@Subscribe
	public void percieveStimulus(Stimulus stimulus) {
		percievedStimuli.add(stimulus.computePercievedStimulus(this));
	}
	
	protected void pruneStimuli() {
		Iterables.removeIf(percievedStimuli, (PercievedStimulus s) -> s.getMagnitude() <= 0);
	}
	
	public LinkedList<PercievedStimulus> getPercievedStimuli() {
		return percievedStimuli;
	}
}
