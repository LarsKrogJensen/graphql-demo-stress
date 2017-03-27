import io.gatling.core.Predef._
import io.gatling.core.body.StringBody
import io.gatling.core.structure.{ChainBuilder, PopulationBuilder, ScenarioBuilder}
import io.gatling.http.Predef._

import scalaj.http._
import scenarios.ListingScenarios

import scala.concurrent.duration._
import scala.util.parsing.json.{JSON, JSONObject}

class ApiSimulation extends Simulation {

  val API_URL: String = System.getProperty("api_url", "http://localhost:8080")
  val DURATION: Int = 60
  val LOAD_FACTOR: Double = 200
  var RAMP_UP_TIME: Int = 1
  val RAMP_USER_PER_SEC = 0.1

  var authToken: String = Authenticator.fetchToken(API_URL)

  private val httpConf =
    http.baseURL(API_URL)
//      .proxy(Proxy("localhost", 8888))
      .acceptHeader("application/json")
      .authorizationHeader(authToken)
      .acceptEncodingHeader("gzip, deflate")
      .acceptLanguageHeader("en-US,en;q=0.5")
      .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  private def withDefaults(scenario: ScenarioBuilder) =
    scenario
      .inject(rampUsersPerSec(RAMP_USER_PER_SEC) to LOAD_FACTOR during (RAMP_UP_TIME seconds),
              constantUsersPerSec(LOAD_FACTOR) during (DURATION seconds))
      .protocols(httpConf)


  setUp(
    withDefaults(ListingScenarios.listingQuery)
  )

}



