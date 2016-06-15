package eu.unicredit.web.hylien

import com.rockymadden.stringmetric.similarity._

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
