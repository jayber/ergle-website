package unit

import org.specs2.mutable.Specification
import services.{RegistrationServiceImpl, RegistrationService}

class RegistrationServiceSpec extends Specification {

  "RegistrationService" should {
    "save any email address" in {
      val registrationService: RegistrationService = new RegistrationServiceImpl

      registrationService.registerEmail("test")
      failure
    }
  }

}
