package controllers

import play.api.mvc.{Controller, Action}
import javax.inject.{Singleton, Named}
import javax.annotation.Resource
import services.RegistrationService

@Named
@Singleton
class RegistrationController extends Controller with OKResult {

  @Resource
  var dataService: RegistrationService = null

  def register(email: String) = Action {

    dataService.registerEmail(email)

    val page = "product"
    getOK(page, views.html.product(), views.html.registered())
  }
}
