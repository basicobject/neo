package neo.util

import com.typesafe.scalalogging.StrictLogging

import java.util.concurrent.TimeUnit
import scala.concurrent.{ExecutionContext, Future}

trait TimedExecution {
  def duration(startTime: Long): Long = TimeUnit.MILLISECONDS
    .convert(System.nanoTime() - startTime, TimeUnit.NANOSECONDS)

  def timedFuture[T](name: String, operation: String)(
      block: => Future[T]
  ): Future[T] = {
    val startTime = System.nanoTime()
    val result = block
    block.onComplete({ _ =>
      println(
        s"Finished $name $operation in " + duration(startTime) + " millis"
      )
    })(ExecutionContext.parasitic)
    result
  }
}
