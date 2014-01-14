package controllers

import play.api.mvc.{Controller, Action}
import javax.inject.{Inject, Singleton, Named}
import play.api.data.Forms._
import services.RegistrationService
import play.api.data.Form
import play.api.templates.Html

@Named
@Singleton
class RegistrationController extends Controller with OKResult {

  @Inject
  var registrationService: RegistrationService = null

  val page = "product"

  val emailForm = Form(
    single(
      "email" -> email
    )
  )

  def register() = Action {
    implicit request =>

      emailForm.bindFromRequest().fold(
        formWithErrors => {
          BadRequest(views.html.index(page, views.html.product(), views.html.register()))
        },
        userData => {
          registrationService.registerEmail(userData)
          getOK(page, views.html.product(), views.html.registered())
        })
  }

  def unsubscribe() = Action {
    implicit request =>

      emailForm.bindFromRequest().fold(
        formWithErrors => {
          BadRequest(views.html.index(page, views.html.product(), views.html.register()))
        },
        userData => {
          registrationService.unsubscribeEmail(userData)
          getOK(page, views.html.unsubscribed(userData), Html(""))
        })
  }
}
