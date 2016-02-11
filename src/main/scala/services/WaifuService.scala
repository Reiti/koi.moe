package services

import shapeless.{CNil, :+:}

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

  val db = Database.forConfig("h2mem1")
  def apply():Endpoint[String :+: String :+: CNil] = wai :+: waifulist


  val waifus = TableQuery[Waifus]

  val waifulist: Endpoint[String] = get("waifus") {
    val all = Await.result(db.run(waifus.result), Duration.Inf)
    Ok(
      html(
        head(tags2.title("Supported Waifus")),
        body(
          table(
            tr(td("Id"),td("Surname"),td("Firstname"),td("Cuteness"),td("Strength")),
            for(w <- all) yield {
              tr(
                td(a(attrs.href := s"/waifu/${w.surname}")( w.id)),
                td(w.surname),
                td(w.firstname),
                td(w.cuteness),
                td(w.strength)
              )
            }
          )
        )
      ).render
    ).withContentType(Some("text/html"))
  }

  val wai: Endpoint[String] = get("waifu" / string)  { (surname: String) =>
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
  } handle {
    case e: NoSuchElementException => NotFound(new Exception("Waifu not supported yet"))
  }
}
