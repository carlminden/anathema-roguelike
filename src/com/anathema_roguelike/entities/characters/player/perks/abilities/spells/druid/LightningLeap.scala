package com.anathema_roguelike
package entities.characters.player.perks.abilities.spells.druid

import com.anathema_roguelike.entities.characters.actions.TargetedAction
import com.anathema_roguelike.entities.characters.perks.actions.GenericTargetedPerk
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.LocationTargeted
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.Targetable
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints.LineOfEffect
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints.LineOfSight
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints.Passable
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.ranges.LongRange
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.ranges.NearbyTargeted
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.shapes.Circle
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.shapes.Shape
import com.anathema_roguelike.entities.characters.player.classes.Druid
import com.anathema_roguelike.entities.characters.player.perks.abilities.spells.Spell
import com.anathema_roguelike.entities.characters.player.perks.specializations.SpecializationCalculation
import com.anathema_roguelike.environment.Location
import com.anathema_roguelike.entities.characters.Character

class LightningLeap() extends Spell[GenericTargetedPerk[Targetable, Location]](4, classOf[Druid]) {
  override protected def createPerk: GenericTargetedPerk[Targetable, Location] = {
    val range = new LongRange[Location](new LineOfSight[Location], new LineOfEffect[Location])
    new GenericTargetedPerk[Targetable, Location](
      "Lightning Leap",
      new NearbyTargeted(range, l => getShape(l)(), new Passable),
      new LocationTargeted[Targetable]) {
      override protected def createAction: TargetedAction[Targetable] = ??? // TODO Auto-generated method stub
    }
  }

  private def getShape(target: Location) = new SpecializationCalculation[Shape](this) {
    override protected def zero: Shape = new Circle(target, () => 3.0)

    override protected def one: Shape = new Circle(target, () => 3.0)

    override protected def two: Shape = new Circle(target, () => 3.0)

    override protected def three: Shape = new Circle(target, () => 3.0)
  }.get
}