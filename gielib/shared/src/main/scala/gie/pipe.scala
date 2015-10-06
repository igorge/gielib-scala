package gie

package object ImplicitPipe {

  implicit final class Pipe[T](val t:T) extends AnyVal{
    @inline def |%>[X, U](f: X=>U)(implicit ev: T=>X) = f(t)
    @inline def |>[U](f: T=>U): U = f(t)
    @inline def map[U](f: T=>U): U = f(t)
  }

}