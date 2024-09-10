package service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import storage.{Deliverable, DeliverableRepository}
import zio.{ZEnvironment, ZIO}

import scala.jdk.CollectionConverters.*


object DeliverableService {
  def listAll(): ZIO[DeliverableRepository, Nothing, List[Deliverable]] = for {
    l <- DeliverableRepository.findAll()
  } yield l

  def create(deliverable: Deliverable): ZIO[DeliverableRepository, Throwable, Deliverable] = for {
    saved <- DeliverableRepository.save(deliverable)
    d <- DeliverableRepository.findById(saved.id)
  } yield d

}
