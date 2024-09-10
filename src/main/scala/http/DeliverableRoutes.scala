package http

import http.ClientRoutes.Client
import service.DeliverableService
import storage.DeliverableRepository
import zio.ZIO
import zio.http.*
import zio.http.codec.PathCodec
import zio.http.endpoint.Endpoint
import zio.json.{DeriveJsonCodec, JsonCodec}
import zio.schema.{DeriveSchema, Schema}

class DeliverableRoutes(val prefix: PathCodec[_]) extends Router[DeliverableRoutes.Env] {
  import DeliverableRoutes.*

  private val indexRoute =
    Endpoint(RoutePattern.GET / prefix)
      .out[List[Deliverable]]

  private val createRoute =
    Endpoint(RoutePattern.POST / prefix)
      .in[Deliverable]
      .out[Deliverable]

  def endpoints = Seq(indexRoute, createRoute)

  def routes: Routes[Env, Response] =
    Routes(
      indexRoute.implementHandler(Handler.fromZIO(DeliverableService.listAll()).map(_.map(Deliverable.fromStorage))),
      createRoute.implementHandler(Handler.fromFunctionZIO {
        (_, input: Deliverable) =>
          DeliverableService.create(input.toStorage)
            .map(Deliverable.fromStorage)
            .orDie
      })
    )
}

object DeliverableRoutes {
  type Env = DeliverableRepository

  case class Deliverable(id: Long, client: ClientRoutes.Client, name: String) {
    def toStorage: storage.Deliverable = storage.Deliverable(id, client.toStorage, name)
  }

  object Deliverable {
    def fromStorage(s: storage.Deliverable): Deliverable =
      Deliverable(s.id, Client.fromStorage(s.client), s.name)
  }

  implicit val schema: Schema[Deliverable] =
    DeriveSchema.gen[Deliverable]
  implicit val codec: JsonCodec[Deliverable] =
    DeriveJsonCodec.gen[Deliverable]
}