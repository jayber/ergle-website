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

    "show unsubscription form" in new WithBrowser {

      browser.goTo(s"http://localhost:$port/unsubscribe")

      val emailField: FluentWebElement = browser.findFirst("#emailField")
      val signUpButton: FluentWebElement = browser.findFirst("#submit")
      browser.pageSource() must contain("Please enter email address to unsubscribe") and
        (emailField must not).beNull and
        (signUpButton must not).beNull
    }

    "allow unsubscription from form" in new WithBrowser {

      val email = "test@test.com"
      browser.goTo(s"http://localhost:$port/unsubscribe")

      val emailField: FluentWebElement = browser.findFirst("#emailField")
      emailField.text(email)
      val signUpButton: FluentWebElement = browser.findFirst("#submit")
      signUpButton.click()

      browser.pageSource() must contain(s"email address $email has been unsubscribed")
    }

    "handle empty unsubscription" in new WithBrowser {

      val email = "test@test.com"
      browser.goTo(s"http://localhost:$port/unsubscribe")

      val firstSignUpButton: FluentWebElement = browser.findFirst("#submit")
      firstSignUpButton.click()

      val emailField: FluentWebElement = browser.findFirst("#emailField")
      val signUpButton: FluentWebElement = browser.findFirst("#submit")
      browser.pageSource() must contain("Please enter email address to unsubscribe") and
        (emailField must not).beNull and
        (signUpButton must not).beNull
    }

    "allow direct unsubscription" in new WithBrowser {

      val email = "test@test.com"
      browser.goTo(s"http://localhost:$port/unsubscribe?email=$email")
      browser.pageSource() must contain(s"email address $email has been unsubscribed")
    }
  }
}
