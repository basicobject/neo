package neo.grpc.services

import com.typesafe.scalalogging.StrictLogging
import io.grpc.ServerServiceDefinition
import neo.grpc.ServiceBinding
import neo.proto.messages.{HealthCheckServiceGrpc, PingRequest, PingResponse}

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

final class NeoHealthCheckService @Inject() (ec: ExecutionContext)
    extends ServiceBinding
    with StrictLogging {
  private object service extends HealthCheckServiceGrpc.HealthCheckService {
    override def ping(request: PingRequest): Future[PingResponse] = {
      logger.info("Got ping request")
      Future.successful(PingResponse("PONG"))
    }
  }

  override def binding(): ServerServiceDefinition = {
    logger.info("Binding health check service")
    HealthCheckServiceGrpc.bindService(service, ec)
  }
}
