package gie.validator


import scala.collection.mutable.ArrayBuffer



sealed abstract class ValidationResult[+A,+B]{
    def isFailure: Boolean
    def get:A
    def getFailures:Seq[B]

    def map[R](f: (A) => R): ValidationResult[R,B] = {
        if(isFailure)
            VFailure[R,B](this.getFailures)
        else
            VSuccess[R,B](f(this.get))
    }

}



case class VSuccess[+A,+B](v:A) extends ValidationResult[A,B] {
    def get: A = v

    def getFailures: Seq[B] = throw new NoSuchElementException("VSuccess.getFailures")

    def isFailure: Boolean = false
}

object success {
    def apply[E,A](v: A): ValidationResult[A,E] = VSuccess[A,E](v)
}


case class VFailure[+A,+B](err:Seq[B])  extends ValidationResult[A,B] {
    def get: A = throw new NoSuchElementException("VFailure.get")

    def getFailures: Seq[B] = err

    def isFailure: Boolean = true
}



object failure{
    def apply[A,B](v: B):ValidationResult[A,B] = VFailure[A,B](Seq(v))
}



object Validation {

    def zipMap[B, A1, B1 <: B, A2, B2 <:B, R ](v1: ValidationResult[A1,B1], v2: ValidationResult[A2,B2])(f: (A1,A2) => R): ValidationResult[R, B] = {
        var errBuffer:ArrayBuffer[B] = null

        if(v1.isFailure){
            if(errBuffer eq null) errBuffer = new ArrayBuffer[B]()
            errBuffer ++= v1.getFailures
        }

        if(v2.isFailure){
            if(errBuffer eq null) errBuffer = new ArrayBuffer[B]()
            errBuffer ++= v2.getFailures
        }

        if(errBuffer eq null)
            VSuccess[R,B](f(v1.get, v2.get))
        else
            VFailure[R,B](errBuffer)
    }

    def zipMap[B, A1, B1 <: B, A2, B2 <:B, A3, B3 <:B, R ](v1: ValidationResult[A1,B1], v2: ValidationResult[A2,B2], v3: ValidationResult[A3,B3])(f: (A1,A2,A3) => R): ValidationResult[R, B] = {
        var errBuffer:ArrayBuffer[B] = null

        if(v1.isFailure){
            if(errBuffer eq null) errBuffer = new ArrayBuffer[B]()
            errBuffer ++= v1.getFailures
        }

        if(v2.isFailure){
            if(errBuffer eq null) errBuffer = new ArrayBuffer[B]()
            errBuffer ++= v2.getFailures
        }

        if(v3.isFailure){
            if(errBuffer eq null) errBuffer = new ArrayBuffer[B]()
            errBuffer ++= v3.getFailures
        }

        if(errBuffer eq null)
            VSuccess[R,B](f(v1.get, v2.get, v3.get))
        else
            VFailure[R,B](errBuffer)
    }

    def zipMap[B, A1, B1 <: B, A2, B2 <:B, A3, B3 <:B, A4, B4 <:B, R ](v1: ValidationResult[A1,B1],
                                                                       v2: ValidationResult[A2,B2],
                                                                       v3: ValidationResult[A3,B3],
                                                                       v4: ValidationResult[A4,B4])
                                                                      (f: (A1,A2,A3,A4) => R): ValidationResult[R, B] = {
        var errBuffer:ArrayBuffer[B] = null

        if(v1.isFailure){
            if(errBuffer eq null) errBuffer = new ArrayBuffer[B]()
            errBuffer ++= v1.getFailures
        }

        if(v2.isFailure){
            if(errBuffer eq null) errBuffer = new ArrayBuffer[B]()
            errBuffer ++= v2.getFailures
        }

        if(v3.isFailure){
            if(errBuffer eq null) errBuffer = new ArrayBuffer[B]()
            errBuffer ++= v3.getFailures
        }

        if(v4.isFailure){
            if(errBuffer eq null) errBuffer = new ArrayBuffer[B]()
            errBuffer ++= v4.getFailures
        }

        if(errBuffer eq null)
            VSuccess[R,B](f(v1.get, v2.get, v3.get, v4.get))
        else
            VFailure[R,B](errBuffer)
    }

}

