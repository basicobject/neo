package neo.search

import neo.{NeoSearchResult, QueryString}

import scala.concurrent.Future

trait NameRepository {
  def search(
      name: QueryString
  ): Future[Seq[NeoSearchResult]]
}
