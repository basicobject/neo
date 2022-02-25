package neo

import com.google.inject.{Guice, Injector, Stage}
import com.typesafe.scalalogging.StrictLogging
import io.grpc.ServerServiceDefinition
import neo.grpc.services.{NeoHealthCheckService, NeoNameSearchService}
import neo.grpc.{GrpcServer, NettyGrpcServer}

import scala.util.control.NonFatal

object Main extends StrictLogging {
  val Port = 3333

  val injector: Injector = Guice.createInjector(
    Stage.PRODUCTION,
    new MainModule()
  )

  private lazy val healthCheckService =
    injector.getInstance(classOf[NeoHealthCheckService])

  private lazy val searchService =
    injector.getInstance(classOf[NeoNameSearchService])

  private def start(): Unit = {
    val services: Set[ServerServiceDefinition] = Set(
      healthCheckService,
      searchService
    ).map(_.binding())

    val server: GrpcServer = new NettyGrpcServer(services, Port)

    try {
      server.start()
      logger.info(s"Netty grpc server listening on port $Port")
    } catch {
      case NonFatal(e) =>
        logger.error("Error starting server", e)
        sys.exit(1)
    }
  }

  def main(args: Array[String]): Unit = {
    logger.info(s"Java version ${System.getProperty("java.version")}")
    logger.info("Starting server")
    start()
  }
}
