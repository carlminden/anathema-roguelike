

package com.anathema_roguelike
package entities.characters.perks.actions

import java.util.Optional

import com.anathema_roguelike.actors.Action
import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.entities.characters.perks.Perk
import com.anathema_roguelike.entities.characters.perks.requirements.PerkRequirement

abstract class ActionPerk[T <: Action[Character]](
    name: String,
    source: Any,
    requirements: PerkRequirement*
  ) extends Perk(name, source, requirements:_*) {

  protected def createAction: T

  def activate: Option[Action[Character]] = {
    if (requirementsMet()) {
      createAction
    } else {
      printUnmetConditionMessages()
      Option.empty
    }
  }
}
