package http

import service.ClientService
import zio.ZIO
import zio.http.*
import zio.http.codec.PathCodec
import zio.http.endpoint.Endpoint
import zio.json.{DeriveJsonCodec, JsonCodec}
import zio.schema.*

import scala.jdk.CollectionConverters.*

class ClientRoutes(val prefix: PathCodec[_]) extends Router[ClientService] {
  import ClientRoutes.*

  type Env = ClientService

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
      indexRoute.implement(_ => ZIO.service[ClientService].map(_.repo.findAll().iterator().asScala.map(Client.fromStorage).toList)),
      createRoute.implement(input => ZIO.service[ClientService].map(_.repo.save(input._2.toStorage.noId)).map(Client.fromStorage))
    )
}

object ClientRoutes {
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
