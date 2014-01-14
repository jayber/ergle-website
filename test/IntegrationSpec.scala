import org.fluentlenium.core.domain.FluentWebElement
import org.specs2.mutable._

import play.api.test._

class IntegrationSpec extends Specification {

  "Application" should {

    "work from within a browser" in new WithBrowser {

      browser.goTo("http://localhost:" + port)

      browser.title must beEqualTo("ergle - product")

      val emailField: FluentWebElement = browser.findFirst("#emailField")
      (emailField must not).beNull
    }

    "allow email submission" in new WithBrowser {

      browser.goTo("http://localhost:" + port)
      val emailField: FluentWebElement = browser.findFirst("#emailField")
      emailField.text("test@value.com")
      val signUpButton: FluentWebElement = browser.findFirst("#submit")
      signUpButton.click()
      browser.pageSource() must contain("email address registered")
    }

    "allow unsubscription" in new WithBrowser {

      val email = "test@test.com"
      browser.goTo(s"http://localhost:$port/unsubscribe?email=$email")
      browser.pageSource() must contain(s"email address $email has been unsubscribed")
    }
  }
}
