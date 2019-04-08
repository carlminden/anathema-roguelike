

package com.anathema_roguelike
package main.utilities.datastructures

import scala.reflect.runtime.universe._

object CollectionUtils {
  def filterByClass[U : TypeTag, F <: U](unfiltered: Iterable[U]): Iterable[F] = {
    unfiltered.filter {
      superclass => typeTagToClass[U].isAssignableFrom(superclass.getClass)
    }.asInstanceOf[Iterable[F]]
  }
}
