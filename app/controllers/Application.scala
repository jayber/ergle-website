package controllers

import javax.inject.{Named, Singleton}

import play.api.mvc._
import play.api.templates.Html

@Named
@Singleton
class Application extends Controller with OKResult {

  def show(page: String) = Action {
    page match {
      case "product" => getOK(page, views.html.productTail())
      case "people" => getOK(page, views.html.people())
      case _ => NotFound
    }
  }
}

trait OKResult {
  self: Controller =>
  def getOK(page: String, content: Html = views.html.productTail(), registerMessage: Html = views.html.register()): SimpleResult = {
    Ok(views.html.index(page, content, registerMessage))
  }
}