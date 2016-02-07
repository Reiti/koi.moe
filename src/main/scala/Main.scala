import io.finch.{head => _, body => _, _}
import com.twitter.finagle.Http


import scalatags.Text._
import scalatags.Text.short._


object Main extends App {
  val api: Endpoint[String] = get(/) {
    Ok(
      html(
        head(tags2.title("Awoo")),
        body(
          img(attrs.src := "https://i.imgur.com/JJ0S1Wm.png")
        )
      ).render
  ).withContentType(Some("text/html")) }

  Http.serve("0.0.0.0:80", api.toService)
}
