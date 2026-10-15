package lesson2

import enumeratum.values._

// ---------------------------------------------------------------------------
// Value enums: the identity is an Int / String / ... `value`, not the name.
// The macro also checks at COMPILE time that every value is unique.
// ---------------------------------------------------------------------------
sealed abstract class Priority(val value: Int) extends IntEnumEntry

object Priority extends IntEnum[Priority] {
  val values: IndexedSeq[Priority] = findValues

  case object Low    extends Priority(1)
  case object Medium extends Priority(5)
  case object High   extends Priority(10)
  // case object Urgent extends Priority(10)  // <- uncomment: compile error, duplicate value
}

sealed abstract class Country(val value: String, val label: String) extends StringEnumEntry

object Country extends StringEnum[Country] {
  val values: IndexedSeq[Country] = findValues

  case object Luxembourg extends Country("LU", "Luxembourg")
  case object France     extends Country("FR", "France")
  case object Belgium    extends Country("BE", "Belgium")
}

object ValueEnumsDemo extends App {
  println(s"Priority.withValue(5)       : ${Priority.withValue(5)}")
  println(s"Priority.withValueOpt(7)    : ${Priority.withValueOpt(7)}")
  println(s"Country.withValueOpt(\"LU\")  : ${Country.withValueOpt("LU").map(_.label)}")
  println(s"Country values              : ${Country.values.map(_.value)}")
}
