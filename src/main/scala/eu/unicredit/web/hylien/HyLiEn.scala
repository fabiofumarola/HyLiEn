package eu.unicredit.web.hylien

object Capabilities extends Enumeration {
  type Capabilities = Value
  val Visual, JSoup = Value
}

import com.typesafe.scalalogging.Logger
import eu.unicredit.web.hylien.Capabilities._
import eu.unicredit.web.{JSoupExtractor, VisualWebExtractor}
import org.slf4j.LoggerFactory

/**
  * Created by fabiofumarola on 25/05/16.
  */
class HyLiEn(capabilities: Capabilities) {
  val logger = Logger(LoggerFactory.getLogger("HyLiEn"))

  private val webExtractor = capabilities match {
    case Visual => new VisualWebExtractor()
    case JSoup => new JSoupExtractor()
  }

  def extract(url: String, tagSimFactor: Float = 0.4F): Unit = {
    val root = webExtractor.parse(url)
    logger.debug(s"parsed ${url}")

    val listfinder = new VisualInnerListfinder(root, tagSimFactor)

  }

}
