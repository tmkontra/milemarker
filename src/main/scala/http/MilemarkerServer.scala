package http

import service.{ClientService, DeliverableService}
import zio.http.{Response, Routes}
import zio.http.codec.PathCodec
import zio.http.endpoint.openapi.{OpenAPIGen, SwaggerUI}

object MilemarkerServer {
  import zio.http.codec.PathCodec._

  private val apiPrefix = "v0"

  private val routers = Seq(
    ClientRoutes(apiPrefix / "client"),
    DeliverableRoutes(apiPrefix / "deliverable")
  )

  private def endpoints = routers.flatMap(_.endpoints)

  private def openapi = OpenAPIGen.fromEndpoints(title="Milemarker API", version="0.1", endpoints)

  def apply() =
      routers.map(_.routes).reduce(_ ++ _) ++
        SwaggerUI.routes(apiPrefix / "docs", openapi)
}
