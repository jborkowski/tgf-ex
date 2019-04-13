package com.jobo

import java.time.Instant

package object model extends com.jobo.model.tags {
  final case class Member(
    id: MemberId,
    email: EmailAddress,
    firstName: Option[FirstName] = None,
    lastName: Option[LastName] = None,
    createdAt: Instant
  )
}

object MemberOps {
  import com.jobo.model.Member
  import com.jobo.sql.MemberRow

  implicit class MemberToRow(m: Member) {
    def toRow: MemberRow = {
      MemberRow(
        m.id,
        m.email,
        m.firstName,
        m.lastName,
        m.createdAt
      )
    }
  }

  implicit class RowToMember(m: MemberRow) {
    def toMember: Member = {
      Member(
        m.id,
        m.email,
        m.firstName,
        m.lastName,
        m.createdAt
      )
    }
  }
}