package gie

import utest._

import scala.collection.mutable


object sorted_merge_test extends TestSuite {

  case class Obj(key: Int, payload: Int = 0)

  def cmp(l: Obj, r: Obj) = implicitly[Ordering[Int]].compare(l.key, r.key)
  def onSame(l: Obj, r: Obj) = l


  def tests = TestSuite {

    'Empty {
      val l = Array[Obj]()
      val r = Array[Obj]()
      val outr = mutable.ArrayBuffer[Obj]()

      val out = gie.sorted_merge.merge(l, r, outr)(cmp)(onSame)

      assert(out eq outr)
      assert(out.size==0)
    }

    'EmptyLeft {
      val l = Array[Obj]()
      val r = Array[Obj](Obj(1))
      val outr = mutable.ArrayBuffer[Obj]()

      val out = gie.sorted_merge.merge(l, r, outr)(cmp)(onSame)

      assert(out eq outr)
      assert(out.size==1)
      assert(out.toSeq == Array(Obj(1)).toSeq)
    }

    'EmptyRight {
      val l = Array[Obj](Obj(1))
      val r = Array[Obj]()
      val outr = mutable.ArrayBuffer[Obj]()

      val out = gie.sorted_merge.merge(l, r, outr)(cmp)(onSame)

      assert(out eq outr)
      assert(out.size==1)
      assert(out.toSeq == Array(Obj(1)).toSeq)
    }

    'SingleMergeLeft {
      val l = Array[Obj](Obj(1,10))
      val r = Array[Obj](Obj(1,11))
      val outr = mutable.ArrayBuffer[Obj]()

      val out = gie.sorted_merge.merge(l, r, outr)(cmp)(onSame)

      assert(out eq outr)
      assert(out.size==1)
      assert(out.toSeq == Array(Obj(1, 10)).toSeq)
    }

    'SingleMergeRight {
      val l = Array[Obj](Obj(1,10))
      val r = Array[Obj](Obj(1,11))
      val outr = mutable.ArrayBuffer[Obj]()

      val out = gie.sorted_merge.merge(l, r, outr)(cmp)((l,r)=>r)

      assert(out eq outr)
      assert(out.size==1)
      assert(out.toSeq == Array(Obj(1, 11)).toSeq)
    }


    'Eqlength {
      val l = Array[Obj](Obj(1), Obj(4))
      val r = Array[Obj](Obj(2), Obj(3))
      val outr = mutable.ArrayBuffer[Obj]()

      val out = gie.sorted_merge.merge(l, r, outr)(cmp)((l,r)=>r)

      assert(out eq outr)
      assert(out.size==4)
      assert(out.toSeq == Array(Obj(1), Obj(2), Obj(3), Obj(4)).toSeq)
    }

    'TailLeft {
      val l = Array[Obj](Obj(1), Obj(4), Obj(10))
      val r = Array[Obj](Obj(2), Obj(3))
      val outr = mutable.ArrayBuffer[Obj]()

      val out = gie.sorted_merge.merge(l, r, outr)(cmp)((l,r)=>r)

      assert(out eq outr)
      assert(out.size==5)
      assert(out.toSeq == Array(Obj(1), Obj(2), Obj(3), Obj(4), Obj(10)).toSeq)
    }

    'TailRight {
      val l = Array[Obj](Obj(1), Obj(4))
      val r = Array[Obj](Obj(2), Obj(3), Obj(10))
      val outr = mutable.ArrayBuffer[Obj]()

      val out = gie.sorted_merge.merge(l, r, outr)(cmp)((l,r)=>r)

      assert(out eq outr)
      assert(out.size==5)
      assert(out.toSeq == Array(Obj(1), Obj(2), Obj(3), Obj(4), Obj(10)).toSeq)
    }

  }
}
