package eu.unicredit.web

import eu.unicredit.web.Models.{ BrowserSize, DomNode, WebList }
import eu.unicredit.web.hylien.VisualHyLiEn

/**
 * Created by fabiofumarola on 29/05/16.
 */
object HyLiEnTest extends App {

  val hylien = new VisualHyLiEn(
    headless = true, quickRender = true,
    logReqs = false, browserSize = BrowserSize(1920, 1080))

  val lists = hylien.extract("http://www.immobiliare.it/44602950-Vendita-Bilocale-via-Pola-2-Milano.html")


  //("http://www.cs.illinois.edu/directory/faculty?quicktabs_faculty_tabs_new=1#quicktabs-faculty_tabs_new")

  //("http://www.cs.illinois.edu")

  //("https://it.wikipedia.org/wiki/Fiat_Chrysler_Automobiles")
 //("http://www.bsvillage.com/Piscine-Fuori-Terra/")

  //("http://www.cs.illinois.edu/directory/faculty")

  //("http://www.cs.ox.ac.uk/")

  // //("http://www.harvard.edu/") //("http://cs.stanford.edu/")

  println(s"Got ${lists.size} lists")
  lists.foreach { l =>
    val r = toString(l)
    println(r)
  }

  hylien.close()

  def toString(l: WebList): String = {
    val buf = new StringBuilder
    buf ++= s"Printing ${l.orientation} of by ${l.elements.size} elements obtained merging ${l.from.size} lists \n"
    buf ++= s"parent dom tag = ${l.parent.tagName}\n"
    buf ++= s"location = ${l.location} \n"
    buf ++= s"size = ${l.size} \n"
    buf ++= s"parent Visual Features = ${l.parent.visualFeatures} \n"
    l.elements.foreach { n =>
      buf ++= s"\t tag = ${n.tagName} \n"
      buf ++= s"\t text = || ${n.text.replace("\n", " ")} || \n"
      //buf ++= s"\t html = || ${n.html} || \n"
      buf ++= s"\t bfs = ${n.bfs}\n"
      buf ++= s"\t urls = ${n.urls}\n"
      buf ++= s"\t urls absolutes = ${DomNode.getUrls(n.html, l.pageUrl)} \n"
      buf ++= s"\t node class attribute = ${n.cssClass} \n"
//      buf ++= s"\t node MapCssProps = ${n.cssProperties} \n"
      buf ++= s"\t BFS nodes Styles = ${n.bfsCssClasses} \n"
      buf ++= s"\t Visual Features =  ${n.visualFeatures} \n"
      buf ++= "----------------------- \n"
    }

//    if (l.from.size > 1){
//      buf ++= "########################### \n"
//      l.from.foreach(p => buf ++= toString(p))
//      buf ++= "########################### \n"
//    }

    buf.toString()
  }
}
