package controllers

import javax.inject.{Inject, Named, Singleton}

import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}
import services.RegistrationService

@Named
@Singleton
class RegistrationController extends Controller with OKResult {

  @Inject
  var registrationService: RegistrationService = null

  val page = "product"

  val registerForm = Form(
    tuple(
      "email" -> email,
      "message" -> text(0, 2000)
    )
  )

  val unsubscribeForm = Form(
    single(
      "email" -> email
    )
  )

  def register() = Action {
    implicit request =>

      registerForm.bindFromRequest().fold(
        formWithErrors => {
          BadRequest(views.html.index(page, views.html.productTail(), views.html.register()))
        },
        userData => {
          registrationService.registerEmail(userData._1, userData._2)
          getOK(page, views.html.productTail(), views.html.registered())
        })
  }

  def unsubscribe(emailOpt: Option[String]) = Action {
    implicit request =>

      emailOpt match {
        case None =>
          getOK("", views.html.unsubscribe())
        case Some(value) =>
          unsubscribeForm.bindFromRequest().fold(
            formWithErrors => {
              BadRequest(views.html.index("", views.html.unsubscribe(formWithErrors.errors.mkString), views.html.register()))
            },
            email => {
              registrationService.unsubscribeEmail(email)
              getOK("", views.html.unsubscribed(email))
            })
      }
  }
}
