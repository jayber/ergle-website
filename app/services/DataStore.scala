package services

import javax.inject.{Named, Singleton}

import play.api.Logger
import play.api.libs.json.{JsObject, Json}
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

abstract class DataStore {
  def unsubscribe(email: String)

  def find(email: String): Future[Option[JsObject]]

  def save(email: String, message: String = ""): Unit
}

@Named
@Singleton
class DataStoreImpl extends DataStore {

  val driver = new MongoDriver
  val connection = driver.connection(List("localhost"))
  val db = connection("ergle")

  def collection: JSONCollection = db.collection[JSONCollection]("user")

  def save(email: String, message: String) {
    val json = Json.obj(
      "message" -> message,
      "email" -> email,
      "subscribed" -> true,
      "created" -> new java.util.Date().getTime)

    collection.insert(json).map(lastError =>
      Logger.debug("Mongo LastError: %s".format(lastError)))
  }

  def find(email: String) = {
    collection.find(Json.obj("email" -> email)).one[JsObject]
  }

  def unsubscribe(email: String) {
    collection.update(Json.obj("email" -> email), Json.obj("$set" -> Json.obj("subscribed" -> false)))
  }
}
