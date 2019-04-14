package com

import com.anathema_roguelike.main.display.VisualRepresentation

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

}
