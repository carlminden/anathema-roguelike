

package com.anathema_roguelike
package entities.characters.perks

import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.entities.characters.perks.requirements.PerkRequirement


abstract class Perk(name: String, initialRequirements: PerkRequirement*) {

  var character: Option[Character] = Option.empty
  var requirements: List[PerkRequirement] = initialRequirements.toList

  override def toString: String = name

  def grant(character: Character): Unit = {
    this.character = character

    character.addPerk(this)
    character.getEventBus.register(this)
  }

  def remove(character: Character): Unit = {
    character.removePerk(this)
    character.getEventBus.unregister(this)

    this.character = Option.empty
  }

  def getCharacter: Character = character.get

  protected def setCharacter(character: Character): Unit = {
    this.character = character
  }

  def requirementsMet(): Boolean = requirements.forall(r => r.getCondition())

  def printUnmetConditionMessages(): Unit = {
    for (requirement <- requirements) {
      if (!requirement.getCondition()) requirement.printUnmetConditionMessage()
    }
  }

  def addRequirement(requirement: PerkRequirement): Unit = {
    requirements +:= requirement
  }

  def getPerkRequirements: Iterable[PerkRequirement] = requirements
}
