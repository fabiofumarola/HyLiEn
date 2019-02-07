package eu.unicredit.web

import eu.unicredit.web.Models._

import scala.collection.mutable

/**
  * Created by fabiofumarola on 25/05/16.
  */
object VisualTagTreeBuilderTest extends App {

  val url = "http://www.bsvillage.com/Piscine-Fuori-Terra/"

  val time2 = System.currentTimeMillis()
  val parser1 = new VisualTagTreeBuilder(true, true)
  val root = parser1.parse(url)
  println(s"page parsed into ${System.currentTimeMillis() - time2}")
  parser1.close()

  println(root)

}

object TagTreeBuilderTest extends App {
  val url = "http://www.cs.illinois.edu/directory/faculty"

  val time3 = System.currentTimeMillis()
  val root = new TagTreeBuilder().parse(url)
  println(s"page parsed into ${System.currentTimeMillis() - time3}")

  println(root)
}

object DomNodeTest extends App {

  val root = DomNode(
    id = 1,
    parentId = -1,
    tagName = "a",
    cssClass = "strong",
    cssSelector = "",
    cssProperties = Map.empty,
    location = noLocation,
    size = noSize,
    text = "",
    html = "<div id=\"my-cs\">\n      <a href=\"http://my.cs.illinois.edu\">MY.CS</a>\n     </div> "

  )

  val child1 = root.copy(id = 1, tagName = "b", children = mutable.Buffer.empty[DomNode])
  val child2 = root.copy(id = 2, tagName = "c", children = mutable.Buffer.empty[DomNode])
  val child3 = root.copy(id = 3, tagName = "d", children = mutable.Buffer.empty[DomNode])
  val child4 = root.copy(id = 4, tagName = "e", children = mutable.Buffer.empty[DomNode])

  root.children.append(child4,child1, child2)
  child2.children.append(child3)

  val bfs = DomNode.bfs(root)
  println(bfs)

}