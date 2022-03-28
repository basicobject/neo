package neo

final case class NeoSearchResult(
    name: String,
    context: String,
    path: String,
    language: String,
    lineNo: Int,
    filename: String,
    extension: Option[String],
    score: Long
)
