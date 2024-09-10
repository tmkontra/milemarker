package storage

import jakarta.annotation.Nonnull
import jakarta.persistence.*

@Entity
class Deliverable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Long = 0L

  @ManyToOne
  @Nonnull
  var client: Client = _

  @Nonnull
  var name: String = _

  def noId: Deliverable = {
    this.id = 0L
    this
  }
}

object Deliverable {
  def apply(client: Client, name: String): Deliverable = {
    val c = new Deliverable()
    c.name = name
    c.client = client
    c
  }

  def apply(id: Long, client: Client, name: String): Deliverable = {
    val c = new Deliverable()
    c.id = id
    c.name = name
    c.client = client
    c
  }
}


