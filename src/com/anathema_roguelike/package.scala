package com

import scala.reflect._
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
}
