package eu.unicredit.web

import eu.unicredit.web.Models.{DomNode, WebList}
import eu.unicredit.web.hylien.VisualHyLiEn

/**
  * Created by fabiofumarola on 29/05/16.
  */
object HyLiEnTest extends App {

  val hylien = new VisualHyLiEn()
  val result = hylien.extract("http://www.cs.illinois.edu/directory/faculty")
  //("http://www.cs.ox.ac.uk/")

  //("http://www.cs.illinois.edu") //("http://www.harvard.edu/") //("http://cs.stanford.edu/")

  println(s"Got ${result.size} lists")
  result.foreach { l =>
    val r = toString(l)
    println(r)
  }

  hylien.close()


  def toString(l: WebList): String = {
    val buf = new StringBuilder
    buf ++= s"Printing ${l.orientation} of by ${l.elements.size} elements \n"
    buf ++= s"parent dom tag = ${l.parent.tagName} \n"
    buf ++= s"location = ${l.location} \n"
    buf ++= s"location = ${l.size} \n"
    l.elements.foreach { n =>
      buf ++= s"\t tag = ${n.tagName} \n"
      buf ++= s"\t text = || ${n.text.replace("\n", " ")} || \n"
      //buf ++= s"\t html = || ${n.html} || \n"
      buf ++= s"\t bfs = ${n.bfs}\n"
      buf ++= s"\t urls = ${n.urls}\n"
      buf ++= s"\t urls absolutes = ${DomNode.getUrls(n.html, l.pageUrl)} \n"
      buf ++= s"\t node class attribute = ${n.cssClasses} \n"
      buf ++= s"\t node MapCssProps = ${n.cssProperties} \n"
      buf ++= s"\t BFS nodes Styles = ${n.styles} \n"
      buf ++= "----------------------- \n"
    }
    buf.toString()
  }


}
