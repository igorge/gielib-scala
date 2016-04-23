package gie.javafx

import javafx.scene.Node

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try
import scalafx.application.Platform

object `package` {

    implicit class FutureOps[T](val f: Future[T]) extends AnyVal {

        def guiOnComplete[R](fun: Try[T]=>R)(implicit executor: ExecutionContext): Unit ={
            f.onComplete(result=>Platform.runLater{fun(result)})
        }

    }

}