package http

import service.ClientService
import zio.ZIO
import zio.http.*
import zio.http.codec.{HttpCodec, PathCodec}
import zio.http.endpoint.Endpoint

class ClientRoutes(val prefix: PathCodec[_]) extends Router[ClientService] {

  type Env = ClientService

  private val indexRoute =
    Endpoint(RoutePattern.GET / prefix)
      .out[String]

  def endpoints = Seq(indexRoute)

  def routes: Routes[Env, Response] =
    Routes(
      indexRoute.implement(_ => ZIO.service[ClientService].map(_.show()))
    )
}
