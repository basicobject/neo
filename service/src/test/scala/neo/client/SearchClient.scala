package neo.client

import io.grpc.{ManagedChannel, ManagedChannelBuilder}
import neo.proto.messages.{NameServiceGrpc, SearchRequest}

object SearchClient extends App {
  val channel: ManagedChannel =
    ManagedChannelBuilder
      .forAddress("localhost", 3333)
      .usePlaintext()
      .build()

  val searchClient = NameServiceGrpc.blockingStub(channel)
  val response = searchClient.search(SearchRequest(query = "Parrot"))
  if (response.results.length > 0)
    response.results.foreach(result => println(result))
  else println("No results found")

}
