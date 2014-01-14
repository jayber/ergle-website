package unit

import org.specs2.mock.Mockito
import org.specs2.specification.Scope
import org.specs2.matcher.ThrownExpectations
import controllers.RegistrationController
import services.RegistrationService
import play.api.test.{PlaySpecification, FakeRequest}
import play.api.mvc.AnyContentAsEmpty

class UnsubscriptionControllerSpec extends PlaySpecification {

  val email: String = "test@test.com"

  "Registration" should {
    "allow users to register" in new SetUp {

      val request: FakeRequest[AnyContentAsEmpty.type] = FakeRequest(GET, s"/unsubscribe?email=$email")
      val result = controller.unsubscribe()(request)

      status(result) must equalTo(OK)
      there was one(registrationService).unsubscribeEmail(email)
    }
  }

  class SetUp extends Mockito with Scope with ThrownExpectations {

    val controller: RegistrationController = new RegistrationController

    val registrationService: RegistrationService = mock[RegistrationService]

    controller.registrationService = registrationService

  }

}
