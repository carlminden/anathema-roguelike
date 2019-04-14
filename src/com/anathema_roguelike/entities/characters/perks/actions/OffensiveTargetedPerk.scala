

package com.anathema_roguelike
package entities.characters.perks.actions

import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.TargetFilter
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints.EnemyTargetConstraint
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.ranges.Range
import com.anathema_roguelike.entities.characters.perks.requirements.PerkRequirement

abstract class OffensiveTargetedPerk(name: String, range: Range[Character], filter: TargetFilter[Character, Character], requirements: PerkRequirement*)
  extends GenericTargetedPerk[Character, Character](name, range, filter, requirements:_*) {

  range.addConstraint(new EnemyTargetConstraint)
}