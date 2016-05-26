package eu.unicredit.web.hylien

import eu.unicredit.web.Models.DomNode

/**
  * Created by fabiofumarola on 26/05/16.
  */
private[this] class VisualInnerListfinder(domNode: DomNode, tagSimFactor: Float) {

  
}

private[this] object VisualInnerListfinder {

  def apply(domNode: DomNode, tagSimFactor: Float): VisualInnerListfinder =
    new VisualInnerListfinder(domNode, tagSimFactor)

  def verticallyAligned() = ???

  def horizontallyAligned() = ???

  def structurallyAligned(threshold: Float) = ???
}



