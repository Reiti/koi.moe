import io.finch.{head => _, body => _, _}
import com.twitter.finagle._
import com.twitter.finagle.http.{Request, Response}
import services.{WaifuService, ResourceService}
import scalatags.Text._
import scalatags.Text.short._
import com.twitter.util.Await
import models.WaifuMapping
import slick.driver.H2Driver.api._


object Main extends App {

  val db = Database.forConfig("h2mem1")
  WaifuMapping(db)
  val welcome: Endpoint[String] = get(/) {
    Ok(
      html(
        head(tags2.title("Awoo")),
        body(
          img(attrs.src := "/img/awoo.png")
        )
      ).render
  ).withContentType(Some("text/html")) }

  val api: Service[Request, Response] = (welcome :+: ResourceService() :+: WaifuService()).toService

  Await.ready(Http.serve("0.0.0.0:80", api))
  db.close()
}
