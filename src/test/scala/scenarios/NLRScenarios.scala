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
      |  event(id: 1004092377) {
      |    id
      |    name
      |    context {
      |      country {
      |        key
      |        name
      |      }
      |      league {
      |        key
      |        name
      |      }
      |      competition {
      |        key
      |        name
      |      }
      |    }
      |    betOffers {      
      |      nodes {
      |        ...BO
      |      }
      |    }
      |  }
      |}
      |
      |fragment BO on BetOffer {
      |  id
      |  name
      |  position
      |  outcomes {
      |    id
      |    name
      |  }
      |}"
      |}
    """.stripMargin.replace("\n", "")


  val liveRightNowQuery: ScenarioBuilder = scenario("NLR")
    .exec(
      http("nlr-query")
        .post("/graphql")
        .body(StringBody(query))
        .check(status.is(200))
        .check(regex(".*Maidstone.*"))
    )

}
