import Config._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scenarios.MeetingScenarios


class ApiSimulation extends Simulation {


  private val httpConf =
    http.baseURL(API_URL)
      .wsBaseURL(WS_URL)
      .acceptHeader("application/json")
      //.authorizationHeader(fetchToken())
//      .acceptEncodingHeader("gzip, deflate")
      .acceptLanguageHeader("en-US,en;q=0.5")
      .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")


  setUp(
    //    SubscriptionScenarios.betOfferSubscription.injectDefaults
    MeetingScenarios.meetingsQuery.injectDefaults
    //    ListingScenarios.listingQuery.injectDefaults
  ).protocols(httpConf)

}



