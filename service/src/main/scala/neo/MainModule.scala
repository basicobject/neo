package neo

import com.datastax.oss.driver.api.core.CqlSession
import com.google.inject.AbstractModule
import neo.cassandra.{CassandraSearchRepository, CqlSessionProvider}
import neo.search.{DefaultNeoSearchEngine, NeoSearchRepo, NeoSearchEngine}

import scala.concurrent.ExecutionContext

class MainModule extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[ExecutionContext]).toInstance(ExecutionContext.global)
    bind(classOf[NeoSearchEngine]).to(classOf[DefaultNeoSearchEngine])
    bind(classOf[CqlSession]).toProvider(classOf[CqlSessionProvider])
    bind(classOf[NeoSearchRepo]).to(classOf[CassandraSearchRepository])
  }
}
