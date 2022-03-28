package neo.cassandra

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.cql.Row
import com.datastax.oss.driver.api.querybuilder.QueryBuilder.{
  bindMarker,
  selectFrom
}
import neo.search.NameRepository
import neo.util.TimedExecution
import neo.{NeoSearchResult, QueryString}

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}
import scala.jdk.CollectionConverters.IterableHasAsScala
import scala.jdk.FutureConverters.CompletionStageOps

final class CassandraNameRepository @Inject() (cqlSession: CqlSession)(implicit
    ec: ExecutionContext
) extends NameRepository
    with TimedExecution {

  import CassandraNameRepository._

  private val simpleName = this.getClass.getSimpleName

  override def search(name: QueryString): Future[Seq[NeoSearchResult]] = {
    timedFuture(simpleName, "search") {
      cqlSession
        .executeAsync(SelectStatement.bind(name.underlying))
        .asScala
        .map(_.currentPage().asScala.map(toNeoNameFn))
        .map(_.toSeq)
    }
  }

  private val SelectStatement = cqlSession.prepare(SelectQuery)

  private def toNeoNameFn: Row => NeoSearchResult = row => {
    NeoSearchResult(
      name = row.getString(Schema.Name),
      context = row.getString(Schema.Context),
      path = row.getString(Schema.Path),
      language = row.getString(Schema.Language),
      lineNo = row.getInt(Schema.LineNo),
      filename = row.getString(Schema.Filename),
      extension = Option(row.getString(Schema.Filename)),
      score = row.getLong(Schema.Score)
    )
  }
}

object CassandraNameRepository {
  private val SelectQuery = selectFrom(Schema.Table)
    .columns(Schema.Name, Schema.Context, Schema.Score)
    .whereColumn(Schema.Name)
    .isEqualTo(bindMarker())
    .build()

  object Schema {
    val Table = "names"
    val Name = "name"
    val Context = "context"
    val Path = "path"
    val Language = "lang"
    val LineNo = "line_no"
    val Filename = "filename"
    val extension = "extension"
    val Score = "score"
  }
}
