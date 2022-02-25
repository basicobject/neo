package neo.cassandra

import com.datastax.oss.driver.api.core.CqlSession
import com.typesafe.scalalogging.StrictLogging

import javax.inject.{Provider, Singleton}

@Singleton
class CqlSessionProvider extends Provider[CqlSession] with StrictLogging {
  lazy val session: CqlSession = CqlSession
    .builder()
    .withKeyspace("neo")
    .build()

  sys.addShutdownHook {
    logger.info("Closing database connection")
    session.close()
  }

  override def get(): CqlSession = session
}
