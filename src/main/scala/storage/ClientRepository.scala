package storage

import org.springframework.data.repository.CrudRepository

trait ClientRepository extends CrudRepository[Client, Long] {
}
