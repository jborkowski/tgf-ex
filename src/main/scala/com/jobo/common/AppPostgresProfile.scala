package com.jobo.common

import com.github.tminglei.slickpg.{ExPostgresProfile, PgArraySupport, PgDate2Support, PgPlayJsonSupport}
import pl.iterators.kebs.Kebs
import pl.iterators.kebs.enums.KebsEnums
import pl.iterators.kebs.tagged.slick.SlickSupport

trait AppPostgresProfile extends ExPostgresProfile
    with PgArraySupport
    with PgDate2Support
    with PgPlayJsonSupport
    with Kebs
    with KebsEnums
    with SlickSupport {
  object PostgresAPI extends API with ArrayImplicits with DateTimeImplicits with JsonImplicits with Date2DateTimePlainImplicits with SlickSupport

  override protected lazy val useNativeUpsert = true

  override val api = PostgresAPI
  override val pgjson = "json"
}

object AppPostgresProfile extends AppPostgresProfile
