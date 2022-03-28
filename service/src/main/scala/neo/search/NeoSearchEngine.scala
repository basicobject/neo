package neo.search

import neo.{NeoServiceError, NeoSearchResult}

import scala.concurrent.Future

trait NeoSearchEngine {
  def search(
      queryString: String
  ): Future[Either[NeoServiceError, Seq[NeoSearchResult]]]
}
