package neo.grpc.services

import com.typesafe.scalalogging.StrictLogging
import io.grpc.ServerServiceDefinition
import io.grpc.stub.StreamObserver
import neo.NeoSearchResult
import neo.grpc.ServiceBinding
import neo.proto.messages.{NameServiceGrpc, SearchRequest, SearchResult}
import neo.search.NeoSearchEngine

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

final class NeoNameSearchService @Inject() (
    searchEngine: NeoSearchEngine
)(implicit
    ec: ExecutionContext
) extends ServiceBinding
    with StrictLogging {

  object service extends NameServiceGrpc.NameService {

    override def search(
        request: SearchRequest,
        responseObserver: StreamObserver[SearchResult]
    ): Unit = {
      def toSearchResult(result: NeoSearchResult) =
        SearchResult(result.name, result.context)

      searchEngine
        .search(request.query)
        .map {
          case Right(values) =>
            values.map(toSearchResult)
          case Left(error) =>
            logger.error(error.message)
            Seq.empty
        }
        .map(_.foreach(responseObserver.onNext))
        .onComplete(_ => responseObserver.onCompleted())
    }

  }

  override def binding(): ServerServiceDefinition =
    NameServiceGrpc.bindService(service, ExecutionContext.global)
}
