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
  }

  "Application" should {
    "allow email submission" in new WithBrowser {

      browser.goTo("http://localhost:" + port)
      val signUpButton: FluentWebElement = browser.findFirst("#submit")
      signUpButton.click()
      browser.pageSource() must contain("email address registered")
    }
  }
}
