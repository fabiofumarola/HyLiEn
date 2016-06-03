package eu.unicredit.web

import java.net.URL

import com.machinepublishers.jbrowserdriver.{ JBrowserDriver, Settings, Timezone }
import eu.unicredit.web.Models._
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.openqa.selenium.{ By, Dimension, WebElement }

import scala.collection.JavaConversions._
import scala.collection.mutable

trait WebExtractor {
  def parse(url: String): DomNode
}

/**
 * Created by fabiofumarola on 24/05/16.
 */
class VisualTagTreeBuilder(headless: Boolean = true, quickRender: Boolean = true,
  browserSize: BrowserSize = BrowserSize(1920, 1080)) extends WebExtractor {

  private val settings = Settings.builder()
    .timezone(Timezone.EUROPE_ROME)
    .headless(headless)
    .screen(new Dimension(browserSize.width, browserSize.height))
    .cache(true)
    .quickRender(quickRender)
    .ajaxWait(300)
    .build()

  private val driver = new JBrowserDriver(settings)

  /**
   *
   * @param url
   * @return the root of the parsed tree
   */
  def parse(url: String): DomNode = {
    driver.get(url)
    val body = driver.findElementByTagName("body")
    val root = toDomNode(1, body, None)
    val queue = mutable.Queue(children(body).map((root, _)): _*)

    var counter = 2
    while (queue.nonEmpty) {
      val (parent, e) = queue.dequeue()
      val node = toDomNode(counter, e, Some(parent))
      counter += 1
      parent.children.append(node)
      queue.enqueue(children(e).map((node, _)): _*)
    }
    root
  }

  private val script =
    """var s = '';
       var o = getComputedStyle(arguments[0]);
       for (var i = 0; i < o.length; i++){
         s += o[i] + '::' + o.getPropertyValue(o[i])+';';
       }
       return s.toString();
    """.stripMargin

  private def cssStyles(e: WebElement): Map[String, String] = {
    driver.executeScript(script, e).toString
      .split(";")
      .map(_.split("::"))
      .map{
        case Array(prop, value) =>
          prop -> value
      }.toMap
  }

  private def children(e: WebElement) =
    e.findElements(By.xpath("child::*")).filter(_.isDisplayed)

  private def toDomNode(id: Int, e: WebElement, parent: Option[DomNode]) = DomNode(
    id = id,
    tagName = e.getTagName,
    cssClasses = e.getAttribute("class"),
    cssProperties = cssStyles(e),
    cssSelector = noCssSelector,
    location = Location(e.getLocation.x, e.getLocation.y),
    size = Size(e.getSize.width, e.getSize.height),
    text = e.getText,
    html = e.getAttribute("outerHTML"))

  def close() = driver.close()

}

class TagTreeBuilder extends WebExtractor {

  def parse(url: String): DomNode = {
    val doc = Jsoup.parse(new URL(url), 2000)
    val body = doc.body()
    val root = toDomNode(1, body, None)
    val queue = mutable.Queue(body.children()
      .filterNot(_.cssSelector().contains("script")).map((root, _)): _*)

    var counter = 2
    while (queue.nonEmpty) {
      val (parent, e) = queue.dequeue()
      val node = toDomNode(counter, e, Some(parent))
      counter += 1
      parent.children.append(node)
      queue.enqueue(e.children().map((root, _)): _*)
    }

    root
  }

  private def toDomNode(id: Int, e: Element, parent: Option[DomNode]) = DomNode(
    id = id,
    tagName = e.tagName(),
    cssClasses = e.className(),
    cssSelector = e.cssSelector(),
    cssProperties = Map.empty, //TODO fill me
    location = noLocation,
    size = noSize,
    text = e.ownText(),
    html = e.html())
}
