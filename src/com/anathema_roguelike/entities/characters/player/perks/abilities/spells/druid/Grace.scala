package com.anathema_roguelike
package entities.characters.player.perks.abilities.spells.druid

import java.util.Optional

import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.entities.characters.actions.TargetedAction
import com.anathema_roguelike.entities.characters.actions.costs.{ActionCosts, EnergyCost, StimulusCost}
import com.anathema_roguelike.entities.characters.perks.actions.SelfTargetedPerk
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.TargetEffect
import com.anathema_roguelike.entities.characters.player.classes.Druid
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell
import com.anathema_roguelike.entities.characters.stimuli.{Resonance, Sight, Sound}
import com.anathema_roguelike.stats.characterstats.attributes.Agility
import com.anathema_roguelike.stats.effects.{AdditiveArrayCalculation, Effect, Modifier}
import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.stats.Stat.CharacterStat

class Grace(source: Any) extends Spell[SelfTargetedPerk](1, classOf[Druid]) {
  override protected def createPerk: SelfTargetedPerk = new SelfTargetedPerk("Grace") {
    override protected def createAction: TargetedAction[Character] = {


      val costs = new ActionCosts(
        new StimulusCost[Sight](getCharacter, classOf[Sight], () => 25.0),
        new StimulusCost[Sound](getCharacter, classOf[Sound], () => 50.0),
        new StimulusCost[Resonance](getCharacter, classOf[Resonance],
          new AdditiveArrayCalculation(() => getSpecializationLevel, 50.0, 70.0, 100.0, 140.0))
      )

      new TargetedAction[Character](getCharacter, EnergyCost.STANDARD(getCharacter), costs,
        new TargetEffect[Character, CharacterStat]("Grace") {

          override def getEffect: Option[Effect[Character, CharacterStat]] = {
            new Effect[Character, CharacterStat](this, List(
                new Modifier[Agility](new AdditiveArrayCalculation(() => getSpecializationLevel, 5.0, 7.0, 10.0, 14.0))
            ))
          }
        })
    }
  }
}

