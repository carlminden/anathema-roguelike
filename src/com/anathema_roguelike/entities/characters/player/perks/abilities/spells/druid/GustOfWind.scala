package com.anathema_roguelike
package entities.characters.player.perks.abilities.spells.druid

import com.anathema_roguelike.entities.characters.actions.TargetedAction
import com.anathema_roguelike.entities.characters.perks.actions.GenericTargetedPerk
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.{ConalAreaOfEffect, Targetable}
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints.{LineOfEffect, LineOfSight}
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.ranges.VeryShortRange
import com.anathema_roguelike.entities.characters.player.classes.Druid
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell
import com.anathema_roguelike.environment.Location

class GustOfWind() extends Spell[GenericTargetedPerk[Targetable, Location]](2, classOf[Druid]) {
  override protected def createPerk: GenericTargetedPerk[Targetable, Location] = {
    new GenericTargetedPerk(
      "Gust of Wind",
      new VeryShortRange[Location](new LineOfSight[Location], new LineOfEffect[Location]),
      new ConalAreaOfEffect[Targetable, Location](() => if (getSpecializationLevel == 3) 4.0 else 3.0) {
        override protected def getOrigin: Location = getCharacter.getLocation
      }
    ) {
      override protected def createAction: TargetedAction[Targetable] = ???
    }
  }
}
