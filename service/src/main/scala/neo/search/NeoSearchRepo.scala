package neo.search

import neo.{NeoSearchResult, QueryString}

import scala.concurrent.Future

trait NeoSearchRepo {
  def insert(neoSearchResult: NeoSearchResult): Unit

  def search(
      query: QueryString
  ): Future[Seq[NeoSearchResult]]
}
