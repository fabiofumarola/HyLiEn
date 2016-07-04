package eu.unicredit.web.hylien

import com.typesafe.scalalogging.LazyLogging
import eu.unicredit.web.Models.{Location, Orientation, WebList}

private[this] object WebListFilters extends LazyLogging {

  def filterDuplicates: Seq[WebList] => Seq[WebList] =
    filterOnCondition((li, lj) => li.orientation != lj.orientation && (li.location == lj.location && li.size == lj.size)) _

  //example of partially applied function

  def filterOnCondition(condition: (WebList, WebList) => Boolean)(lists: Seq[WebList]): Seq[WebList] = {
    var removed = Set.empty[WebList]

    for (i <- lists.indices) {
      val li = lists(i)
      if (!removed.contains(li)) {
        for (j <- i + 1 until lists.size) {
          val lj = lists(j)
          if (!removed.contains(lj)) {
            if (condition(li, lj))
              removed += lj
          }
        }
      }
    }

    logger.debug("removed {} list", removed.size)
    (lists.toSet &~ removed).toSeq
  }

  def filterEmptyText(list: Seq[WebList]): Seq[WebList] =
    list.filter { l =>
      //test that the it does have text
      l.elements.map(_.text.replace(" ", ""))
        .reduce(_ + _).nonEmpty
    }

  def tiledListsFinder(minsim: Float)(lists: Seq[WebList]): Seq[WebList] = {

    var toRemove = Set.empty[WebList]
    var tiledLists = Seq.empty[WebList]

    for (i <- lists.indices) {
      val li = lists(i)
      if (!toRemove.contains(li)) {

        var tiledList = WebList(
          pageUrl = li.pageUrl,
          parent = li.parent,
          orientation = Orientation.tiled,
          location = li.location,
          size = li.size,
          elements = li.elements,
          from = Seq(li))

        for (j <- i + 1 until lists.size) {
          val lj = lists(j)
          if (!toRemove.contains(lj)) {

            //if they have the same orientation and are aligned for x or y
            if (li.orientation == lj.orientation &&
              (li.location.x == lj.location.x || li.location.y == lj.location.y)) {
              if (Distances.normalizedEditDistance(li.bfs, lj.bfs) <= minsim) {
                tiledList = tiledList.copy(
                  size = li.size + lj.size,
                  location = li.location + lj.location,
                  elements = tiledList.elements ++ lj.elements,
                  from = tiledList.from ++ Seq(lj))
              }
            }
          }
        }
        if (tiledList.from.length > 1) {
          toRemove ++= tiledList.from
          tiledLists +:= tiledList
        }

      }
    }

    (lists.toSet &~ toRemove).toSeq ++ tiledLists
  }

}

