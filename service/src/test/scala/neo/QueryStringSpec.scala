package neo

import org.scalatest.matchers.must.Matchers._
import org.scalatest.wordspec.AnyWordSpec

class QueryStringSpec extends AnyWordSpec {
  "QueryString" should {
    "builds queries from a string" in {
      val result = QueryString
        .buildQueries("my smart query")
      result.isRight mustBe true
      result.right.get mustBe a[Set[_]]
      result.right.get.size mustBe 1 // currently build a single query
    }

    "return error if the length of query is more than 255" in {
      val result = QueryString.buildQueries("my query test" * 100)
      result.isLeft mustBe true
      result mustBe Left(SearchQueryTooLong)
    }

    "return error if query string is empty" in {
      val result = QueryString.buildQueries("  ")
      result.isLeft mustBe true
      result mustBe Left(EmptyQueryString)
    }

    "return error if query string contains special characters" in {
      val result = QueryString.buildQueries(" $ ")
      result.isLeft mustBe true
      result mustBe Left(InvalidQueryString)
    }
  }
}
