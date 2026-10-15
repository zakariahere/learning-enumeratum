package lesson2

import enumeratum._
import enumeratum.EnumEntry._

// ---------------------------------------------------------------------------
// 1) Override entryName by hand: the DB / JSON spelling differs from Scala's
// ---------------------------------------------------------------------------
sealed abstract class PaymentStatus(override val entryName: String) extends EnumEntry

object PaymentStatus extends Enum[PaymentStatus] {
  val values: IndexedSeq[PaymentStatus] = findValues

  case object Pending   extends PaymentStatus("PENDING")
  case object Settled   extends PaymentStatus("SETTLED")
  case object Cancelled extends PaymentStatus("CANCELED_BY_USER") // legacy spelling
}

// ---------------------------------------------------------------------------
// 2) Built-in naming mixins: no strings to type at all
// ---------------------------------------------------------------------------
sealed trait Channel extends EnumEntry with UpperSnakecase

object Channel extends Enum[Channel] {
  val values: IndexedSeq[Channel] = findValues

  case object MobileApp   extends Channel // MOBILE_APP
  case object WebBanking  extends Channel // WEB_BANKING
  case object BranchOffice extends Channel // BRANCH_OFFICE
}

object CustomNamesDemo extends App {
  println(s"PaymentStatus values  : ${PaymentStatus.values.map(_.entryName)}")
  println(s"withName CANCELED...  : ${PaymentStatus.withNameOption("CANCELED_BY_USER")}")
  println(s"withName 'Cancelled'  : ${PaymentStatus.withNameOption("Cancelled")}") // None! lookups use entryName
  println(s"toString vs entryName : ${PaymentStatus.Cancelled.toString} / ${PaymentStatus.Cancelled.entryName}")

  println(s"Channel entryNames    : ${Channel.values.map(_.entryName)}")
  println(s"withName MOBILE_APP   : ${Channel.withNameOption("MOBILE_APP")}")
}
