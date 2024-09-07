package http

import service.{ClientService, DeliverableService}
import zio.ZIO
import zio.http.*
import zio.http.codec.{HttpCodec, PathCodec}
import zio.http.endpoint.Endpoint

class DeliverableRoutes(val prefix: PathCodec[_]) extends Router[DeliverableService] {

  type Env = DeliverableService

  private val indexRoute =
    Endpoint(RoutePattern.GET / prefix)
      .out[String]

  def endpoints = Seq(indexRoute)

  def routes: Routes[Env, Response] =
    Routes(
      indexRoute.implementAs("deliverable!")
    )
}
