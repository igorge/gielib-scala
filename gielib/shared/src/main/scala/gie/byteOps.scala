package gie


object ByteOps {
  @inline final def ub2i(b:Byte): Int = 0x000000FF & b
}