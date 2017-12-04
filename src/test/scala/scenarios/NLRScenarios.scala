package scenarios

import io.gatling.core.Predef._
import io.gatling.core.body.StringBody
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._


object NLRScenarios {
  private val query =
    """
      |{
      | "query": "query {
      |   liveRightNow(first: 1) {
      |     range {
      |       start
      |       size
      |       total
      |     }
      |     events {
      |       id
      |       name
      |       mainBetOffer {
      |         id
      |         outcomes {id name}
      |       }
      |     }
      |   }
      |  }"
      |}
    """.stripMargin.replace("\n", "")


  val liveRightNowQuery: ScenarioBuilder = scenario("NLR")
    .exec(
      http("nlr-query")
        .post("/graphql")
        .body(StringBody(query))
        .check(status.is(200))
        .check(regex(".*liveRightNow.*"))
    )

}
