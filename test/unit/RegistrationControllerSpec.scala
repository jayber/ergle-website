package unit

import play.api.test.{PlaySpecification, FakeRequest}
import controllers.RegistrationController
import org.specs2.mock.Mockito
import services.RegistrationService
import org.specs2.specification.Scope
import org.specs2.matcher.ThrownExpectations
import play.api.mvc.AnyContentAsEmpty

class RegistrationControllerSpec extends PlaySpecification with Mockito {

  val email: String = "test@test.com"

  "Registration" should {
    "allow users to register" in new SetUp {

      val request: FakeRequest[AnyContentAsEmpty.type] = FakeRequest(GET, s"/register?email=$email")
      val result = controller.register()(request)

      status(result) must equalTo(OK)
      there was one(registrationService).registerEmail(email)
    }

    "validate email address" in new SetUp {
      val invalidEmail = "test"
      val request: FakeRequest[AnyContentAsEmpty.type] = FakeRequest(GET, s"/register?email=$invalidEmail")

      val result = controller.register()(request)

      status(result) must equalTo(BAD_REQUEST)
      there was no(registrationService).registerEmail(invalidEmail)
    }
  }

  class SetUp extends Mockito with Scope with ThrownExpectations {

    val controller: RegistrationController = new RegistrationController

    val registrationService: RegistrationService = mock[RegistrationService]

    controller.registrationService = registrationService

  }

}
