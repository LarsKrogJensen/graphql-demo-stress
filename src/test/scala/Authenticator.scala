import scala.util.parsing.json.JSON
import scalaj.http.{Http, HttpRequest}


object Authenticator {

  def fetchToken(url: String): String = {
    val request: HttpRequest = Http(url + "/authenticate")
      .postData("""{"client_id": "xxxx", "client_secret" : "xxxxx"}""")

    val result = JSON.parseFull(request.asString.body)
    result match {
      case Some(map: Map[String, Any]) => "Bearer " + map("access_token").asInstanceOf[String]
      case None => throw new RuntimeException("Invalid auth respnse")
      case _ => throw new RuntimeException("Invalid auth respnse")
    }

  }
}

