package eu.unicredit.web

import scala.collection.mutable

/**
 * Created by fabiofumarola on 24/05/16.
 */
object Models {

  case class Location(x: Int, y: Int)

  case class Size(width: Int, height: Int)

  case class VisualDomNode(
    id: Int,
    tagName: String,
    cssClasses: String,
    location: Location,
    size: Size,
    text: String,
    children: mutable.Buffer[VisualDomNode] = mutable.Buffer.empty[VisualDomNode])

  case class DomNode(
    id: Int,
    tagName: String,
    cssClasses: String,
    cssSelector: String,
    text: String,
    children: mutable.Buffer[DomNode] = mutable.Buffer.empty[DomNode])

}