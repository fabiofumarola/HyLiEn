package eu.unicredit.web.hylien

import eu.unicredit.web.Models.DomNode

/**
  * Created by fabiofumarola on 03/06/16.
  */
private[this] object LookAhead {
  /**
    * This method performs look ahead on non aligned elements. JUST for TEST
    *
    * @param domNode
    * @param maxRecordTags
    * @param mapper
    * @return
    */
  def aligned(domNode: DomNode, maxRecordTags: Int, mapper: DomNode => (Int, DomNode)): Map[Int, Seq[DomNode]] = {
    val candidates = domNode.children
      .filter(_.bfs.size <= maxRecordTags)
      .map(mapper)
      .foldLeft(Map.empty[Int, Seq[DomNode]]) { (map, posNode) =>
        val (pos, node) = posNode
        map + (pos -> (map.getOrElse(pos, Seq.empty) :+ node))
      }

    val alignedMap = candidates.filter(_._2.size > 1)
    val lookAheads = candidates.filter(_._2.size == 1)

    lookAheads.values.flatten
      .map(mapper)
      .foldLeft(alignedMap) { (map, posNode) =>
        val (pos, node) = posNode
        map + (pos -> (map.getOrElse(pos, Seq.empty) :+ node))
      }.filter(_._2.size > 1)
  }
}
