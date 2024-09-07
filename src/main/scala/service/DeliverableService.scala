package service

trait DeliverableService {}

object DeliverableService {
  def live = new DeliverableService {}
}
