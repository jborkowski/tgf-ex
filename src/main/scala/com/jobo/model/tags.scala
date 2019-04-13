package com.jobo.model

import java.util.UUID

import pl.iterators.kebs.tag.meta.tagged
import pl.iterators.kebs.tagged._

@tagged
trait tags {
  trait MemberIdTag
  trait EmailAddressTag
  trait FirstNameTag
  trait LastNameTag

  type MemberId = UUID @@ MemberIdTag
  type EmailAddress = String @@ EmailAddressTag
  type FirstName = String @@ FirstNameTag
  type LastName = String @@ LastNameTag
}