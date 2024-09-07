package service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import storage.{Client, ClientRepository}

@Component
class ClientService @Autowired()(val repo: ClientRepository) {

  def save(name: String): Client =
    repo.save(Client(name=name))
}
