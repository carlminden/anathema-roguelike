

package com.anathema_roguelike
package entities.items

import com.anathema_roguelike.main.utilities.{HasWeightedProbability, Utils}
import com.google.common.collect.HashMultimap

import scala.collection.JavaConverters._
import scala.reflect.runtime.universe._

abstract class ItemFactory[T <: Item] extends HasWeightedProbability {

  private val factories: HashMultimap[Class[_ <: ItemType[_ <: T]], ItemFactory[_ <: T]] = {

    HashMultimap.create[Class[_ <: ItemType[_ <: T]], ItemFactory[_ <: T]]
  }

  protected def addFactory[F <: ItemFactory[_ <: T]](factory: F): Unit = {
    factories.put(factory.getSupportedType, factory)

    factory.getSubFactories.foreach(t => addFactory(t))
  }

  def getSubFactories: Iterable[ItemFactory[_ <: T]] = factories.values.asScala

  private def getFactories[P <: ItemType[_ <: T], F <: ItemFactory[T]](itemType: Class[_ <: P]): Set[F] = {
    factories.get(itemType).asInstanceOf[Set[F]]
  }

  def generate[S <: T : TypeTag](cls: Class[_ <: ItemType[S]]): S = {

    if (cls == getSupportedType) {
      generate[S](cls)
    } else if (getFactories(cls).isEmpty) {
      throw new RuntimeException("This Factory does not support that type")
    } else {
      val f: ItemFactory[T] = Utils.getWeightedRandomSample(getFactories(cls))

      if (f.getSupportedType == cls) {
        f.generate[S](cls)
      } else {
        f.generate[S](cls)
      }
    }
  }

  def generate: T = Utils.getWeightedRandomSample(factories.values.asScala).generate

  def getSupportedType: Class[_ <: ItemType[_ <: T]]
}
