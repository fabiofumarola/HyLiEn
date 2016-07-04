package hylien

import eu.unicredit.web.Models.{DomNode, Location, Size}
import eu.unicredit.web.hylien.Distances
import org.specs2.mutable.Specification
import org.specs2.specification.Scope

import scala.collection.mutable


/**
  * Created by fabiana on 7/4/16.
  */
class TreeEditDistanceSpecs extends Specification {
  class Context extends Scope {

    val node_tagA = DomNode(id =1,
      parentId=0,
      tagName = "a",
      cssClass ="",
      cssProperties = Map(),
      cssSelector = "",
      location = Location(100, 100),
      size = Size(100,100),
      text= "node A",
      html = "")

    val node_tagB = DomNode(id = 2,
      parentId=0,
      tagName = "b",
      cssClass ="",
      cssProperties = Map(),
      cssSelector = "",
      location = Location(100, 100),
      size = Size(100,100),
      text= "node B",
      html = "")

    val node_tagC = DomNode(id = 3,
      parentId=0,
      tagName = "c",
      cssClass ="",
      cssProperties = Map(),
      cssSelector = "",
      location = Location(100, 100),
      size = Size(100,100),
      text= "node C",
      html = "")

    val node_tagD = DomNode(id = 2,
      parentId=0,
      tagName = "d",
      cssClass ="",
      cssProperties = Map(),
      cssSelector = "",
      location = Location(100, 100),
      size = Size(100,100),
      text= "node D",
      html = "")

    val node_tagE = DomNode(id = 2,
      parentId=0,
      tagName = "e",
      cssClass ="",
      cssProperties = Map(),
      cssSelector = "",
      location = Location(100, 100),
      size = Size(100,100),
      text= "node E",
      html = "")

    val node_tagF = DomNode(id = 2,
      parentId=0,
      tagName = "f",
      cssClass ="",
      cssProperties = Map(),
      cssSelector = "",
      location = Location(100, 100),
      size = Size(100,100),
      text= "node F",
      html = "")

    val node_tagG = DomNode(id = 2,
      parentId=0,
      tagName = "g",
      cssClass ="",
      cssProperties = Map(),
      cssSelector = "",
      location = Location(100, 100),
      size = Size(100,100),
      text= "node G",
      html = "")

    val node_tagH = DomNode(id = 2,
      parentId=0,
      tagName = "h",
      cssClass ="",
      cssProperties = Map(),
      cssSelector = "",
      location = Location(100, 100),
      size = Size(100,100),
      text= "node H",
      html = "")

    val node_tagI = DomNode(id = 2,
      parentId=0,
      tagName = "i",
      cssClass ="",
      cssProperties = Map(),
      cssSelector = "",
      location = Location(100, 100),
      size = Size(100,100),
      text= "node I",
      html = "")

    val T1 = DomNode(id = 2,
      parentId=0,
      tagName = "b",
      cssClass ="",
      cssProperties = Map(),
      cssSelector = "",
      location = Location(100, 100),
      size = Size(100,100),
      text= "T1",
      html = "T1",
      children = mutable.Buffer(node_tagC, node_tagD)
    )
    val T2 = DomNode(id = 2,
      parentId=0,
      tagName = "c",
      cssClass ="",
      cssProperties = Map(),
      cssSelector = "",
      location = Location(100, 100),
      size = Size(100,100),
      text= "T2",
      html = "",
      children = mutable.Buffer(node_tagF)
    )

    val T3 = DomNode(id = 2,
      parentId=0,
      tagName = "d",
      cssClass ="",
      cssProperties = Map(),
      cssSelector = "",
      location = Location(100, 100),
      size = Size(100,100),
      text= "T3",
      html = "",
      children = mutable.Buffer(node_tagE)
    )

    val T4 = DomNode(id = 2,
      parentId=0,
      tagName = "g",
      cssClass ="",
      cssProperties = Map(),
      cssSelector = "",
      location = Location(100, 100),
      size = Size(100,100),
      text= "T4",
      html = "",
      children = mutable.Buffer(node_tagH, node_tagF)
    )

    val T5 = DomNode(id = 2,
      parentId=0,
      tagName = "c",
      cssClass ="",
      cssProperties = Map(),
      cssSelector = "",
      location = Location(100, 100),
      size = Size(100,100),
      text= "T5",
      html = "",
      children = mutable.Buffer(T4, node_tagF)
    )

    val T6 = DomNode(id = 2,
      parentId=0,
      tagName = "a",
      cssClass ="",
      cssProperties = Map(),
      cssSelector = "",
      location = Location(100, 100),
      size = Size(100,100),
      text= "T6",
      html = "",
      children = mutable.Buffer(T1, T2, T3, T5)
    )

    val T7 = DomNode(id = 2,
      parentId=0,
      tagName = "b",
      cssClass ="",
      cssProperties = Map(),
      cssSelector = "",
      location = Location(100, 100),
      size = Size(100,100),
      text= "T7",
      html = "",
      children = mutable.Buffer(node_tagC, node_tagD)
    )

    val T8 = DomNode(id = 2,
      parentId=0,
      tagName = "g",
      cssClass ="",
      cssProperties = Map(),
      cssSelector = "",
      location = Location(100, 100),
      size = Size(100,100),
      text= "T8",
      html = "",
      children = mutable.Buffer( node_tagF, node_tagH, node_tagI)
    )

    val T9 = DomNode(id = 2,
      parentId=0,
      tagName = "c",
      cssClass ="",
      cssProperties = Map(),
      cssSelector = "",
      location = Location(100, 100),
      size = Size(100,100),
      text= "T9",
      html = "",
      children = mutable.Buffer(T8)
    )

    val T10 = DomNode(id = 2,
      parentId=0,
      tagName = "e",
      cssClass ="",
      cssProperties = Map(),
      cssSelector = "",
      location = Location(100, 100),
      size = Size(100,100),
      text= "T10",
      html = "",
      children = mutable.Buffer( node_tagF)
    )

    val T11 = DomNode(id = 2,
      parentId=0,
      tagName = "d",
      cssClass ="",
      cssProperties = Map(),
      cssSelector = "",
      location = Location(100, 100),
      size = Size(100,100),
      text= "T11",
      html = "",
      children = mutable.Buffer(T10)
    )

    val T12 = DomNode(id = 2,
      parentId=0,
      tagName = "a",
      cssClass ="",
      cssProperties = Map(),
      cssSelector = "",
      location = Location(100, 100),
      size = Size(100,100),
      text= "T12",
      html = "",
      children = mutable.Buffer(T7, T9, T11)
    )

  }

  "TreeEditDistance" should {
    "returns a score of 7.0 when you compare 2 trees having 7 nodes in common" in new Context{
      Distances.treeEditDistance(T6, T12) === 7D
    }
    "returns a score of 0.0 when you compare 2 trees having no common nodes" in new Context{
      Distances.treeEditDistance(node_tagA, node_tagB) === 0
    }
  }

  "NormalizedTreeEditDistance" should {
    "returns a score of 0.56 when compare 2 trees having 7 nodes in common and size 13 and 12 respectively" in new Context{
      Distances.normalizedTreeEditDistance(T6, T12) === 0.56
    }
    "returns a score of 1 when a tree is compared with itself" in new Context {
      Distances.normalizedTreeEditDistance(T6,T6) === 1
    }
  }
}
