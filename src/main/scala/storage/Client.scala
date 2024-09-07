package storage

import jakarta.persistence.*
import org.springframework.data.repository.CrudRepository

@Entity
class Client {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Long = 0L

  var name: String = _

  def noId: Client = {
    this.id = 0L
    this
  }
}

object Client {
  def apply(name: String): Client = {
    val c = new Client()
    c.name = name
    c
  }
}

trait ClientRepository extends CrudRepository[Client, Long] {
}
