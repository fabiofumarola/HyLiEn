package eu.unicredit.web

import scala.annotation.tailrec
import scala.collection.mutable

/**
 * Created by fabiofumarola on 24/05/16.
 */
object Models {

  case class Location(x: Int, y: Int)

  val noLocation = Location(-1, -1)

  case class Size(width: Int, height: Int)

  val noSize = Size(-1, -1)

  val noCssSelector = ""

  case class DomNode(
    id: Int,
    tagName: String,
    cssClasses: String,
    cssSelector: String,
    location: Location,
    size: Size,
    text: String,
    children: mutable.Buffer[DomNode] = mutable.Buffer.empty[DomNode]) {
    lazy val bfs = DomNode.bfs(this)
  }

  object DomNode {

    def bfs(n: DomNode): Seq[String] = {

      @tailrec
      def bfs0(nodes: Seq[DomNode], acc: Seq[String]): Seq[String] =
        if (nodes.isEmpty) acc
        else {
          val (head, tail) = (nodes.head, nodes.tail)
          bfs0(tail ++ head.children, acc ++ head.children.map(_.tagName))
        }

      bfs0(n.children, Seq(n.tagName) ++ n.children.map(_.tagName))
    }
  }

  object Orientation extends Enumeration {
    type Orientation = Value
    val horizontal, vertical, tiled = Value
  }

  import Orientation._

  case class WebList(
    parent: DomNode,
    orientation: Orientation,
    elements: Seq[DomNode])
}

