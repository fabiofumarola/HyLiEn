package eu.unicredit.web.hylien

import eu.unicredit.web.Models._
import scala.language.postfixOps
import scala.util.Random


private[this] object VisualListFinder {
  /**
   *
   * @param domNode
   * @param minsim
   * @param maxRecordTags
   * @return return a tuple with lists and non aligned nodes
   */
  def find(pageUrl: String, domNode: DomNode, minsim: Float, maxRecordTags: Int): (Seq[WebList], Seq[DomNode]) = {

    domNode.tagName match {
      //GOT from MDR
      //TODO add other stylish tags
      case "p" => (Seq.empty[WebList], domNode.children)

      case "span" => (Seq.empty[WebList], domNode.children)

      case _ =>

        val verticalAligned: Map[Int, Seq[DomNode]] = findVerticallyAligned(domNode, maxRecordTags)
        val horizontalAligned: Map[Int, Seq[DomNode]] = findHorizontallyAligned(domNode, maxRecordTags)
        var notAligned = findNotAligned(domNode, verticalAligned, horizontalAligned)

        val verticalList = verticalAligned.map {
          case (pos, list) =>
            val (similar, nonSimilar) = structuralFilter(list, minsim)
            notAligned = notAligned ++ nonSimilar
            pos -> WebList(pageUrl, domNode, Orientation.vertical, domNode.location, domNode.size, similar)
        }.filter(_._2.elements.size > 1).values

        val horizontalList = horizontalAligned.map {
          case (pos, list) =>
            val (similar, nonSimilar) = structuralFilter(list, minsim)
            notAligned = notAligned ++ nonSimilar
            pos -> WebList(pageUrl, domNode, Orientation.horizontal, domNode.location, domNode.size, similar)
        }.filter(_._2.elements.size > 1).values

        val webLists = verticalList ++ horizontalList
        //be sure to not include list elements inside notAligned
        notAligned = notAligned &~ webLists.flatMap(_.elements).toSet

        (webLists.toSeq, notAligned.toSeq)
    }

  }

  /**
   *
   * @param domNode
   * @param maxRecordTags
   * @param mapper
   * @return meta function to get all the aligned elements, fold all the aligned element in a Map[Int, Seq[DomNode] ]
   */
  private def aligned(domNode: DomNode, maxRecordTags: Int, mapper: DomNode => (Int, DomNode)): Map[Int, Seq[DomNode]] =
    domNode.children
      .filter(_.bfs.size <= maxRecordTags)
      .map(mapper)
      .foldLeft(Map.empty[Int, Seq[DomNode]]) { (map, posNode) =>
        val (pos, node) = posNode
        map + (pos -> (map.getOrElse(pos, Seq.empty) :+ node))
      }.filter(_._2.size > 1)

  private def findVerticallyAligned(domNode: DomNode, maxRecordTags: Int) =
    aligned(domNode, maxRecordTags, n => n.location.x -> n)

  private def findHorizontallyAligned(domNode: DomNode, maxRecordTags: Int) =
    aligned(domNode, maxRecordTags, n => n.location.y -> n)

  private def findNotAligned(domNode: DomNode, vertical: Map[Int, Seq[DomNode]], horizontal: Map[Int, Seq[DomNode]]) = {
    val aligned = (vertical.values ++ horizontal.values).flatten.toSet
    domNode.children.toSet.diff(aligned)
  }

  /**
   *
   * @param seq
   * @param minsim
   * @return (Similars, NonSimilars) a seq of structurally similar DomNode and a seq of non structurally similar DomNodes
   */
  private def structuralFilter(seq: Seq[DomNode], minsim: Float): (Seq[DomNode], Seq[DomNode]) = {
    var nonSimilar = List.empty[DomNode]

    val similar = Random.shuffle(seq) match {
      //take the head and for the tail filter all the elements similar to the head
      case head :: tail =>
        head :: tail.filter { n =>
          val dist = Distances.normalizedEditDistance(head.bfs, n.bfs)
          if (dist > minsim) nonSimilar = n :: nonSimilar
          dist <= minsim
        }
    }

    if (similar.size > 2) (similar, nonSimilar)
    else (Seq.empty, similar ++ nonSimilar)
  }

}
