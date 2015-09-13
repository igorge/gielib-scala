package gie

import scala.annotation.{switch, tailrec}
import scala.collection.Searching.{Found, InsertionPoint, SearchResult}

object search {

  @tailrec
  private def binarySearch[A, B](key: A, data: IndexedSeq[B], from: Int, to: Int)(compare: (A,B)=>Int): SearchResult = {
    if (to == from) InsertionPoint(from) else {
      val idx = from+(to-from-1)/2
      (math.signum(compare(key, data(idx))): @switch) match {
        case -1 => binarySearch(key, data, from, idx)(compare)
        case  1 => binarySearch(key, data, idx + 1, to)(compare)
        case  _ => Found(idx)
      }
    }
  }


}