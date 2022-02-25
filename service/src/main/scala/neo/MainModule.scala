package neo

import com.datastax.oss.driver.api.core.CqlSession
import com.google.inject.AbstractModule
import neo.cassandra.{CassandraNameRepository, CqlSessionProvider}
import neo.search.{DefaultNeoSearchEngine, NameRepository, NeoSearchEngine}

import scala.concurrent.ExecutionContext

class MainModule extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[ExecutionContext]).toInstance(ExecutionContext.global)
    bind(classOf[NeoSearchEngine]).to(classOf[DefaultNeoSearchEngine])
    bind(classOf[CqlSession]).toProvider(classOf[CqlSessionProvider])
    bind(classOf[NameRepository]).to(classOf[CassandraNameRepository])
  }
}
