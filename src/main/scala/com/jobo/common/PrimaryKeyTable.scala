package com.jobo.common

import slick.lifted.{PrimaryKey, Rep}
import AppPostgresProfile.api._

abstract class PrimaryKeyTable[T](tag: Tag, tableName: String) extends Table[T](tag, tableName) {
  def primaryKey(columns: Rep[_]*): PrimaryKey = PrimaryKey("primary_key", columns.map(_.toNode).toIndexedSeq)
  val primaryKey: PrimaryKey
}
