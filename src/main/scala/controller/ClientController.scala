package controller

import service.ClientService
import zio.ZIO
import zio.http.*
import zio.http.codec.{HttpCodec, PathCodec}
import zio.http.endpoint.Endpoint

object ClientController {

  type Env = ClientService

  val indexRoute = Endpoint(RoutePattern.GET).out[String]

  def apply(): Routes[Env, Nothing] =
    Routes(
      indexRoute.implement(_ => ZIO.service[ClientService].map(_.show()))
    )
}
