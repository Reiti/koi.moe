package services

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import io.finch._
import slick.lifted.TableQuery

import models.Waifus
import slick.driver.H2Driver.api._

import scalatags.Text._
import scalatags.Text.tags.{head, body}
import scalatags.Text.short._

/**
  * User: Michael Reitgruber
  * Date: 07.02.2016
  * Time: 18:51
  */
object WaifuService {

  def apply():Endpoint[String] = wai

  val waifus = TableQuery[Waifus]


  val wai: Endpoint[String] = get("waifu" / string)  { (surname: String) =>
    val db = Database.forConfig("h2mem1")
    try {
      val q = waifus.filter(_.surname === surname).map(_.firstname)
      val action = q.result.head
      val result = db.run(action)
      val firstname = Await.result(result, Duration.Inf)
      Ok(
        html(
          head(tags2.title(s"$surname, $firstname")),
          body(
            img(attrs.src := s"/img/${surname.toLowerCase}-${firstname.toLowerCase}.png")
          )
        ).render
      ).withContentType(Some("text/html"))
    }finally {
      db.close
    }
  } handle {
    case e: NoSuchElementException => NotFound(new Exception("Waifu not supported yet"))
  }
}
