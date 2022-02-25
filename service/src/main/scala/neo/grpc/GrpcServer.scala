package neo.grpc

import io.grpc.ServerServiceDefinition

trait GrpcServer {
  def bindings(): Seq[ServerServiceDefinition]
  def start(): Unit
  def stop(): Unit
}
