package com.jobo.component

import cats.Monad
import com.jobo.common.AppPostgresProfile.api._
import com.jobo.model._
import com.jobo.sql.Members
import slick.dbio.DBIO
import slick.lifted.TableQuery
import pl.iterators.kebs._
import pl.iterators.kebs.tagged._

import scala.concurrent.ExecutionContext
import scala.language.higherKinds

class PgMemberPersister[M[_] : Monad]() extends MemberPersister[DBIO] {
  private val members = TableQuery[Members]
  import com.jobo.MemberOps._

  def upsert(member: Member)(implicit ec: ExecutionContext): DBIO[Unit] = {
    members.insertOrUpdate(member.toRow).map(_ => ())
  }

  def find(memberId: MemberId)(implicit ec: ExecutionContext): DBIO[Option[Member]] = {
    members.filter(_.id === memberId).result.map(_.headOption.map(_.toMember))
  }

  def list()(implicit ec: ExecutionContext): DBIO[Seq[Member]] = {
    members.sortBy(_.createdAt).result.map(_.map(_.toMember))
  }

  def delete(memberId: MemberId)(implicit ec: ExecutionContext): DBIO[Unit] = {
    members.filter(_.id === memberId).result.map(_ => ())
  }
}
