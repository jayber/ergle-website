package unit

import org.specs2.mutable.Specification
import play.api.test.FakeRequest
import controllers.RegistrationController
import org.specs2.mock.Mockito
import services.RegistrationService

class RegistrationSpec extends Specification with Mockito {

  "Registration" should {
    "allow users to register" in {

      val controller: RegistrationController = new RegistrationController

      val dataService: RegistrationService = mock[RegistrationService]

      controller.dataService = dataService

      controller.register("test").apply(FakeRequest())

      there was one(dataService).registerEmail("test")

    }
  }
}
