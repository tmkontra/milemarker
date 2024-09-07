package service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.{Component, Service}
import storage.ClientRepository

@Component
class ClientService @Autowired()(val repo: ClientRepository) {
  def show(): Unit =
    println(repo.show)
}
