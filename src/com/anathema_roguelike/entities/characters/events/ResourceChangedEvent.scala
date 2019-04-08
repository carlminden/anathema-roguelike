

package com.anathema_roguelike
package entities.characters.events

import com.anathema_roguelike.stats.characterstats.resources.Resource

class ResourceChangedEvent(var source: Any, var resource: Class[_ <: Resource], var amount: Double) {
  def getSource: Any = source

  def getAmount: Double = amount

  def getResource: Class[_ <: Resource] = resource
}
