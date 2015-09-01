package gie.jsutils

import org.scalajs.dom

import scala.concurrent.duration.Duration
import scala.concurrent.{CanAwait, ExecutionContext, Future, Promise}
import scala.util.Try

class ErrorEventWrapper(e: dom.ErrorEvent) extends Exception(s"ErrorEven: ${e.message}")

class ConnectionException(val status:Int, val data: Any) extends Exception(s"status: ${status}, data: ${data}")
class ConnectionAbortedException(status:Int, data: Any) extends ConnectionException(status, data)

object XMLHttpRequestFuture {
  object responseType {
    val domString = ""
    val arrayBuffer = "arraybuffer"
    val blob = "blob"
    val document = "document"
    val json = "json"
    val text = "text" //DOMString
  }
}


class XMLHttpRequestFuture(wrapped: dom.XMLHttpRequest, responseType: Option[String] = None) extends Future[AnyRef]{
  type T=AnyRef

  private val m_promise = Promise[T]()

  responseType.foreach( wrapped.responseType = _)

  wrapped.onload = (e: dom.Event) => {
    val status = wrapped.status

    status match {
      case 200 =>
        m_promise.success(impl_decodeResponse())
      case _ =>
        m_promise.failure( new ConnectionException(status, null) )
    }
  }

  wrapped.onerror = (ee: dom.ErrorEvent) => {
    val status = wrapped.status
    val data = null

    val e = new ConnectionException(status, data)
    e.initCause( new ErrorEventWrapper(ee) )

    m_promise.failure( e )
  }

  wrapped.onabort = (_: Any) =>{
    val status = wrapped.status

    m_promise.failure( new ConnectionAbortedException(status, null) )
  }

  private def impl_decodeResponse(): T ={
    wrapped.response
  }

  private lazy val future = m_promise.future

  override def onComplete[U](f: (Try[T]) => U)(implicit executor: ExecutionContext): Unit = future.onComplete(f)

  override def isCompleted: Boolean = m_promise.isCompleted

  override def value: Option[Try[T]] = future.value

  override def result(atMost: Duration)(implicit permit: CanAwait): T = future.result(atMost)

  override def ready(atMost: Duration)(implicit permit: CanAwait): this.type = {future.ready(atMost);this}
}