package service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import storage.{Client, ClientRepository}

@Component
class ClientService @Autowired()(val repo: ClientRepository) {

  def save(client: Client): Client =
    repo.save(Client(client.name))
}
