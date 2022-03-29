package neo.search

import neo._

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}
import scala.util.control.NonFatal

final class DefaultNeoSearchEngine @Inject() (repo: NeoSearchRepo)(implicit
    ec: ExecutionContext
) extends NeoSearchEngine {

  def search(
      queryString: String
  ): Future[Either[NeoServiceError, Seq[NeoSearchResult]]] = {

    def search(queries: Set[QueryString]) = {
      val inProgressSearches = queries.map(repo.search).toSeq
      Future
        .sequence(inProgressSearches)
        .map(results => Right(results.flatten))
        .recover { case NonFatal(e) => Left(SearchFailure) }
    }

    QueryString
      .buildQueries(queryString) match {
      case Left(error)    => Future.successful(Left(error))
      case Right(queries) => search(queries)
    }
  }
}
