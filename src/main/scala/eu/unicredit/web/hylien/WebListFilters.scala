package eu.unicredit.web.hylien

import com.typesafe.scalalogging.LazyLogging
import eu.unicredit.web.Models.WebList


private[this] object WebListFilters extends LazyLogging {


  def filterDuplicates: Seq[WebList] => Seq[WebList] =
    filterOnCondition((li, lj) => li.orientation != lj.orientation && (li.location == lj.location && li.size == lj.size)) _
    //example of partially applied function

  def filterOnCondition(condition: (WebList, WebList) => Boolean)(list: Seq[WebList]): Seq[WebList] = {
    var removed = Set.empty[WebList]

    for (i <- list.indices) {
      val li = list(i)
      if (!removed.contains(li)) {
        for (j <- i + 1 until list.size) {
          val lj = list(j)
          if (!removed.contains(lj)) {
            if (condition(li, lj))
              removed += lj
          }
        }
      }
    }

    logger.debug("removed {} list", removed.size)
    (list.toSet &~ removed).toSeq
  }

  def filterEmptyText(list: Seq[WebList]): Seq[WebList] =
    list.filter { l =>
      //test that the it does have text
      l.elements.map(_.text)
        .reduce(_ + _).nonEmpty
    }
}

