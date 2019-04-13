package com.jobo.component

import com.jobo.model._

import scala.concurrent.ExecutionContext
import scala.language.higherKinds

trait MemberPersister[M[_]] {
  def upsert(member: Member)(implicit ec: ExecutionContext): M[Unit]
  def find(memberId: MemberId)(implicit ec: ExecutionContext): M[Option[Member]]
  def list()(implicit ec: ExecutionContext): M[Seq[Member]]
  def delete(memberId: MemberId)(implicit ec: ExecutionContext): M[Unit]
}

