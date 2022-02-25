package neo.grpc.services

import com.typesafe.scalalogging.StrictLogging
import io.grpc.ServerServiceDefinition
import neo.NeoName
import neo.grpc.ServiceBinding
import neo.proto.messages.{
  NameServiceGrpc,
  SearchRequest,
  SearchResponse,
  SearchResult
}
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
    override def search(request: SearchRequest): Future[SearchResponse] =
      searchEngine
        .search(request.query)
        .map {
          case Right(values) =>
            SearchResponse(values.map(toSearchResult))
          case Left(error) =>
            logger.error(error.message)
            SearchResponse(Seq.empty)
        }

    private def toSearchResult(result: NeoName): SearchResult = SearchResult(
      result.name,
      result.context
    )
  }

  override def binding(): ServerServiceDefinition =
    NameServiceGrpc.bindService(service, ExecutionContext.global)
}
