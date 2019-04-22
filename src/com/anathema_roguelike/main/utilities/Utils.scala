/*******************************************************************************
 * Copyright (c) 2019. Carl Minden
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

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
import scala.util.Random

object Utils {

  private val subtypeCache = mutable.Map[TypeTag[_], List[_]]()
  private val annotationCache = mutable.Map[Class[_ <: Annotation], Set[Class[_]]]()

  private val names = new Properties
  private val descriptions = new Properties
  private val colors = new Properties

  names.load(getClass.getResourceAsStream("/names.properties"))
  descriptions.load(getClass.getResourceAsStream("/descriptions.properties"))
  colors.load(getClass.getResourceAsStream("/colors.properties"))

  private val rand = new Random()

  def getRandom = rand

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

  def getKeysByValue[T, E](map: Map[T, E], value: E): Iterable[T] = {
    map.collect {
      case (k, v) if(Objects.equals(v, value)) => k
    }
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

  def getObjectField[T](obj: String, field: String): T = {

    val mirror = universe.runtimeMirror(getClass.getClassLoader)

    val moduleSymbol = mirror.staticModule(obj)
    val moduleMirror = mirror.reflectModule(moduleSymbol)
    val instanceMirror = mirror.reflect(moduleMirror.instance)

    val fields = moduleSymbol.typeSignature.decls.filter(f => f.asTerm.isVal && f.name == field)

    instanceMirror.reflectField(fields.head.asTerm).get.asInstanceOf[T]
  }

  def getColor(obj: Any): SColor = {
    val color = getProperty(colors, classify(obj), "WHITE")

    getObjectField[SColor]("com.anathema_roguelike.main.display.Color", color)
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
