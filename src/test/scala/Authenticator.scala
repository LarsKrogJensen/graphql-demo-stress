import scala.util.parsing.json.JSON
import scalaj.http.{Http, HttpRequest}


object Authenticator {

  def fetchToken(): String = {
    val request: HttpRequest = Http(Config.API_URL + "/authenticate")
      .postData("""{"client_id": "xxxxx", "client_secret" : "xxxx"}""")

    val result = JSON.parseFull(request.asString.body)
    result match {
      case Some(map: Map[String, Any]) => "Bearer " + map("access_token").asInstanceOf[String]
      case None => throw new RuntimeException("Invalid auth respnse")
      case _ => throw new RuntimeException("Invalid auth respnse")
    }

  }
}

