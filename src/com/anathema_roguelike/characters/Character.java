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

import com.anathema_roguelike.characters.abilities.Ability;
import com.anathema_roguelike.characters.abilities.AbilitySet;
import com.anathema_roguelike.characters.ai.AIPathFinder;
import com.anathema_roguelike.characters.ai.Faction;
import com.anathema_roguelike.characters.attacks.BasicAttackAbility;
import com.anathema_roguelike.characters.classes.CharacterClass;
import com.anathema_roguelike.characters.effects.Effect;
import com.anathema_roguelike.characters.effects.EffectSet;
import com.anathema_roguelike.characters.effects.buffs.BuffCollection;
import com.anathema_roguelike.characters.effects.conditions.Condition;
import com.anathema_roguelike.characters.effects.conditions.ConditionCollection;
import com.anathema_roguelike.characters.effects.descriptors.Descriptor;
import com.anathema_roguelike.characters.events.MoveEvent;
import com.anathema_roguelike.characters.events.ResourceChangedEvent;
import com.anathema_roguelike.characters.events.TurnEvent;
import com.anathema_roguelike.characters.stats.Stat;
import com.anathema_roguelike.characters.stats.StatSet;
import com.anathema_roguelike.characters.stats.abilityscores.AbilityScore;
import com.anathema_roguelike.characters.stats.secondarystats.Concealment;
import com.anathema_roguelike.characters.stats.secondarystats.DamageBonus;
import com.anathema_roguelike.characters.stats.tertiarystats.TertiaryStat;
import com.anathema_roguelike.characters.stats.tertiarystats.resources.CurrentHealth;
import com.anathema_roguelike.characters.stats.tertiarystats.resources.Damage;
import com.anathema_roguelike.characters.stats.tertiarystats.resources.Resource;
import com.anathema_roguelike.dungeon.Direction;
import com.anathema_roguelike.dungeon.Doorway;
import com.anathema_roguelike.dungeon.DungeonLevel;
import com.anathema_roguelike.dungeon.Point;
import com.anathema_roguelike.dungeon.Stairs;
import com.anathema_roguelike.items.Weapon;
import com.anathema_roguelike.main.Config;
import com.anathema_roguelike.main.Entity;
import com.anathema_roguelike.main.Game;
import com.anathema_roguelike.main.display.BufferMask;
import com.anathema_roguelike.main.display.Color;
import com.anathema_roguelike.main.display.VisualRepresentation;
import com.anathema_roguelike.main.ui.messages.Message;
import com.anathema_roguelike.main.utilities.Roll;
import com.anathema_roguelike.main.utilities.Utils;
import com.anathema_roguelike.main.utilities.pathfinding.Path;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public abstract class Character extends Entity {
	
	private int faction;
	
	private int level;
	private CharacterClass charClass;
	private StatSet stats = new StatSet(this);
	private boolean actionRemaining = false;
	private long turn = 0;
	
	private AbilitySet abilities = new AbilitySet();
	
	private EffectSet effects;
	
	private Inventory inventory = new Inventory(this);
	
	private EventBus eventBus = new EventBus();
	
	private boolean alive = true;
	
	private double facing = Direction.UP;
	BufferMask currentVisibility;
	
	public abstract void onDeath();
	public abstract void killedBy(Character attacker);
	
	protected abstract void onTurn();
	
	public Character(VisualRepresentation representation) {
		super(representation);
		level = 0;
		effects = new EffectSet(this);
		
		Game.getInstance().getEventBus().register(this);
		eventBus.register(this);
		
		new BasicAttackAbility(this).grant(this);
	}
	
	public void takeTurn() {
		setActionRemaining(true);
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
		
		stats.get(CurrentHealth.class).reset(null);
		
	}
	
	public Roll rollD20() {
		
		int roll = Utils.roll(1, 20);
		boolean crit = false;
		
		if(roll == 20) {
			crit = true;
		}
		
		return new Roll(roll, crit);
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
	
	public void applyEffect(Effect effect) {
		effects.apply(effect);
	}
	
	public void removeEffect(Effect effect) {
		effects.remove(effect);
	}
	
	public void cureCondition(Class<? extends Condition> condition) {
		effects.cureCondition(condition);
	}
	
	public boolean hasCondition(Class<? extends Condition> condition) {
		return effects.hasCondition(condition);
	}
	
	public ConditionCollection getConditionSet() {
		return effects.getConditions();
	}
	
	public BuffCollection getBuffs() {
		return effects.getBuffs();
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

	protected StatSet getStats() {
		return stats;
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

	public boolean moveCharacterBy(int x,  int y, boolean turn) {
		return moveCharacterTo(new Point(getX() + x, getY() + y), turn, false);
	}
	
	public boolean moveCharacterBy(int x,  int y) {
		return moveCharacterBy(x, y, true);
	}
	
	public boolean move(int direction, boolean turn) {
		setActionRemaining(false);
		return moveCharacterTo(Direction.offset(getPosition(), direction), turn, false);
	}
	
	public boolean move(int direction) {
		return move(direction, true);
	}
	
	public boolean moveCharacterTo(Point point, boolean turn, boolean teleport) {
		
		DungeonLevel level = Game.getInstance().getState().getDungeonLevel(getDepth());
		
		Collection<Character> characters = level.getEntitiesAt(point, Character.class);
		
		if(characters.size() > 0) {
			Character character = characters.iterator().next();
			
			if(character != null) {
				if(!Faction.friendly(character, this)) {
					basicAttack(character.getPosition());
				}
				
				return false;
			}
		}
		
		
		if(level.isPassable(point)) {
			
			if(turn) {
				setFacing(Direction.angleOf(new Point(getX(), getY()), point));
			}
			
			AIPathFinder pathfinder = new AIPathFinder(this);
			Path path = pathfinder.getPath(getPosition(), point);
			
			Point currentPosition = getPosition();
			
			for(Point p : path) {
				getDungeonLevel().getEventBus().post(new MoveEvent(this, p));
				
				getDungeonLevel().moveEntityTo(this, p);
				
				currentPosition = p;
			}
			
			
			return true;
		} else if(level.getDungeonCell(point) instanceof Doorway && this instanceof Player) {
			Doorway door = (Doorway)level.getDungeonCell(point);
			door.open();
			
			return true;
		} else {
			return false;
		}
	}
	
	public boolean moveCharacterTo(Point point) {
		return moveCharacterTo(point, true, false);
	}
	
	public void damage(Character attacker, int damage) {
		
		applyEffect(new Damage(attacker, CurrentHealth.class, damage));
		
		if(getModifiedStatScore(CurrentHealth.class) <= 0 && isAlive()) {
			alive = false;
			//TODO not sure if this should actually be 2 callbacks
			killedBy(attacker);
			onDeath();
		}
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public int getBasicAttackDamage() {
		
		int weaponDamage = inventory.getEquipedItem(Weapon.class).getWeaponDamage(this).roll();
		
		int modifiers = getModifiedStatScore(DamageBonus.class);
		
		return weaponDamage + modifiers;
	}
	
	public int getModifiedStatScore(Class<? extends Stat> stat) {
		int base = stats.get(stat).getAmount();
		int modifier = effects.getStatBonus(stat); 
		
		return (int) ((base + modifier) * effects.getStatMultiplier(stat));
	}
	
	public int getBaseStatScore(Class<? extends Stat> stat) {
		return stats.get(stat).getAmount();
	}
	
	public int getResourceMax(Class<? extends Resource> resource) {
		return stats.get(resource).getMaximum();
	}
	
	public int getAbilityModifier(Class<? extends AbilityScore> ability) {
		return AbilityScore.getModifier(getModifiedStatScore(ability));
	}
	
	public void setAbilityScore(Class<? extends AbilityScore> ability, int amount) {
		stats.get(ability).setScore(amount);
	}
	
	public void setTertiaryStat(Object source, Class<? extends TertiaryStat> stat, int amount) {
		stats.get(stat).set(source, amount);
	}
	
	public void modifyTertiaryStat(Object source, Class<? extends TertiaryStat> stat, int amount) {
		TertiaryStat tertiaryStat = stats.get(stat);
		tertiaryStat.modify(source, amount);
	}
	
	public boolean takeStairs(int direction) {
		DungeonLevel level = Game.getInstance().getState().getDungeonLevel(getDepth());
		
		int zOffset = 0;
		int newStairDirection = 0;
		
		if(level.getDungeonCell(getPosition()) instanceof Stairs) {
			Stairs stairs = (Stairs) level.getDungeonCell(getPosition());
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
				
				DungeonLevel newLevel = Game.getInstance().getState().getDungeonLevel(getDepth() + zOffset);
				Point stairsPosition = newLevel.getStairs(newStairDirection).getPosition();
				
				setDepth(getDepth() + zOffset);
				
				level.removeEntity(this);
				newLevel.addEntity(this, stairsPosition);
				
				
				return true;
			}
		}
		
		return false;
	}
	
	public DungeonLevel getDungeonLevel() {
		return Game.getInstance().getState().getDungeonLevel(getDepth());
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
			double concealment = character.getModifiedStatScore(Concealment.class) / 100.0;
			
			double light = getDungeonLevel().getLightLevels().get(character.getPosition());
			
			double distance = getPosition().distance(character.getPosition());
			
			if(distance < 2) {
				return 1;
			}
			
			if(character instanceof Player) {
//				System.out.println("CONCEALMENT: " + concealment + ", LIGHT: " + light + "DISTANCE: " + distance + " = " + (concealment * light * (8 / distance)));
			}
			
			return Math.min(1, concealment * light * (8 / distance));
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
		getDungeonLevel().getLightLevels().recomputeLightLevels();
		currentVisibility = getDungeonLevel().getLitFOVProcessor().computeLitFOVMask(this);
	}

	public boolean isVulnerableTo(Class<? extends Descriptor> descriptor) {
		return effects.isVulnerableTo(descriptor);
	}
	public double getFacing() {
		return facing;
	}
	
	public void setFacing(double facing) {
		this.facing = facing;
	}
}
