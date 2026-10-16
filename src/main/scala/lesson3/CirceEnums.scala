package lesson3

import enumeratum._
import enumeratum.EnumEntry.UpperSnakecase
import enumeratum.values._
import io.circe.generic.semiauto._
import io.circe.parser.decode
import io.circe.syntax._
import io.circe.{Decoder, Encoder}

// ---------------------------------------------------------------------------
// 1) Name-based enum: mix in CirceEnum -> Encoder + Decoder for free.
//    The JSON string is the entryName (here UPPER_SNAKE via the mixin).
// ---------------------------------------------------------------------------
sealed trait Channel extends EnumEntry with UpperSnakecase

object Channel extends Enum[Channel] with CirceEnum[Channel] {
  val values: IndexedSeq[Channel] = findValues

  case object MobileApp  extends Channel
  case object WebBanking extends Channel
}

// ---------------------------------------------------------------------------
// 2) Value enum: IntCirceEnum -> JSON is the Int value, not the name.
// ---------------------------------------------------------------------------
sealed abstract class Priority(val value: Int) extends IntEnumEntry

object Priority extends IntEnum[Priority] with IntCirceEnum[Priority] {
  val values: IndexedSeq[Priority] = findValues

  case object Low    extends Priority(1)
  case object Medium extends Priority(5)
  case object High   extends Priority(10)
}

// ---------------------------------------------------------------------------
// 3) A payload that uses both. Derivation just finds the enum codecs.
// ---------------------------------------------------------------------------
final case class Payment(id: String, channel: Channel, priority: Priority)

object Payment {
  implicit val encoder: Encoder[Payment] = deriveEncoder
  implicit val decoder: Decoder[Payment] = deriveDecoder
}

object CirceDemo extends App {
  val p = Payment("pay-42", Channel.MobileApp, Priority.High)

  // --- encode ---
  println(s"enum alone      : ${(Channel.WebBanking: Channel).asJson.noSpaces}")
  println(s"value enum alone: ${(Priority.Medium: Priority).asJson.noSpaces}")
  println(s"payload         : ${p.asJson.noSpaces}")

  // --- decode: happy path ---
  println(s"decode ok       : ${decode[Payment]("""{"id":"pay-1","channel":"WEB_BANKING","priority":5}""")}")

  // --- decode: the failures you will see in real life ---
  println(s"scala name      : ${decode[Payment]("""{"id":"pay-2","channel":"WebBanking","priority":5}""")}")
  println(s"unknown value   : ${decode[Payment]("""{"id":"pay-3","channel":"MOBILE_APP","priority":7}""")}")
  println(s"lowercase       : ${decode[Payment]("""{"id":"pay-4","channel":"mobile_app","priority":1}""")}")
}

// ---------------------------------------------------------------------------
// 4) Lenient decoding for messy upstreams: write the codec yourself with
//    the helpers in enumeratum.Circe instead of mixing in CirceEnum.
// ---------------------------------------------------------------------------
sealed trait Source extends EnumEntry with UpperSnakecase

object Source extends Enum[Source] {
  val values: IndexedSeq[Source] = findValues

  case object CoreBanking extends Source
  case object CardSystem  extends Source

  implicit val encoder: Encoder[Source] = enumeratum.Circe.encoder(this)
  implicit val decoder: Decoder[Source] = enumeratum.Circe.decodeCaseInsensitive(this)
}

object LenientDemo extends App {
  println(s"case-insensitive: ${decode[Source](""""core_banking"""")}")
  println(s"scala name      : ${decode[Source](""""CoreBanking"""")}")
  println(s"encode          : ${(Source.CardSystem: Source).asJson.noSpaces}")
}
