package models

import slick.driver.H2Driver.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * User: Michael Reitgruber
  * Date: 07.02.2016
  * Time: 17:35
  */

object WaifuMapping{
  def apply(db: Database) = {
    val waifus = TableQuery[Waifus]
    Await.result(db.run(DBIO.seq(
      waifus.schema.create,
      waifus += Waifu("Misaka", "Mikoto", 6.1, 8.2),
      waifus += Waifu("Onodera", "Kosaki", 9.1, 3.3)
    )), Duration.Inf)
  }
}

case class Waifu(surname: String, firstname: String, cuteness: Double, strength: Double, id: Option[Int] = None)

class Waifus(tag: Tag) extends Table[Waifu](tag, "WAIFUS") {
  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def surname = column[String]("WAIFU_SURNAME")
  def firstname = column[String]("WAIFU_FIRSTNAME")
  def cuteness = column[Double]("CUTENESS")
  def strength = column[Double]("STRENGTH")
  def * = (surname, firstname, cuteness, strength, id.?) <> ((Waifu.apply _).tupled, Waifu.unapply)
}
