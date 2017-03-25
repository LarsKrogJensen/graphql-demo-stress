package scenarios

import io.gatling.core.Predef._
import io.gatling.core.body.StringBody
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
//  val sessionHeaders = Map("Authorization" -> "Bearer ${authToken}",
//                             "Content-Type" -> "application/json")


  val listingQuery: ScenarioBuilder = scenario("Listings")
//    .exec(
//      http("Authenticate")
//        .post("/authenticate")
//        .body(StringBody("""{"client_id": "six", "client_secret" : "sixsixsix"}"""))
//        .asJSON
//        .check(status.is(200))
//        .check(jsonPath("$..access_token").find.saveAs("token"))
//    )
    .feed(equityTsids)
    .exec(
      http("listing-query")
        .post("/graphql")
        .body(StringBody(query))
        .header("authorization", "Bearer ${authToken}")
        .check(status.is(200))
        .check(regex("listing"))
    )

}
