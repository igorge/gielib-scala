import java.io.{ByteArrayOutputStream, PrintWriter}

package object gie {

    def getExceptionStackTrace(e: Throwable): String={

        val outStream = new ByteArrayOutputStream()
        val outWriter = new PrintWriter(outStream)
        e.printStackTrace(outWriter)

        outWriter.flush()
        outStream.flush()

        outStream.toString()
    }


}
