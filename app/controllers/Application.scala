package controllers

import play.api.mvc._
import play.api.templates.Html
import javax.inject.{Singleton, Named}

@Named
@Singleton
class Application extends Controller with OKResult {

  def show(page: String) = Action {
    page match {
      case "product" => getOK(page, views.html.product())
      case "people" => getOK(page, views.html.people())
      case _ => NotFound
    }
  }
}

trait OKResult {
  self: Controller =>
  def getOK(page: String, content: Html, registerMessage: Html = views.html.register()): SimpleResult = {
    Ok(views.html.index(page, content, registerMessage))
  }
}