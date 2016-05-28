package eu.unicredit.web.hylien


import com.typesafe.scalalogging.Logger
import eu.unicredit.web.{TagTreeBuilder, VisualTagTreeBuilder}
import org.slf4j.LoggerFactory

/**
  * Created by fabiofumarola on 25/05/16.
  */
class VisualHyLiEn() {
  val logger = Logger(LoggerFactory.getLogger("HyLiEn"))

  private val webExtractor = new VisualTagTreeBuilder()

  def extract(url: String, tagSimFactor: Float = 0.4F, maxRecordTags: Int = 30): Unit = {
    val root = webExtractor.parse(url)
    logger.debug(s"parsed ${url}, start extracting lists")

    //    val listfinder = new VisualInnerListfinder(root, tagSimFactor)

  }

}
