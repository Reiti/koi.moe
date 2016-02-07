package services

import com.twitter.io.{Reader, Buf}
import io.finch._


/**
  * User: Michael Reitgruber
  * Date: 07.02.2016
  * Time: 14:36
  */
object ResourceService {

  def apply(): Endpoint[Buf] = images

  val images: Endpoint[Buf] = get("img" / string) { (name: String) =>
    val reader: Reader = Reader.fromStream(getClass.getClassLoader.getResourceAsStream(name))
    Ok(Reader.readAll(reader)).withContentType(Some("image/png"))
  }
}
