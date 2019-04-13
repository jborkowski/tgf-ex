package com.jobo.component

import cats.Monad
import com.jobo.component.MemberService._
import com.jobo.model._
import com.softwaremill.macwire._
import pl.iterators.sealed_monad.syntax._

import scala.concurrent.ExecutionContext
import scala.language.higherKinds

class MemberService[M[_] : Monad] {
  val persister = wire[MemberPersister[M]]

  def insert(member: Member)(implicit ec: ExecutionContext): M[InsertResult] = {
    (for {
      _ <- persister.find(member.id).seal[InsertResult].ensureNot(_.isDefined, InsertResult.MemberAlreadyExists)
      _ <- persister.upsert(member).seal
    } yield InsertResult.Success(member)).run
  }

  def update(member: Member)(implicit ec: ExecutionContext): M[UpdateResult] = {
    (for {
      _ <- persister.find(member.id).seal[UpdateResult].ensure(_.isDefined, UpdateResult.MissingMemberToUpdate)
      _ <- persister.upsert(member).seal
    } yield UpdateResult.Success(member)).run
  }

  def delete(memberId: MemberId)(implicit ec: ExecutionContext): M[DeleteResult] = {
    (for {
      _ <- persister.find(memberId).seal[DeleteResult].ensureNot(_.isDefined, DeleteResult.MissingMemberToDelete)
      _ <- persister.delete(memberId).seal
    } yield DeleteResult.Success).run
  }
}

object MemberService {
  sealed abstract class InsertResult
  object InsertResult {
    case class Success(message: Member) extends InsertResult
    case object MemberAlreadyExists     extends InsertResult
  }

  sealed abstract class UpdateResult
  object UpdateResult {
    case class Success(message: Member) extends UpdateResult
    case object MissingMemberToUpdate   extends UpdateResult
  }

  sealed abstract class DeleteResult
  object DeleteResult {
    case object Success               extends DeleteResult
    case object MissingMemberToDelete extends DeleteResult
  }
}