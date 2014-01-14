package unit

import org.specs2.mutable.Specification
import services.{DataStore, RegistrationServiceImpl}
import org.specs2.mock.Mockito
import org.specs2.specification.Scope
import scala.concurrent.Future
import play.api.libs.json.Json
import scala.concurrent.ExecutionContext.Implicits.global

class RegistrationServiceSpec extends Specification with Mockito {

  val email: String = "test"

  "RegistrationService" should {
    "save any new email address" in new SetUp {
      dataStore.find(anyString) returns Future(None)

      registrationService.registerEmail(email)

      there was one(dataStore).find(email) andThen one(dataStore).save(email)
    }

    "lower case email address" in new SetUp {
      dataStore.find(anyString) returns Future(None)

      registrationService.registerEmail(email.toUpperCase)

      there was one(dataStore).find(email.toLowerCase) andThen one(dataStore).save(email.toLowerCase)
    }

    "NOT save any existing email address" in new SetUp {

      dataStore.find(email) returns Future(Some(Json.obj(
        "email" -> email,
        "created" -> new java.util.Date().getTime)))

      registrationService.registerEmail(email)

      there was no(dataStore).save(email)
    }

    "unsubscribe email" in new SetUp {

      registrationService.unsubscribeEmail(email)

      there was one(dataStore).unsubscribe(email)

    }
  }

  trait SetUp extends Scope {
    val registrationService: RegistrationServiceImpl = new RegistrationServiceImpl
    val dataStore: DataStore = mock[DataStore]
    registrationService.dataStore = dataStore
  }

}



