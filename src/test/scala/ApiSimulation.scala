import io.gatling.core.Predef._
import io.gatling.core.body.StringBody
import io.gatling.core.structure.{ChainBuilder, PopulationBuilder, ScenarioBuilder}
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import scenarios.ListingScenarios

import scala.concurrent.duration._

class ApiSimulation extends Simulation {

  val API_URL: String = System.getProperty("api_url", "http://localhost:8080")
  val DURATION: Int = 60
  val LOAD_FACTOR: Double = 4
  var RAMP_UP_TIME: Int = 1
  val RAMP_USER_PER_SEC = 0.1


  private val httpConf =
    http.baseURL(API_URL)
//      .proxy(Proxy("localhost", 8888))
      .acceptHeader("application/json")
//      .authorizationHeader(fetchToken)
//      .acceptEncodingHeader("gzip, deflate")
      .acceptLanguageHeader("en-US,en;q=0.5")
      .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  private def withDefaults(scenario: ScenarioBuilder) =
    scenario
      .inject(rampUsersPerSec(RAMP_USER_PER_SEC) to LOAD_FACTOR during (RAMP_UP_TIME seconds),
              constantUsersPerSec(LOAD_FACTOR) during (DURATION seconds))
      .protocols(httpConf)

  private def fetchToken: String = {
    //session
    scn.
    return "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJPaWtLQWZxSFkyeHBaVzUwU1VSQ2MybDRpMk55WldGMGFXOXVWR2x0WlZ3eU1ERTNMVEF6TFRJMFZERTRPalUzT2pRM0xqYzFNeXN3TVRvd01Qcz0uYWI2NGViNTZhZDA0NGY3ZTFkNTYzMzZiZGNkNGE3MzRlNTBkODBiNCIsImlhdCI6MTQ5MDM3ODI2N30=.cak2s59LALdX28o9EUFdnvuh6sWXFThMMAvsfvRMGFY="
  }


      val scn = scenario("Authenticate")
        .exec(
          http("Authenticate")
            .post("/authenticate")
            .body(StringBody("""{"client_id": "six", "client_secret" : "sixsixsix"}"""))
            .asJSON
            .check(status.is(200))
            .check(jsonPath("$..access_token").find.saveAs("authToken"))
        )

  before(
    scn.inject(atOnceUsers(1)).protocols(httpConf)
  )

  setUp(
    ListingScenarios.listingQuery.inject(atOnceUsers(4)).protocols(httpConf)
    //withDefaults(ListingScenarios.listingQuery)
  )

}



