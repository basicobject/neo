package neo.grpc

import com.typesafe.scalalogging.StrictLogging
import io.grpc.ServerServiceDefinition
import io.grpc.netty.NettyServerBuilder

import scala.util.control.NonFatal

final class NettyGrpcServer(
    services: Set[ServerServiceDefinition],
    port: Int
) extends GrpcServer
    with StrictLogging {
  private val builder = NettyServerBuilder
    .forPort(port)

  bindings().foreach(builder.addService)

  private val server = builder.build()

  override def start(): Unit = {
    try {
      server.start()
    } catch {
      case NonFatal(e) =>
        logger.error("Failed to start server", e)
        sys.exit(-1)
    }

    sys.addShutdownHook({
      logger.info(s"Shutting down ${this.getClass.getSimpleName}")
      server.shutdown()
    })
  }

  override def stop(): Unit = server.shutdown()

  override def bindings(): Seq[ServerServiceDefinition] = services.toSeq
}
