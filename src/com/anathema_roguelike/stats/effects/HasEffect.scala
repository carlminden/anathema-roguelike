

package com.anathema_roguelike
package stats.effects

trait HasEffect[+E <: Effect[_, _]] {
  def getEffect: Option[E] = Option.empty
}