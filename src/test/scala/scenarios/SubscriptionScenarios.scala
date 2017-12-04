package scenarios

import io.gatling.core.Predef._
import io.gatling.core.body.StringBody
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._


object SubscriptionScenarios {
  private val query =
    """
      |subscription BetOfferChanged {
      |  betOfferChanged {
      |    id
      |    name
      |    outcomes {
      |      id
      |      odds
      |    }
      |  }
      |}
    """.stripMargin.replace("\n", "")


  val betOfferSubscription: ScenarioBuilder = scenario("Subscription")
    .exec(ws("Connect WS").open("/subscriptions"))
    .pause(1)
    .exec(ws("Send Init")
            .sendText("""{"type": "connection_init", "payload": {}}""")
            .check(wsAwait.within(1).until(1).regex(".*connection_ack.*"))
    )
    .pause(1)
    .exec(ws("Send Subscribe")
            .sendText(s"""{"id": "1", "type": "start", "payload": {"query": "$query"}}""")
            .check(wsAwait.within(10).until(5).regex(".*betOfferChanged.*"))
    )
    .pause(10)
    .exec(ws("Send Unubscribe").sendText(s"""{"id": "1", "type": "stop"}"""))
    .pause(1)
    .exec(ws("Close WS").close)


}
