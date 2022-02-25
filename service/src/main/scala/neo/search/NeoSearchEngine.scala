package neo.search

import neo.{NeoServiceError, NeoName}

import scala.concurrent.Future

trait NeoSearchEngine {
  def search(
      queryString: String
  ): Future[Either[NeoServiceError, Seq[NeoName]]]
}
