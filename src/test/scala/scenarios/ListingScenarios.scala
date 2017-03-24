package scenarios

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._


object ListingScenarios {
  private val equityTsids = csv("tsids-equity.csv").random
  private val query =
    """
      |{
      | "query": "{ listing(id: \"${tsid}\") { id }}"
      |}
    """.stripMargin

  val listingQuery: ScenarioBuilder = scenario("Listings")
    .feed(equityTsids)
    .exec(
      http("listing-query")
        .post("/graphql")
        .body(StringBody(query))
        .check(status.is(200))
        .check(regex("listing"))
    )

}
