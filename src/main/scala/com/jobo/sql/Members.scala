package com.jobo.sql

import java.time.Instant

import com.jobo.model._
import com.jobo.common.AppPostgresProfile.api._
import com.jobo.common.PrimaryKeyTable
import slick.lifted.{PrimaryKey, Rep, Tag}
import pl.iterators.kebs._
import pl.iterators.kebs.tagged._

case class MemberRow(
  id: MemberId,
  email: EmailAddress,
  firstName: Option[FirstName] = None,
  lastName: Option[LastName] = None,
  createdAt: Instant
)

class Members(tag: Tag) extends PrimaryKeyTable[MemberRow](tag, "members") {

  override val primaryKey: PrimaryKey = primaryKey(id)

  def id: Rep[MemberId]                 = column[MemberId]("id")
  def email: Rep[EmailAddress]          = column[EmailAddress]("email")
  def createdAt: Rep[Instant]           = column[Instant]("created_at")
  def firstName: Rep[Option[FirstName]] = column[Option[FirstName]]("first_name")
  def lastName: Rep[Option[LastName]]   = column[Option[LastName]]("last_name")

  override def * = (id, email, firstName, lastName, createdAt) <> ((MemberRow.apply _).tupled, MemberRow.unapply)
}