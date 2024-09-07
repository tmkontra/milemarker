package http

import zio.http.{Response, Routes}
import zio.http.endpoint.Endpoint

trait Router[-Env] {
  def endpoints: Seq[Endpoint[_, _, _, _, _]]
  def routes: Routes[Env, Response]
}
