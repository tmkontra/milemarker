package http

import service.ClientService
import storage.ClientRepository
import zio.ZIO
import zio.http.*
import zio.http.codec.PathCodec
import zio.http.endpoint.Endpoint
import zio.json.{DeriveJsonCodec, JsonCodec}
import zio.schema.*

import scala.jdk.CollectionConverters.*


class ClientRoutes(val prefix: PathCodec[_]) extends Router[ClientRoutes.Env] {
  import ClientRoutes.*

  private val indexRoute =
    Endpoint(RoutePattern.GET / prefix)
      .out[List[Client]]

  private val createRoute =
    Endpoint(RoutePattern.POST / prefix)
      .in[Client]
      .out[Client]

  def endpoints = Seq(indexRoute, createRoute)

  def routes: Routes[Env, Response] =
    Routes(
      indexRoute.implement(_ => ClientService.findAll().map(_.map(Client.fromStorage)).orDie),
      createRoute.implement((_, input) => ClientService.create(input.toStorage).map(Client.fromStorage).orDie)
    )
}

object ClientRoutes {
  type Env = ClientRepository

  case class Client(id: Long, name: String) {
    def toStorage: storage.Client = {
      val s = storage.Client(name = this.name)
      s.id = this.id
      s
    }
  }

  object Client {
    def fromStorage(c: storage.Client) = Client(c.id, c.name)
  }

  implicit val schema: Schema[Client] =
    DeriveSchema.gen[Client]
  implicit val codec: JsonCodec[Client] =
    DeriveJsonCodec.gen[Client]
}
