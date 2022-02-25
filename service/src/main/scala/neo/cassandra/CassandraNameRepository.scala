package neo.cassandra

import com.datastax.oss.driver.api.core.CqlSession
import neo.search.NameRepository
import neo.{NeoName, QueryString}

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

final class CassandraNameRepository @Inject() (cqlSession: CqlSession)(implicit
    ec: ExecutionContext
) extends NameRepository {
  override def search(name: QueryString): Future[Seq[NeoName]] =
    Future.successful(Seq.empty)
}
