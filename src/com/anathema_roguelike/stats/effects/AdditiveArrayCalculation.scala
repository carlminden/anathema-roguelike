

package com.anathema_roguelike
package stats.effects

//TODO this is dumb especially since this is used in non modifier situations,
// I think I need to pull the additive/multaplicative concept out of the types of the calculations
class AdditiveArrayCalculation(index: () => Int, values: Double*) extends AdditiveCalculation {

  override def apply(): Double = values(index())
}
