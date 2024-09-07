package storage

import jakarta.persistence._
import org.springframework.data.repository.CrudRepository

@Entity
class Client {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  val id = 0L
  val name: String = null
}

trait ClientRepository extends CrudRepository[Client, Long] {
  def show: String = "ClientRepository"
}
