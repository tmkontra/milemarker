package storage

import jakarta.annotation.Nonnull
import jakarta.persistence.*

@Entity
class Client {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Long = 0L

  @Nonnull
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


