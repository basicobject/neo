package neo.indexer

import neo.search.NeoSearchRepo

trait SourceCodeIndexer {
  def buildIndex(rootDir: String, repo: NeoSearchRepo): Unit
}
