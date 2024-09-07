package storage

import jakarta.persistence.*
import org.springframework.data.repository.CrudRepository

@Entity
class Client(var name: String) {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Long = 0L

  def noId: Client = {
    this.id = 0L
    this
  }
}

trait ClientRepository extends CrudRepository[Client, Long] {
}
