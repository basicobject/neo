package neo

final class QueryString private (val underlying: String) extends AnyVal

object QueryString {
  def buildQueries(str: String): Either[NeoServiceError, Set[QueryString]] = {
    val cleanedString = cleanWhiteSpaces(str)

    if (cleanedString.isEmpty) Left(EmptyQueryString)
    else if (cleanedString.length > MaxLength) Left(SearchQueryTooLong)
    else if (!cleanedString.forall(c => c.isLetterOrDigit || c.isSpaceChar))
      Left(InvalidQueryString)
    else Right(Set(new QueryString(cleanedString)))
  }

  private val cleanWhiteSpaces: String => String = (str) =>
    str.trim().replaceAll("\\s+", " ")

  val MaxLength = 255
}
