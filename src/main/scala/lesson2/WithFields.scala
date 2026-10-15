package lesson2

import enumeratum._

// Enum entries are plain classes, so they can carry data and behaviour.
sealed abstract class Currency(val symbol: String, val decimals: Int) extends EnumEntry {
  def format(minorUnits: Long): String =
    symbol + BigDecimal(minorUnits, decimals).toString // BigDecimal(unscaled, scale)
}

object Currency extends Enum[Currency] {
  val values: IndexedSeq[Currency] = findValues

  case object EUR extends Currency("€", 2)
  case object USD extends Currency("$", 2)
  case object JPY extends Currency("¥", 0)

  // Derived lookups are one-liners because `values` is always complete.
  val bySymbol: Map[String, Currency] = values.map(c => c.symbol -> c).toMap
}

object WithFieldsDemo extends App {
  println(Currency.EUR.format(123456)) // €1234.56
  println(Currency.JPY.format(5000))   // ¥5000
  println(s"bySymbol(\"$$\") : ${Currency.bySymbol.get("$")}")
}
