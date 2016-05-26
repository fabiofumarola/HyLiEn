package eu.unicredit.web
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
    parent: Option[DomNode],
    id: Int,
    tagName: String,
    cssClasses: String,
    cssSelector: String,
    location: Location,
    size: Size,
    text: String,
    children: mutable.Buffer[DomNode] = mutable.Buffer.empty[DomNode])

}
