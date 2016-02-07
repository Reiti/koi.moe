package services

import com.twitter.io.{Reader, Buf}
import io.finch._
import models.{Waifu, Waifus}
import slick.driver.H2Driver.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * User: Michael Reitgruber
  * Date: 07.02.2016
  * Time: 14:36
  */
object ResourceService {



  val waifus = TableQuery[Waifus]

  val wai: Endpoint[String] = get("waifu" / string)  { (surname: String) =>
    val db = Database.forConfig("h2mem1")
    try {
      val q = waifus.filter(_.surname === surname).map(_.firstname)
      val action = q.result.head
      val result = db.run(action)
      Ok(Await.result(result, Duration.Inf))
    }finally {
      db.close
    }
  } handle {
    case e: NoSuchElementException => NotFound(new Exception("Waifu not supported yet"))
  }
  val images: Endpoint[Buf] = get("img" / string) { (name: String) =>
    val reader: Reader = Reader.fromStream(getClass.getClassLoader.getResourceAsStream(name))
    Ok(Reader.readAll(reader)).withContentType(Some("image/png"))
  }
}
