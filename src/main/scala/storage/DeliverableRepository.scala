package storage

import org.springframework.data.repository.CrudRepository
import zio.*
import scala.jdk.CollectionConverters._

trait DeliverableRepository extends CrudRepository[Deliverable, Long] {
}

object DeliverableRepository {
  def save(d: Deliverable): URIO[DeliverableRepository, Deliverable] =
    ZIO.serviceWith[DeliverableRepository](_.save(d))
    
  def findById(id: Long): RIO[DeliverableRepository, Deliverable] =
    for {
      found <- ZIO.serviceWith[DeliverableRepository](_.findById(id))
      result <- ZIO.attempt(found.orElseThrow())
    } yield result
    
  def findAll(): URIO[DeliverableRepository, List[Deliverable]] =
    ZIO.serviceWith[DeliverableRepository](_.findAll()).map(_.asScala.toList)
}