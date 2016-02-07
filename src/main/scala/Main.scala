import com.twitter.io.{Reader, Buf}
import io.finch.{head => _, body => _, _}
import com.twitter.finagle._
import com.twitter.finagle.http.{Request, Response}
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


  val resources: Endpoint[Buf] = get("img" / string) { (name: String) =>
      val reader: Reader = Reader.fromStream(getClass.getClassLoader.getResourceAsStream(name))
      Ok(Reader.readAll(reader)).withContentType(Some("image/png"))
  }

  val api: Service[Request, Response] = (welcome :+: resources).toService

  Await.ready(Http.serve("0.0.0.0:80", api))
}
