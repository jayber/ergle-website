package services

import javax.inject.{Inject, Named, Singleton}

import scala.concurrent.ExecutionContext.Implicits.global

abstract class RegistrationService {
  def registerEmail(email: String, message: String = "")

  def unsubscribeEmail(email: String)
}

@Named
@Singleton
class RegistrationServiceImpl extends RegistrationService {

  @Inject
  var dataStore: DataStore = null

  def registerEmail(email: String, message: String) {
    val emailLower: String = email.toLowerCase
    dataStore.find(emailLower).map {
      case None => dataStore.save(emailLower, message)
      case _ => // do nothing
    }
  }

  def unsubscribeEmail(email: String) {
    val emailLower: String = email.toLowerCase
    dataStore.unsubscribe(emailLower)
  }
}
