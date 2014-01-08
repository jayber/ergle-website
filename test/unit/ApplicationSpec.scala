package unit

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

class ApplicationSpec extends Specification {

  "Application" should {

    "send 404 on a bad request" in new WithApplication {
      status(route(FakeRequest(GET, "/boum")).get) must equalTo(NOT_FOUND)
    }

    "render the index page" in new WithApplication {
      val home = route(FakeRequest(GET, "/")).get

      status(home) must equalTo(OK)
      contentType(home) must beSome.which(_ == "text/html")
      contentAsString(home) must contain("ergle - product")
    }
  }
}
