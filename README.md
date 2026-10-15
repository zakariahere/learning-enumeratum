# Learning enumeratum

Companion code for the **Learning enumeratum** series on [blog.zakaria.lu](https://blog.zakaria.lu/topics).
Every lesson is a small, runnable sbt package. I'm learning [enumeratum](https://github.com/lloydmeta/enumeratum) for work, and I compare it with what plain Scala gives you.

- Scala 2.13.18, sbt 1.11.7, enumeratum 1.9.0

## Lessons

| # | Package | Topic |
|---|---------|-------|
| 1 | `lesson1` | `scala.Enumeration` vs sealed trait + case objects vs enumeratum |
| 2 | `lesson2` | Custom `entryName`, naming mixins, enums with fields, `IntEnum` / `StringEnum` |

## Run

```bash
sbt "runMain lesson1.PlainScalaDemo" "runMain lesson1.EnumeratumDemo"
sbt "runMain lesson2.CustomNamesDemo" "runMain lesson2.WithFieldsDemo" "runMain lesson2.ValueEnumsDemo"
```
