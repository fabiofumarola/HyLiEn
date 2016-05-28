package eu.unicredit.web

import com.rockymadden.stringmetric.similarity._
import eu.unicredit.web.hylien.Distances

/**
  * Created by fabiofumarola on 28/05/16.
  */
object DistancesTest extends App {

  println(Distances.normalizedEditDistance(List(1,2),List(1,2)))
  println(Distances.normalizedEditDistance(List(1,2),List(1,2,1)))

  println(Distances.diceSorensenMetric(List("strong","div2","div2","div3"), List("strong", "div1")))

  println(JaroWinklerMetric.compare("dwayne", "duane"))
}
