package services

import javax.inject.{Inject, Singleton, Named}
import scala.concurrent.ExecutionContext.Implicits.global

abstract class RegistrationService {
  def registerEmail(email: String)

  def unsubscribeEmail(email: String)
}

@Named
@Singleton
class RegistrationServiceImpl extends RegistrationService {

  @Inject
  var dataStore: DataStore = null

  def registerEmail(email: String) {
    val emailLower: String = email.toLowerCase
    dataStore.find(emailLower).map {
      case None => dataStore.save(emailLower)
    }
  }

  def unsubscribeEmail(email: String) {
    val emailLower: String = email.toLowerCase
    dataStore.unsubscribe(emailLower)
  }
}
