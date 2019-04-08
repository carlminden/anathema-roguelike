

package com.anathema_roguelike
package entities.characters.perks.actions

import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.SingleTargeted
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints.SelfOnly
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.ranges.PointBlank
import com.anathema_roguelike.entities.characters.Character

abstract class SelfTargetedPerk(name: String, source: Any)
  extends TargetedPerk[Character](name, source, new PointBlank(new SelfOnly), new SingleTargeted[Character]) {
}
