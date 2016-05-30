package eu.unicredit.web

import eu.unicredit.web.Models.WebList
import eu.unicredit.web.hylien.VisualHyLiEn

/**
  * Created by fabiofumarola on 29/05/16.
  */
object HyLiEnTest extends App {

  val hylien = new VisualHyLiEn()
  val result = hylien.extract("http://www.harvard.edu/")

  result.foreach { l =>
    val r = toString(l)
    println(r)
  }

  hylien.close()


  def toString(l: WebList): String = {
    val buf = new StringBuilder
    buf ++= s"Printing ${l.orientation} of by ${l.elements.size} elements \n"
    buf ++= s"parent dom tag = ${l.parent.tagName} \n"
    l.elements.foreach { n =>
      buf ++= s"\t tag = ${n.tagName} \n"
      buf ++= s"\t text = || ${n.text.replace("\n", " ")} || \n"
      buf ++= s"\t bfs = ${n.bfs}"
      buf ++= "----------------------- \n"
    }
    buf.toString()
  }
}
