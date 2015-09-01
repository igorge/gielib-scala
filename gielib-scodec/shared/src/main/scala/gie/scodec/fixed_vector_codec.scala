package gie.scodec

import scodec.Attempt.{Failure, Successful}
import scodec.bits.BitVector
import scodec._


class FixedVectorCodec[A](count: Int, codec: Codec[A]) extends Codec[Vector[A]] {

  def sizeBound = codec.sizeBound * count

  def encode(vector: Vector[A]) = Encoder.encodeSeq(codec)(vector)

  def decode(buffer: BitVector) =
    Decoder.decodeCollect[Vector, A](codec, Some(count))(buffer) flatMap {
      case DecodeResult(vec, remainder) if vec.size!=count => Attempt.failure(Err(s"FixedVectorCodec: got only ${vec.size} elements out of ${count}"))
      case r@_ => Attempt.successful(r)
    }

  override def toString = s"vector($codec)"

}


object FixedVectorCodec {
  def fixedVectorCodec[A](count: Int, codec: Codec[A]):Codec[Vector[A]] = new FixedVectorCodec(count, codec)
}

