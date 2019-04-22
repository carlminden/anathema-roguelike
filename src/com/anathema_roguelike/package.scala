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

package com

import com.anathema_roguelike.main.display.VisualRepresentation
import com.anathema_roguelike.main.utilities.position.{HasPosition, Point}

import scala.reflect._
import scala.reflect.api.{TypeCreator, Universe}
import scala.reflect.runtime.currentMirror
import scala.reflect.runtime.universe._


package object anathema_roguelike {

  def createInstance[T](tt: TypeTag[T], args: Any*): T = {

    currentMirror.reflectClass(tt.tpe.typeSymbol.asClass).reflectConstructor(
      tt.tpe.members.filter(m =>{
        m.isMethod && m.asMethod.isConstructor && m.asMethod.isPrimaryConstructor
      }

      ).head.asMethod
    )(args: _*).asInstanceOf[T]
  }

  implicit def typeTagToClass[T: TypeTag]: Class[T] = {
    typeTag[T].mirror.runtimeClass( typeTag[T].tpe ).asInstanceOf[Class[T]]
  }

  implicit def toOption[T](value: T): Option[T] = Some(value)

  implicit def toVisualRepresentation(value: Char): VisualRepresentation = new VisualRepresentation(value)

  implicit class CollectionUtils[C : TypeTag](collection: Iterable[C]) {
    def filterByType[T : TypeTag]: Iterable[T] = {

      if((typeOf[C] =:= typeOf[T]) && typeOf[C].takesTypeArgs) {
        throw new RuntimeException(s"Cannot filter by erased generic type ${typeOf[T]}")
      }

      collection.collect { case t: T => t }
    }
  }

  implicit def classToTypeTag[A](c: Class[A]): TypeTag[A] = {
    val mirror = runtimeMirror(c.getClassLoader)  // obtain runtime mirror
    val sym = mirror.staticClass(c.getName)  // obtain class symbol for `c`
    val tpe = sym.selfType  // obtain type object for `c`
    // create a type tag which contains above type object
    TypeTag(mirror, new TypeCreator {
      def apply[U <: Universe with Singleton](m: api.Mirror[U]): U#Type =
        if (m eq mirror) tpe.asInstanceOf[U # Type]
        else throw new IllegalArgumentException(s"Type tag defined in $mirror cannot be migrated to other mirrors.")
    })
  }

  implicit def toPoint(hasPosition: HasPosition): Point = hasPosition.getPosition

}
