package gie.utils

package object loan {
  
  def acquire[T <: AutoCloseable, U](resource: =>T)(fun: T=>U):U = {

    val allocatedResource = resource

    val r = try {
      fun(allocatedResource)
    } catch {

      case ex: Throwable =>
        try{
          allocatedResource.close()
        } catch {
          case exx:Throwable => ex.addSuppressed(exx)
        }

        throw ex
    }

    allocatedResource.close()

    r
  }

  def acquire[T1 <: AutoCloseable, T2 <: AutoCloseable, U](resource1: =>T1, resource2: =>T2)(fun: (T1,T2)=>U):U = {

    acquire(resource1){res1=>
      acquire(resource2){res2 =>
        fun(res1, res2)
      }
    }

  }

  
}

