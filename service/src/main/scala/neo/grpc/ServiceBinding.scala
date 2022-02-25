package neo.grpc

import io.grpc.ServerServiceDefinition

trait ServiceBinding {
  def binding(): ServerServiceDefinition
}
