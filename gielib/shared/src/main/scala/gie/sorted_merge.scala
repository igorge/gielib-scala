package gie

import scala.annotation.switch
import scala.collection.generic.Growable

object sorted_merge {

  def merge[
      T,
      S1 <% IndexedSeq[T],
      S2 <% IndexedSeq[T],
      SR <% Growable[T]  ]
  (s1: S1, s2: S2, out:SR)
  (cmp: (T,T)=>Int)(onEq: (T,T)=>T): SR =
  {
    val s1_size = s1.size
    val s2_size = s2.size

    var s1_i = 0
    var s2_i = 0

    while (s1_i!=s1_size && s2_i!=s2_size){
      (math.signum(cmp(s1(s1_i), s2(s2_i))): @switch) match {
        case -1 =>
          out += s1(s1_i)
          s1_i += 1
        case  1 =>
          out += s2(s2_i)
          s2_i += 1
        case  _ =>
          out += onEq( s1(s1_i), s2(s2_i) )
          s1_i += 1
          s2_i += 1
      }
    } // while

    // copy tail
    if(s1_i!=s1_size){
      while (s1_i!=s1_size) {
        out += s1(s1_i)
        s1_i += 1
      }
    } else { // s1_i==s1_size && s2_i!=s2_size
      while (s2_i!=s2_size) {
        out += s2(s2_i)
        s2_i += 1
      }

    }

    out
  }

}