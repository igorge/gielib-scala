package gie.jsutils


import scala.scalajs.js


object Require extends js.GlobalScope {
   def require(v: String): js.Dynamic = js.native
}

object jsRequire extends (String=>js.Dynamic) {
   def apply(v: String) = Require.require(v)
}

