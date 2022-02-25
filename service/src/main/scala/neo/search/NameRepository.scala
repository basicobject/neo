package neo.search

import neo.{NeoName, QueryString}

import scala.concurrent.Future

trait NameRepository {
  def search(
      name: QueryString
  ): Future[Seq[NeoName]]
}
