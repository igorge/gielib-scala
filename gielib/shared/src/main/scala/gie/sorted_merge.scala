package gie

import scala.annotation.switch
import scala.collection.generic.Growable

object sorted_merge {


  @inline
  def merge[T](s1: IndexedSeq[T], s2: IndexedSeq[T], out:Growable[T])
              (cmp: (T,T)=>Int)
              (eqMap: (T,T)=>T): out.type =
  {
    mergedForeach(s1, s2)(cmp){ (l,r)=>out+=eqMap(l,r) }(l=>out+=l)(r=>out+=r)
    out
  }


  @inline
  def mergedForeachOptSeq[T](s1: IndexedSeq[T], s2: IndexedSeq[T])
                      (cmp: (T,T)=>Int)
                      (outEq: (T,T)=>Unit)(outS1: T=>Unit)(outS2: T=>Unit): Unit =
  {
    if(s1 eq null) {
      if(s2 ne null){
        val s2_size = s2.size
        var i = 0
        while (i!=s2_size){
          outS2(s2(i))
          i+=1
        }
      }
    } else if(s2 eq null) {
        val s1_size = s1.size
        var i = 0
        while (i!=s1_size){
          outS1(s1(i))
          i+=1
        }
    } else {
      mergedForeach(s1,s2)(cmp)(outEq)(outS1)(outS2)
    }

  }


  def mergedForeach[T](s1: IndexedSeq[T], s2: IndexedSeq[T])
                      (cmp: (T,T)=>Int)
                      (outEq: (T,T)=>Unit)(outS1: T=>Unit)(outS2: T=>Unit): Unit =
  {
    val s1_size = s1.size
    val s2_size = s2.size

    var s1_i = 0
    var s2_i = 0

    while (s1_i!=s1_size && s2_i!=s2_size){
      (math.signum(cmp(s1(s1_i), s2(s2_i))): @switch) match {
        case -1 =>
          outS1( s1(s1_i) )
          s1_i += 1
        case  1 =>
          outS2( s2(s2_i) )
          s2_i += 1
        case  _ =>
          outEq( s1(s1_i), s2(s2_i) )
          s1_i += 1
          s2_i += 1
      }
    } // while

    // copy tail
    if(s1_i!=s1_size){
      while (s1_i!=s1_size) {
        outS1( s1(s1_i) )
        s1_i += 1
      }
    } else { // s1_i==s1_size && s2_i!=s2_size
      while (s2_i!=s2_size) {
        outS2( s2(s2_i) )
        s2_i += 1
      }

    }
  }
  
}