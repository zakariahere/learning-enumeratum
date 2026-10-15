package lesson1

// ---------------------------------------------------------------------------
// Way #1: scala.Enumeration (the built-in, "old-school" way)
// ---------------------------------------------------------------------------
object ColorEnum extends Enumeration {
  type ColorEnum = Value
  val Red, Green, Blue = Value
}

// ---------------------------------------------------------------------------
// Way #2: sealed trait + case objects (the idiomatic ADT way)
// ---------------------------------------------------------------------------
sealed trait Color
object Color {
  case object Red   extends Color
  case object Green extends Color
  case object Blue  extends Color

  // 😩 You must maintain this list BY HAND. Add `Yellow` and forget it here?
  //    The compiler won't say a word.
  val values: List[Color] = List(Red, Green, Blue)

  // 😩 And you write string parsing yourself, every single time.
  def fromString(s: String): Option[Color] = values.find(_.toString == s)
}

object PlainScalaDemo extends App {
  import ColorEnum._

  // --- Enumeration pain points ---
  def describeEnum(c: ColorEnum): String = c match {
    case Red   => "warm"
    case Green => "natural"
    // Blue is missing -> Scala 2.13 DOES warn here (older 2.12 did not), but only a warning,
    // and it is weaker than sealed traits (no data per value, Value is one shared type).
  }
  println(s"Enumeration values: ${ColorEnum.values}")
  println(s"withName: ${ColorEnum.withName("Red")}")   // throws NoSuchElementException on bad input
  println(describeEnum(Red))

  // --- sealed trait: exhaustive matching works ---
  def describe(c: Color): String = c match {
    case Color.Red   => "warm"
    case Color.Green => "natural"
    case Color.Blue  => "cool"
  }
  println(describe(Color.Blue))
  println(s"sealed fromString: ${Color.fromString("Green")}")
}
