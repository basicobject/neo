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
  val results = searchClient.search(SearchRequest(query = "kiwi")).toSeq
  if (results.nonEmpty)
    results.foreach(result => println(result))
  else println("No results found")

}
