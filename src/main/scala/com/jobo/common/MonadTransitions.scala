package com.jobo.common

import cats.{Id, ~>}
import slick.dbio.DBIO
import com.jobo.common.AppPostgresProfile.api._
import scala.concurrent.Future
import scala.util.Try
import slick.dbio


class MonadTransitions {
  trait FromFutureTransitions {
    implicit val futureToDbIo: Future ~> DBIO = new (Future ~> DBIO) {
      override def apply[A](f: Future[A]): DBIO[A] = DBIO.from(f)
    }
  }

  trait ToFutureTransitions {
    implicit val idToFuture: Id ~> Future = new (Id ~> Future) {
      override def apply[A](fa: Id[A]): Future[A] = Future.fromTry(Try(fa))
    }

    implicit val futureToFuture: Future ~> Future = new (Future ~> Future) {
      override def apply[A](fa: Future[A]): Future[A] = fa
    }
  }

  object DbFutureTransitionsInstance {
    implicit def instance(db: Database): DBIO ~> Future = new (DBIO ~> Future) {
      ???
    }
    implicit def instance(db: Database): DBIO ~> Future = new (DBIO ~> Future) {
      ???
    }
  }

  object FutureToDBIOTransitionsInstance extends FromFutureTransitions {
    implicit def instance(): Future ~> DBIO = futureToDbIo
  }

  object FutureToFutureTransitions extends ToFutureTransitions {
    implicit def instance(): Future ~> Future = futureToFuture
  }
}
