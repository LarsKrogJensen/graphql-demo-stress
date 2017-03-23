package computerdatabase

import io.gatling.core.Predef._
import io.gatling.core.body.StringBody
import io.gatling.http.Predef._

class BasicSimulation extends Simulation {

  val query: String =
       """
         |{
         | "query": "{ listing(id: \"848\") { id }}"
         |}
       """.stripMargin

  private val httpConf = http
    .baseURL("http://localhost:8080")
    .acceptHeader("application/json")
    .authorizationHeader("Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJPaWtLQWZxSFkyeHBaVzUwU1VSQ2MybDRpMk55WldGMGFXOXVWR2x0WlZ3eU1ERTNMVEF6TFRJelZERTVPalUzT2pVeUxqSTROQ3N3TVRvd01Qcz0uNjcwMDI5ODUyMTFkNzUzYmZjNzkwOTIzMGJiYzQ2NDYwY2FjODRiZCIsImlhdCI6MTQ5MDI5NTQ3Mn0=.PWG9x7_HBIjBtfwuKdAK03jkOb8d8qA6EOBNVx3hLAs=")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  private val scn = scenario("Listings")
    .exec(
      http("request_10")
        .post("/graphql")
        .body(StringBody(query)))


  setUp(scn.inject(atOnceUsers(1)).protocols(httpConf))
}
