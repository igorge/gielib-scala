package gie.utils.prop

import java.io.InputStreamReader
import java.util.PropertyResourceBundle
import gie.utils.loan
import scala.util.{Failure, Success, Try}

class PropException(cause: Throwable = null) extends Exception(cause)
class PropResourceNotFound(resource: String) extends PropException { override def toString = s"PropResourceNotFound( '${resource}' )"}
class PropKeyNotFound(resource: String, cause: Throwable = null) extends PropException(cause) { override def toString = s"PropKeyNotFound( '${resource}' )"}
class PropEmpty extends PropException

class Messages(val props:Props){
  def get(id: String) = props(id)
  def get(id: Symbol) = props(id)

  def apply(id: String) = props(id)
  def apply(id: Symbol) = props(id)
}

class Configuration(val props:Props){
  def get(id: String) = props(id)
  def get(id: Symbol) = props(id)

  def apply(id: String) = props(id)
  def apply(id: Symbol) = props(id)
}


trait Props { self=>
  def apply(id: String):String
  final def apply(id: Symbol):String = this.apply(id.name)

  def andThen(other: Props) = new Props {
    def apply(id: String): String = Try{ self.apply(id) } match {
      case Success(v)   => v
      case Failure(ex)  =>
        try{ other.apply(id) } catch{ case exx:Throwable=> ex.addSuppressed(exx); throw ex}
    }
  }
}

trait WithMemo extends Props { self: Props =>

  val m_memoFun = impl_memoBuilder( super.apply(_))

  private def impl_memoBuilder(fun: String=>String) = {
    import scalaz.Memo

    val memoBuilder = Memo.immutableHashMapMemo[String,String]
    memoBuilder(fun)
  }

  abstract override def apply(id: String):String = {
    m_memoFun(id)
  }
}

trait WithPrefix extends Props { self: Props =>
  val prefix:String

  abstract override def apply(id: String):String = {
    super.apply( prefix + id )
  }
}

class  PropsFromClassLoaderBundle(resourceName: String) extends Props{

  val msgsResourceBundle = {
    loan.acquire {
      val msgsStream = this.getClass().getClassLoader().getResourceAsStream(resourceName)
      if(msgsStream eq null) throw new PropResourceNotFound(resourceName)
      new InputStreamReader(msgsStream, "utf-8")
    }( new PropertyResourceBundle(_) )
  }

  override def apply(id: String):String = {
    try msgsResourceBundle.getString(id) catch { case e: Throwable => throw new PropKeyNotFound(id, e) }
  }

}

class  PropsFromMultiple(props: Props*) extends Props {

  private val m_props = {
    val iter = props.toIterator
    var acum: Props = null

    while( iter.hasNext ){
      val cur = iter.next()
      if(acum eq null) acum = cur else {
        acum = acum.andThen(cur)
      }
    }
    acum
  }

  override def apply(id: String):String = {
    if(m_props eq null) throw new PropEmpty
    m_props.apply(id)
  }

}


//
//import scala.util.Try
//
//case class MessagesSearchList(paths: Seq[String])
//
//
//class MessageResolver(val messages: Messages, val sl: MessagesSearchList){
//  val resolverMemo = messages.memo( messages(_)(sl) )
//
//  def apply(id: Symbol):String = resolverMemo(id)
//}
//
//object MessageResolver {
//  def apply(messages: Messages, sl: MessagesSearchList): MessageResolver = {
//    new MessageResolver(messages, sl)
//  }
//
//  def apply(parent :MessageResolver, sl: MessagesSearchList): MessageResolver = {
//    new MessageResolver(parent.messages, MessagesSearchList( sl.paths ++ parent.sl.paths ))
//  }
//}
//
//class Messages()(implicit app: ApplicationTrait) {
//  import java.util.{PropertyResourceBundle}
//  import java.io.InputStreamReader
//
//  val msgsResourceBundle = {
//    val p = s"${app.cfg.getString(app.KEY_MSGS)}.properties"
//    val msgsStream = app.getClass().getClassLoader().getResourceAsStream(p)
//    assert(msgsStream!=null)
//    val msgsReader = new InputStreamReader(msgsStream, "utf-8")
//    new PropertyResourceBundle(msgsReader)
//  }
//
//  def apply(id: Symbol)(implicit sl: MessagesSearchList): String = this(id.name)
//  def apply(id: String)(implicit sl: MessagesSearchList): String = {
//    var opResult:Try[String] = null
//    sl.paths.find{key=>
//      opResult = Try{msgsResourceBundle.getString(s"${key}.${id}")}
//      opResult.isSuccess
//    }
//    opResult.get
//  }
//
//  def resolver(sl: MessagesSearchList) = new MessageResolver(this, sl)
//
//  def memo(fun: Symbol=>String) = {
//    import scalaz.Memo
//
//    val memoBuilder = Memo.immutableHashMapMemo[Symbol,String]
//    memoBuilder(fun)
//  }
//
//}