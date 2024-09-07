package http

import zio.http.Routes
import zio.http.codec.PathCodec
import zio.http.endpoint.openapi.{OpenAPIGen, SwaggerUI}

object MilemarkerServer {
  import zio.http.codec.PathCodec._

  private val apiPrefix = "v0"

  val routers = Seq(
    ClientRoutes(apiPrefix / "client"),
    DeliverableRoutes(apiPrefix / "deliverable")
  )

  def endpoints = routers.flatMap(_.endpoints)

  def openapi = OpenAPIGen.fromEndpoints(title="Milemarker API", version="0.1", endpoints)

  def apply() =
      routers.map(_.routes).reduce(_ ++ _) ++
        SwaggerUI.routes(apiPrefix / "docs", openapi)
}
