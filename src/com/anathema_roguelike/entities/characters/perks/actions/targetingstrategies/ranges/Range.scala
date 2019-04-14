

package com.anathema_roguelike
package entities.characters.perks.actions.targetingstrategies.ranges

import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.{TargetFilter, Targetable}
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.shapes.Shape
import com.anathema_roguelike.main.ui.uielements.interactiveuielements.GetTargetInterface
import com.anathema_roguelike.main.utilities.Utils

import scala.collection.JavaConverters._
import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.entities.characters.perks.actions.targetingstrategies.constraints.TargetConstraint

import scala.reflect.ClassTag
import scala.reflect.runtime.universe._

abstract class Range[T <: Targetable : TypeTag](constraints: TargetConstraint[T, Character]*)
  extends TargetFilter[T, Character](constraints:_*) {

  protected def getShape(character: Character): Shape

  override def getTargets(character: Option[Character]): Iterable[T] = {
    //This seems like it shouldnt be valid if there is no origin
    val c = character.get

    getTargetsInShape(getShape(c), c.getEnvironment, c)
  }

  def getTarget(character: Option[Character]): Option[T] = {
    character.map(c => {
      val validTargets: Iterable[T] = getTargetsInShape(getShape(c), c.getEnvironment, c)

      if (validTargets.size == 1) {
        validTargets.iterator.next
      } else {
        val instructions: String = "Select a " + Utils.getName(getTargetType) + " within " + Utils.getName(this)

        new GetTargetInterface[T](validTargets.asJavaCollection, instructions).run
      }
    })
  }
}
