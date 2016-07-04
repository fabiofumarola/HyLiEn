package eu.unicredit.web.hylien

import com.rockymadden.stringmetric.similarity._
import eu.unicredit.web.Models.DomNode

import scala.collection.mutable
import scala.util.Try

/**
  * Created by fabiofumarola on 27/05/16.
  */
object Distances {

  def editDistance[T](a: Seq[T], b: Seq[T]) = {
    ((0 to b.size).toList /: a) ((prev, x) =>
      (prev zip prev.tail zip b).scanLeft(prev.head + 1) {
        case (h, ((d, v), y)) => Math.min(Math.min(h + 1, v + 1), d + (if (x == y) 0 else 1))
      }).last.toFloat
  }

  def normalizedEditDistance[T](a: Seq[T], b: Seq[T]): Float = {
    editDistance(a, b) / ((a.size + b.size) / 2)
  }

  def levenshtein(a: Seq[String], b: Seq[String]) = {
    LevenshteinMetric.compare(
      Encoder.encode(a).toArray,
      Encoder.encode(b).toArray).get.toFloat / ((a.size + b.size) / 2)
  }

  def diceSorensenMetric(a: Seq[String], b: Seq[String]) = {
    DiceSorensenMetric(1).compare(
      Encoder.encode(a).toArray,
      Encoder.encode(b).toArray)
  }

  /**
    * Implement the simple tree matching algorithm
    *
    * @param a
    * @param b
    * @return
    */
  def treeEditDistance (a: DomNode, b:DomNode): Double = {

    a.tagName.equals(b.tagName) match {
      case false => 0D
      case true =>
        val num_rows = a.children.size + 1
        val num_columns =  b.children.size + 1
        val matchMatrix = Array.ofDim[Double](num_rows, num_columns)

        //Initialize 0th row and 0th column
        matchMatrix.indices.foreach(row => matchMatrix(row)(0) = 0D)
        matchMatrix(0).indices.foreach(column => matchMatrix(0)(column) = 0D)

        val pairs = for{
          row <- 1 until num_rows
          column <- 1 until num_columns
        } yield (row, column)

        pairs.foreach {
          case (row, column) =>
            val left_distance = matchMatrix(row)(column - 1)
            val up_distance = matchMatrix(row - 1)(column)
            val diagonal_distance = matchMatrix(row - 1)(column - 1) + treeEditDistance(a.children(row - 1), b.children(column - 1))
            val bestDistance = List(left_distance, up_distance, diagonal_distance).max
            matchMatrix(row)(column) = bestDistance
        }
        1D + matchMatrix(matchMatrix.length - 1)(matchMatrix(0).length - 1)
    }
  }

  def normalizedTreeEditDistance (a: DomNode, b:DomNode) : Double = {
    def getSize0(nodes: List[DomNode], acc:Int): Int = {
      nodes match {
        case List() => acc
        case h::tail => getSize0(h.children.toList ++ tail, acc+1)
      }
    }
    def getSize(tree: DomNode): Int = {
      getSize0(List(tree), 0)
    }

    val ted = treeEditDistance(a,b)
    val avgNodes = (getSize(a) + getSize(b)).toDouble /2
    ted.toDouble / avgNodes
  }

}

object Encoder {

  private val map = mutable.Map.empty[String, Char]
  private val reverseMap = mutable.Map.empty[Char, String]

  //starting from the encoding of a
  private var current = 97

  def encode(seq: Seq[String]): Seq[Char] = {
    seq.map { e =>
      map.getOrElseUpdate(e, {
        val a = current
        current += 1
        reverseMap += (a.toChar -> e)
        a.toChar
      })
    }
  }

  def decode(seq: Seq[Char]): Try[Seq[String]] = Try {
    seq.map(c => reverseMap(c))
  }
}
