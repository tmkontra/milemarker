package run

import http.*
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.{ComponentScan, Import}
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import run.Application.Environment
import service.{ClientService, DeliverableService}
import storage.{Client, ClientRepository, DeliverableRepository}
import zio.*
import zio.Console.*
import zio.http.Server

import java.io.IOException
import scala.jdk.CollectionConverters.*

@SpringBootApplication
@ComponentScan(basePackageClasses = Array(classOf[ClientService], classOf[ClientRepository]))
@EnableJpaRepositories(basePackageClasses = Array(classOf[ClientRepository]))
@EntityScan(basePackageClasses = Array(classOf[Client]))
class Application {}

object Application extends ZIOAppDefault:
  override def run: ZIO[Environment & ZIOAppArgs & Scope, Any, Any] =
    ZIO.scoped {
      for {
        appArgs <- getArgs
        ctx <- managedSpringApp(appArgs.toList)
        _ <- printLine("milemarker!")
        s = ctx.getBean(classOf[ClientRepository])
        d = ctx.getBean(classOf[DeliverableRepository])
        _ <- Server.serve(MilemarkerServer())
          .provide(
            ZLayer.succeed(Server.Config.default.port(8000)) >>> Server.live ++
              ZLayer.succeed(s) ++
              ZLayer.succeed(d))
      } yield ()
    }

  private def managedSpringApp(args: List[String]): ZIO[Scope, IOException, ConfigurableApplicationContext] =
    ZIO.acquireRelease(
      ZIO.attemptBlockingIO {
        SpringApplication.run(classOf[Application], args: _*)
      }
    )(closeApp)

  private def closeApp(ctx: ConfigurableApplicationContext): UIO[Unit] =
    ZIO.attemptBlockingIO {
      if (ctx.isActive)
        SpringApplication.exit(ctx)
    }.orDie
