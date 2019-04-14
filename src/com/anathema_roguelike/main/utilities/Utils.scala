package com.anathema_roguelike
package main.utilities


import java.util.{Objects, Properties}

import com.anathema_roguelike.main.display.Color
import gigadot.rebound.Rebound
import org.apache.commons.math3.distribution.EnumeratedDistribution
import org.apache.commons.math3.util.Pair
import squidpony.squidgrid.gui.gdx.SColor

import collection.JavaConverters._
import scala.collection.mutable
import scala.reflect._
import scala.reflect.runtime._
import scala.reflect.runtime.universe._
import Numeric.Implicits._

object Utils {

  private val subtypeCache = mutable.Map[TypeTag[_], List[_]]()
  private val annotationCache = mutable.Map[Class[_ <: Annotation], Set[Class[_]]]()

  private val names = new Properties
  private val descriptions = new Properties
  private val colors = new Properties

  names.load(getClass.getResourceAsStream("/names.properties"))
  descriptions.load(getClass.getResourceAsStream("/descriptions.properties"))
  colors.load(getClass.getResourceAsStream("/colors.properties"))

  def getWeightedRandomSample[T <: HasWeightedProbability](iterable: Iterable[T]): T = {

    val list = iterable.map(i => {
      //actually do want java lang Double for compatibility with the distribution library
      new Pair[T, java.lang.Double](i, i.getWeightedProbability)
    })

    new EnumeratedDistribution[T](list.toList.asJava).sample
  }

  def clamp[T : Numeric](n: T, l: T, h: T): T = {
    val num = implicitly[Numeric[T]]

    if (num.gt(n, h)) h else if (num.lt(n, l)) l else n
  }

  def getKeysByValue[T, E](map: Map[T, E], value: E): Set[T] = {
    map.filter {
      case (k, v) => Objects.equals(v, value)
    }.keys.toSet
  }

  def getSuperclass(subclass: Any): Class[_] = {
    subclass match {
      case c: Class[_] => c.getSuperclass
      case _ => subclass.getClass.getSuperclass
    }
  }

  def getSubclasses[T : TypeTag](predicate: Class[_ <: T] => Boolean = (_: Class[_ <: T]) => true,
      includeAbstract: Boolean = false, includeSuperclass: Boolean = false): Iterable[Class[_ <: T]] = {

    if (subtypeCache.contains(typeTag[T])) {
      subtypeCache.getOrElse(typeTag[T], List()).asInstanceOf[List[Class[_ <: T]]]
    }
    else {
      val rebound = new Rebound("com.anathema_roguelike", includeAbstract)
      val subTypes = rebound.getSubClassesOf(typeTagToClass[T])

      if (!includeSuperclass) {
        subTypes.remove(typeTagToClass[T])
      }

      val sorted = subTypes.asScala.toList.sortBy(s => s.getName)
      subtypeCache.put(typeTag[T], sorted)

      sorted
    }.filter(predicate)
  }

  def getProperty(properties: Properties, obj: Any, defaultValue: String): String = {

    obj match {
      case s: String => s
      case c => {
        var cls = c.getClass

        while (cls.isAnonymousClass) cls = cls.getEnclosingClass

        Option(properties.getProperty(cls.getSimpleName)).getOrElse(defaultValue)
      }
    }
  }

  def getDescription(obj: Any): String = {

    obj match {
      case _: String => ""
      case c if c == classOf[String] => ""
      case _ => getProperty(descriptions, obj, getName(obj) + " MISSING DESCRIPTION")
    }
  }

  def getName(obj: Any): String = {

    obj match {
      case c: Class[_] => getProperty(names, obj, splitCamelCase(classify(c).getSimpleName))
      case o => {
        if (obj.getClass.getMethod("toString").getDeclaringClass != classOf[Any]) {
          obj.toString
        } else {
          getProperty(names, obj, splitCamelCase(classify(o).getSimpleName))
        }
      }
    }
  }

  def getColor(obj: Any): SColor = {
    val color = getProperty(colors, classify(obj), "WHITE")

    //TODO this seems odd, not 100% sure why there is a get(null)
    classOf[Color].getField(color).get(null).asInstanceOf[SColor]
  }

  private def classify(obj: Any) = {

    var ret = obj match {
      case c: Class[_] => c
      case o => o.getClass
    }

    while(ret.isAnonymousClass) ret = ret.getEnclosingClass

    ret
  }

  private def splitCamelCase(s: String) = {
    s.replaceAll(String.format(
      "%s|%s|%s",
      "(?<=[A-Z])(?=[A-Z][a-z])",
      "(?<=[^A-Z])(?=[A-Z])",
      "(?<=[A-Za-z])(?=[^A-Za-z])"), " ")
  }

}
