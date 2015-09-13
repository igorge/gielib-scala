package gie

import utest._

import scala.collection.Searching.{InsertionPoint, Found}
import scala.collection.mutable.ArrayBuffer

object binary_search extends TestSuite {

  case class Obj(key: Int)

  val cmp:(Int, Obj)=>Int = (key, o)=>{
    implicitly[Ordering[Int]].compare(key, o.key)
  }

  def tests = TestSuite {

      'Empty {
        val array = ArrayBuffer[Obj]()
        val r = search.binarySearch(3, array, 0, array.size)(cmp)
        assert(r == InsertionPoint(0))
      }

      'SingleFound {
        val array = ArrayBuffer( Obj(3) )
        val r = search.binarySearch(3, array, 0, array.size)(cmp)
        assert(r == Found(0))
      }

      'SingleNotFound {
        val array = ArrayBuffer( Obj(2) )
        val r = search.binarySearch(3, array, 0, array.size)(cmp)
        assert(r == InsertionPoint(1))
      }


      'Combined {
        val array = ArrayBuffer( Obj(1), Obj(3), Obj(6) )

        val r01 = search.binarySearch(1, array)(cmp)
        assert( r01 == Found(0))

        val r02 = search.binarySearch(3, array)(cmp)
        assert( r02 == Found(1))

        val r03 = search.binarySearch(6, array)(cmp)
        assert( r03 == Found(2))

        val r04 = search.binarySearch(0, array)(cmp)
        assert( r04 == InsertionPoint(0))

        val r05 = search.binarySearch(2, array)(cmp)
        assert( r05 == InsertionPoint(1))

        val r06 = search.binarySearch(5, array)(cmp)
        assert( r06 == InsertionPoint(2))

        val r07 = search.binarySearch(8, array)(cmp)
        assert( r07 == InsertionPoint(3))
      }


      'Combined2 {
        val array = ArrayBuffer( Obj(1), Obj(3), Obj(6), Obj(11))

        val r01 = search.binarySearch(1, array)(cmp)
        assert( r01 == Found(0))

        val r02 = search.binarySearch(3, array)(cmp)
        assert( r02 == Found(1))

        val r03 = search.binarySearch(6, array)(cmp)
        assert( r03 == Found(2))

        val r04 = search.binarySearch(0, array)(cmp)
        assert( r04 == InsertionPoint(0))

        val r05 = search.binarySearch(2, array)(cmp)
        assert( r05 == InsertionPoint(1))

        val r06 = search.binarySearch(5, array)(cmp)
        assert( r06 == InsertionPoint(2))

        val r07 = search.binarySearch(8, array)(cmp)
        assert( r07 == InsertionPoint(3))


        val r08 = search.binarySearch(11, array)(cmp)
        assert( r08 == Found(3))

        val r09 = search.binarySearch(13, array)(cmp)
        assert( r09 == InsertionPoint(4))

      }


  }

}