package neo.client

import io.grpc.{ManagedChannel, ManagedChannelBuilder}
import neo.proto.messages.PingRequest

object HealthCheckClient extends App {
  val channel: ManagedChannel =
    ManagedChannelBuilder.forAddress("localhost", 3333).usePlaintext().build()

  val ping = PingRequest("PING")

  val stub = neo.proto.messages.HealthCheckServiceGrpc.blockingStub(channel)

  val response = stub.ping(ping)
  println(response)
}
