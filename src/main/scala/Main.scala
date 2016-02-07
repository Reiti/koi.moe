import io.finch._
import com.twitter.finagle.Http

object Main extends App {
  val api: Endpoint[String] = get("hello") { Ok("Hello, World!") }

  Http.serve("0.0.0.0:8080", api.toService)
}
