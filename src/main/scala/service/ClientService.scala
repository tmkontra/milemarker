package service

import jakarta.persistence.EntityNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import storage.{Client, ClientRepository}
import zio.*

import java.io.IOException
import java.util.Optional
import scala.jdk.CollectionConverters.*
import scala.jdk.javaapi.OptionConverters.*

@Component
class ClientService @Autowired()(val repo: ClientRepository) {

  def save(client: Client): Client =
    repo.save(Client(client.name))
}

object ClientService {
  def findAll(): ZIO[ClientRepository, IOException, List[Client]] =
    for {
      repo <- ZIO.service[ClientRepository]
      list <- ZIO.attemptBlockingIO(repo.findAll())
    } yield list.asScala.toList

  def create(c: Client): ZIO[ClientRepository, Throwable, Client] =
    for {
      repo <- ZIO.service[ClientRepository]
      saved <- ZIO.attemptBlockingIO(repo.save(c.noId))
      found <- ZIO.attemptBlockingIO(repo.findById(saved.id))
      client <- ZIO.fromOption(toScala(found)).orElseFail(EntityNotFoundException("missing!"))
    } yield client
}