import io.finch.{head => _, body => _, _}
import com.twitter.finagle._
import com.twitter.finagle.http.{Request, Response}
import services.ResourceService
import scalatags.Text._
import scalatags.Text.short._
import com.twitter.util.Await


object Main extends App {

  val welcome: Endpoint[String] = get(/) {
    Ok(
      html(
        head(tags2.title("Awoo")),
        body(
          img(attrs.src := "/img/awoo.png")
        )
      ).render
  ).withContentType(Some("text/html")) }

  val api: Service[Request, Response] = (welcome :+: ResourceService()).toService

  Await.ready(Http.serve("0.0.0.0:80", api))
}
