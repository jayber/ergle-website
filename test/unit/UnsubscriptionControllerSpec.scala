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

  "Unsubscription" should {
    "show unsubscription page" in new SetUp {

      val request: FakeRequest[AnyContentAsEmpty.type] = FakeRequest(GET, "/unsubscribe")
      val result = controller.unsubscribe(None)(request)

      status(result) must equalTo(OK)
      contentAsString(result) must contain("Please enter email address to unsubscribe")
      there was no(registrationService).unsubscribeEmail(email)
    }

    "allow users to unsubscribe" in new SetUp {

      val request: FakeRequest[AnyContentAsEmpty.type] = FakeRequest(GET, s"/unsubscribe?email=$email")
      val result = controller.unsubscribe(Some(email))(request)

      status(result) must equalTo(OK)
      there was one(registrationService).unsubscribeEmail(email)
    }

    "handle invalid unsubscribe" in new SetUp {

      val request: FakeRequest[AnyContentAsEmpty.type] = FakeRequest(GET, s"/unsubscribe?email=")
      val result = controller.unsubscribe(Some(email))(request)

      status(result) must equalTo(BAD_REQUEST)
      contentAsString(result) must contain("Please enter email address to unsubscribe")
    }
  }

  class SetUp extends Mockito with Scope with ThrownExpectations {
    val controller: RegistrationController = new RegistrationController
    val registrationService: RegistrationService = mock[RegistrationService]
    controller.registrationService = registrationService
  }

}
