package com.anathema_roguelike.main
import com.anathema_roguelike.main.utilities.AutoClassToInstanceMap
import com.anathema_roguelike.stats.Stat

import scala.reflect.runtime._
import scala.reflect.runtime.universe._

object Test {

  def main(args: Array[String]): Unit = {

    println(Option(null).getOrElse("stuff"))
  }
}