package eu.unicredit.web

/**
 * Created by fabiofumarola on 25/05/16.
 */
object VisualWebExtractorTest extends App {

  //  val url = "https://ec.europa.eu/research/participants/portal/desktop/en/opportunities/h2020/#c,calls=level3/t/EU.1./0/1/1/default-group&level4/t/EU.1.1./0/1/1/default-group&level4/t/EU.1.2./0/1/1/default-group&level4/t/EU.1.3./0/1/1/default-group&level4/t/EU.1.4./0/1/1/default-group&level3/t/EU.2./0/1/1/default-group&level4/t/EU.2.1./0/1/1/default-group&level5/t/EU.2.1.1./0/1/1/default-group&level5/t/EU.2.1.2./0/1/1/default-group&level5/t/EU.2.1.3./0/1/1/default-group&level5/t/EU.2.1.4./0/1/1/default-group&level5/t/EU.2.1.5./0/1/1/default-group&level5/t/EU.2.1.6./0/1/1/default-group&level4/t/EU.2.2./0/1/1/default-group&level4/t/EU.2.3./0/1/1/default-group&level3/t/EU.3./0/1/1/default-group&level4/t/EU.3.1./0/1/1/default-group&level4/t/EU.3.2./0/1/1/default-group&level4/t/EU.3.3./0/1/1/default-group&level4/t/EU.3.4./0/1/1/default-group&level4/t/EU.3.5./0/1/1/default-group&level4/t/EU.3.6./0/1/1/default-group&level4/t/EU.3.7./0/1/1/default-group&level3/t/EU.4./0/1/1/default-group&level3/t/EU.5./0/1/1/default-group&level3/t/EU.7./0/1/1/default-group&level2/t/Euratom/0/1/1/default-group&hasForthcomingTopics/t/true/1/1/0/default-group&hasOpenTopics/t/true/1/1/0/default-group&allClosedTopics/t/true/0/1/0/default-group&+PublicationDateLong/asc"
  val url = "https://www.stanford.edu/"

  val time2 = System.currentTimeMillis()
  val parser1 = new VisualWebExtractor(true, true)
  val root = parser1.parse(url)
  println(s"page parsed into ${System.currentTimeMillis() - time2}")
  parser1.close()

  //  var time1 = System.currentTimeMillis()
  //  val visual1 = new VisualWebExtractor(true, true)
  //  (1 to 10).foreach(_ => visual1.parse(url))
  //  println(s"page parsed into ${(System.currentTimeMillis() - time1) / 10}")

  val time3 = System.currentTimeMillis()
  val parser2 = new VisualWebExtractor(true, true)
  parser2.parse(url)
  println(s"page parsed into ${System.currentTimeMillis() - time3}")
  parser2.close()

  println(root)

}

object JSoupExtractorTest extends App {
  //    val url = "https://ec.europa.eu/research/participants/portal/desktop/en/opportunities/h2020/#c,calls=level3/t/EU.1./0/1/1/default-group&level4/t/EU.1.1./0/1/1/default-group&level4/t/EU.1.2./0/1/1/default-group&level4/t/EU.1.3./0/1/1/default-group&level4/t/EU.1.4./0/1/1/default-group&level3/t/EU.2./0/1/1/default-group&level4/t/EU.2.1./0/1/1/default-group&level5/t/EU.2.1.1./0/1/1/default-group&level5/t/EU.2.1.2./0/1/1/default-group&level5/t/EU.2.1.3./0/1/1/default-group&level5/t/EU.2.1.4./0/1/1/default-group&level5/t/EU.2.1.5./0/1/1/default-group&level5/t/EU.2.1.6./0/1/1/default-group&level4/t/EU.2.2./0/1/1/default-group&level4/t/EU.2.3./0/1/1/default-group&level3/t/EU.3./0/1/1/default-group&level4/t/EU.3.1./0/1/1/default-group&level4/t/EU.3.2./0/1/1/default-group&level4/t/EU.3.3./0/1/1/default-group&level4/t/EU.3.4./0/1/1/default-group&level4/t/EU.3.5./0/1/1/default-group&level4/t/EU.3.6./0/1/1/default-group&level4/t/EU.3.7./0/1/1/default-group&level3/t/EU.4./0/1/1/default-group&level3/t/EU.5./0/1/1/default-group&level3/t/EU.7./0/1/1/default-group&level2/t/Euratom/0/1/1/default-group&hasForthcomingTopics/t/true/1/1/0/default-group&hasOpenTopics/t/true/1/1/0/default-group&allClosedTopics/t/true/0/1/0/default-group&+PublicationDateLong/asc"
  val url = "https://www.stanford.edu/"

  var time1 = System.currentTimeMillis()
  val visual1 = new JSoupExtractor()
  (1 to 10).foreach(_ => visual1.parse(url))
  println(s"page parsed into ${(System.currentTimeMillis() - time1) / 10}")

  val time2 = System.currentTimeMillis()
  val root = new JSoupExtractor().parse(url)
  println(s"page parsed into ${System.currentTimeMillis() - time2}")

  val time3 = System.currentTimeMillis()
  new JSoupExtractor().parse(url)
  println(s"page parsed into ${System.currentTimeMillis() - time3}")

  println(root)
}