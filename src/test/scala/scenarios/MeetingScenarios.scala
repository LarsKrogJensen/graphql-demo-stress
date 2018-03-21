package scenarios

import io.gatling.core.Predef._
import io.gatling.core.body.StringBody
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._


object MeetingScenarios {
  private val q = """{"query":"query {\n\tmeetings (offeringId: \"888\", sport: \"horse_racing\") {\n\t\tnodes {\n\t\t\tid\n\t\t}\n\t}\n}"}""""
  private val query =
    """
      |{
      | "query": "query {
      |    	meetings () {
      |		    nodes {
      |			    id
      |		    }
      |	    }
      |   }"
      |}
    """.stripMargin.replace("\n", "")


  val meetingsQuery: ScenarioBuilder = scenario("Meetings")
    .exec(
      http("meetings-query")
        .post("/statistics/v2018/graphql")
        .body(StringBody(q))
        .check(status.is(200))
        .check(regex(".*meetings.*"))
    )

}
