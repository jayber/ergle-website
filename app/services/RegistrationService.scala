package services

import javax.inject.{Singleton, Named}

abstract class RegistrationService {
  def registerEmail(email: String)
}

@Named
@Singleton
class RegistrationServiceImpl extends RegistrationService {

  def registerEmail(email: String) {
    println("saved something")
  }
}
