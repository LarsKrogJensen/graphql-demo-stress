import io.gatling.core.Predef.{constantUsersPerSec, rampUsersPerSec}
import io.gatling.core.structure.{PopulationBuilder, ScenarioBuilder}

import scala.concurrent.duration._
/**
  * Created by lars on 2017-03-28.
  */
object Config {
  val API_URL: String = System.getProperty("api_url", "http://localhost:8080")
  val DURATION: Int = 60
  val LOAD_FACTOR: Double = 200
  var RAMP_UP_TIME: Int = 1
  val RAMP_USER_PER_SEC = 0.1


  implicit class Extensions(val scenario: ScenarioBuilder) extends AnyVal {
    def injectDefaults: PopulationBuilder =
      scenario.inject(rampUsersPerSec(RAMP_USER_PER_SEC) to LOAD_FACTOR during (RAMP_UP_TIME seconds),
                      constantUsersPerSec(LOAD_FACTOR) during (DURATION seconds))
  }

}
