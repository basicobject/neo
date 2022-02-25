package neo

import neo.QueryString.MaxLength

sealed trait NeoServiceError {
  def message: String
}

case object SearchQueryTooLong extends NeoServiceError {
  override def message: String =
    s"Search query length is > $MaxLength"
}

case object InvalidQueryString extends NeoServiceError {
  override def message: String =
    "Query string should not contain any special characters"
}

case object EmptyQueryString extends NeoServiceError {
  override def message: String = "Query string can't be empty"
}

case object SearchFailure extends NeoServiceError {
  override def message: String = "Search failed"
}
