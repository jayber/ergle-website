package controllers

import play.api.mvc._
import play.api.templates.Html

object Application extends Controller {

  def index(page: String) = Action {
    page match {
      case "product" => getOK(page, views.html.product())
      case "people" => getOK(page, views.html.people())
      case _ => NotFound
    }
  }


  def getOK(page: String, content: Html): SimpleResult = {
    Ok(views.html.index(page, content))
  }
}