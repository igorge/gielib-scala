package gie

import utest._

import scala.collection.mutable


object mergedForeach_test extends TestSuite {

  case class Obj(key: Int, payload: Int = 0)
  def cmp(l: Obj, r: Obj) = implicitly[Ordering[Int]].compare(l.key, r.key)

  def tests = TestSuite {

    'Empty {
      val l = Array[Obj]()
      val r = Array[Obj]()
      val out_l = mutable.ArrayBuffer[Obj]()
      val out_r = mutable.ArrayBuffer[Obj]()
      val out_m = mutable.ArrayBuffer[Obj]()

      def outSame(l:Obj, r: Obj): Unit ={
        out_l += l
        out_r += r
        out_m += l
      }

      def outS1(l:Obj): Unit ={
        out_l += l
        out_m += l
      }

      def outS2(r:Obj): Unit ={
        out_r += r
        out_m += r
      }

      gie.sorted_merge.mergedForeachOptSeq(l, r)(cmp)(outSame)(outS1)(outS2)

      assert(out_l.toSeq == Array().toSeq)
      assert(out_r.toSeq == Array().toSeq)
      assert(out_m.toSeq == Array().toSeq)
    }

    'EmptyNull {
      val out_l = mutable.ArrayBuffer[Obj]()
      val out_r = mutable.ArrayBuffer[Obj]()
      val out_m = mutable.ArrayBuffer[Obj]()

      def outSame(l:Obj, r: Obj): Unit ={
        out_l += l
        out_r += r
        out_m += l
      }

      def outS1(l:Obj): Unit ={
        out_l += l
        out_m += l
      }

      def outS2(r:Obj): Unit ={
        out_r += r
        out_m += r
      }

      gie.sorted_merge.mergedForeachOptSeq(null, null)(cmp)(outSame)(outS1)(outS2)

      assert(out_l.toSeq == Array().toSeq)
      assert(out_r.toSeq == Array().toSeq)
      assert(out_m.toSeq == Array().toSeq)
    }

    'EmptyLeft {
      val l = Array[Obj]()
      val r = Array[Obj](Obj(1))

      val out_l = mutable.ArrayBuffer[Obj]()
      val out_r = mutable.ArrayBuffer[Obj]()
      val out_m = mutable.ArrayBuffer[Obj]()

      def outSame(l:Obj, r: Obj): Unit ={
        out_l += l
        out_r += r
        out_m += l
      }

      def outS1(l:Obj): Unit ={
        out_l += l
        out_m += l
      }

      def outS2(r:Obj): Unit ={
        out_r += r
        out_m += r
      }

      gie.sorted_merge.mergedForeachOptSeq(l, r)(cmp)(outSame)(outS1)(outS2)

      assert(out_l.toSeq == Array().toSeq)
      assert(out_r.toSeq == Array(Obj(1)).toSeq)
      assert(out_m.toSeq == Array(Obj(1)).toSeq)
    }

    'EmptyLeftNull {
      val r = Array[Obj](Obj(1))

      val out_l = mutable.ArrayBuffer[Obj]()
      val out_r = mutable.ArrayBuffer[Obj]()
      val out_m = mutable.ArrayBuffer[Obj]()

      def outSame(l:Obj, r: Obj): Unit ={
        out_l += l
        out_r += r
        out_m += l
      }

      def outS1(l:Obj): Unit ={
        out_l += l
        out_m += l
      }

      def outS2(r:Obj): Unit ={
        out_r += r
        out_m += r
      }

      gie.sorted_merge.mergedForeachOptSeq(null, r)(cmp)(outSame)(outS1)(outS2)

      assert(out_l.toSeq == Array().toSeq)
      assert(out_r.toSeq == Array(Obj(1)).toSeq)
      assert(out_m.toSeq == Array(Obj(1)).toSeq)
    }

    'EmptyRight {
      val l = Array[Obj](Obj(1))
      val r = Array[Obj]()

      val out_l = mutable.ArrayBuffer[Obj]()
      val out_r = mutable.ArrayBuffer[Obj]()
      val out_m = mutable.ArrayBuffer[Obj]()

      def outSame(l:Obj, r: Obj): Unit ={
        out_l += l
        out_r += r
        out_m += l
      }

      def outS1(l:Obj): Unit ={
        out_l += l
        out_m += l
      }

      def outS2(r:Obj): Unit ={
        out_r += r
        out_m += r
      }

      gie.sorted_merge.mergedForeachOptSeq(l, r)(cmp)(outSame)(outS1)(outS2)

      assert(out_l.toSeq == Array(Obj(1)).toSeq)
      assert(out_r.toSeq == Array().toSeq)
      assert(out_m.toSeq == Array(Obj(1)).toSeq)
    }

    'EmptyRightNull {
      val l = Array[Obj](Obj(1))
      val r = Array[Obj]()

      val out_l = mutable.ArrayBuffer[Obj]()
      val out_r = mutable.ArrayBuffer[Obj]()
      val out_m = mutable.ArrayBuffer[Obj]()

      def outSame(l:Obj, r: Obj): Unit ={
        out_l += l
        out_r += r
        out_m += l
      }

      def outS1(l:Obj): Unit ={
        out_l += l
        out_m += l
      }

      def outS2(r:Obj): Unit ={
        out_r += r
        out_m += r
      }

      gie.sorted_merge.mergedForeachOptSeq(l, null)(cmp)(outSame)(outS1)(outS2)

      assert(out_l.toSeq == Array(Obj(1)).toSeq)
      assert(out_r.toSeq == Array().toSeq)
      assert(out_m.toSeq == Array(Obj(1)).toSeq)
    }

    'Eqlength {
      val l = Array[Obj](Obj(1), Obj(4), Obj(10,33))
      val r = Array[Obj](Obj(2), Obj(3), Obj(10,22))

      val out_l = mutable.ArrayBuffer[Obj]()
      val out_r = mutable.ArrayBuffer[Obj]()
      val out_m = mutable.ArrayBuffer[Obj]()

      def outSame(l:Obj, r: Obj): Unit ={
        out_l += l
        out_r += r
        out_m += l
      }

      def outS1(l:Obj): Unit ={
        out_l += l
        out_m += l
      }

      def outS2(r:Obj): Unit ={
        out_r += r
        out_m += r
      }

      gie.sorted_merge.mergedForeachOptSeq(l, r)(cmp)(outSame)(outS1)(outS2)

      assert(out_l.toSeq == Array(Obj(1), Obj(4), Obj(10,33)).toSeq)
      assert(out_r.toSeq == Array(Obj(2), Obj(3), Obj(10,22)).toSeq)
      assert(out_m.toSeq == Array(Obj(1), Obj(2), Obj(3), Obj(4), Obj(10,33)).toSeq)
    }



  }
}


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
