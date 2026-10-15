package lesson1

import enumeratum._

// Same sealed trait idea... but extend EnumEntry, and the companion extends Enum[...]
sealed trait Fruit extends EnumEntry


object Fruit extends Enum[Fruit] {

  // ✨ Macro finds every case object below at compile time. No hand-written list.
  val values: IndexedSeq[Fruit] = findValues

  case object Apple  extends Fruit
  case object Banana extends Fruit
  case object Cherry extends Fruit
}

object EnumeratumDemo extends App {
  println(s"values           : ${Fruit.values}")
  println(s"withName         : ${Fruit.withName("Apple")}")          // throws on unknown
  println(s"withNameOption   : ${Fruit.withNameOption("Mango")}")    // safe -> None
  println(s"withNameInsensitive: ${Fruit.withNameInsensitiveOption("banana")}")
  println(s"entryName        : ${Fruit.Cherry.entryName}")
  println(s"indexOf          : ${Fruit.indexOf(Fruit.Banana)}")

  // Still a real sealed trait -> exhaustiveness checking for free 🎉
  def color(f: Fruit): String = f match {
    case Fruit.Apple  => "red"
    case Fruit.Cherry => "dark red"
    case Fruit.Banana => "yellow"
  }

  Fruit.values.foreach(f => println(s"$f is ${color(f)}"))
}
