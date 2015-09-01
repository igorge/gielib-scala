package gie.jsutils

import scala.scalajs.js

class BufferConstructor(ctor: js.Dynamic = jsRequire("buffer").Buffer){
  def apply[T <: js.Any](v: T) = ctor.apply(v)
}

object BufferConstructor extends (()=>BufferConstructor){
  def apply() = new BufferConstructor()
}