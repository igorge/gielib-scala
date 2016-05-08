package gie

import java.util.{UUID=>jUUID}
import java.nio.ByteBuffer


class RichUUID(val jUuid:jUUID) extends AnyVal with Ordered[RichUUID] {
  override def toString() = jUuid.toString()
  override def compare(other: RichUUID) = jUuid.compareTo(other.jUuid)

  def toByteArray() = ByteBuffer.wrap( Array[Byte](16) ).putLong( jUuid.getMostSignificantBits() ).putLong( jUuid.getLeastSignificantBits() ).array()

  def toJava = jUuid
}

object UUID {

  implicit def richUUID(juuid: jUUID):RichUUID = new RichUUID(juuid)

  val uuidStringSize = 36
  def unapply(data: String):Option[jUUID] = if(data.size!=uuidStringSize) None else this.apply(data).toOption
  def apply() = jUUID.randomUUID()
  def apply(data:String) = scala.util.Try{ jUUID.fromString(data) }
  def apply(data: Array[Byte]) = {
    assume( data.size==16 )
    val bb = ByteBuffer.wrap(data)
    new jUUID( bb.getLong(), bb.getLong() )
  }
  def zero =  new jUUID(0,0)
}

