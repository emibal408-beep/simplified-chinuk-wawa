package com.example.data

object NumbersGenerator {
    private val baseDigits = arrayOf(
        "", "ikt", "mokst", "klone", "lakkit", "kwinnum",
        "taghum", "sinnamokst", "stotekin", "kwaist"
    )

    fun getChinukNumber(n: Int): String {
        if (n == 1000) return "tahtlum takamonuk"
        if (n <= 0 || n > 1000) return ""

        var remaining = n
        val parts = mutableListOf<String>()

        val hundreds = remaining / 100
        if (hundreds > 0) {
            if (hundreds == 1) {
                parts.add("takamonuk")
            } else {
                parts.add("${baseDigits[hundreds]} takamonuk")
            }
            remaining %= 100
        }

        val tens = remaining / 10
        if (tens > 0) {
            if (tens == 1) {
                parts.add("tahtlum")
            } else {
                parts.add("${baseDigits[tens]} tahtlum")
            }
            remaining %= 10
        }

        if (remaining > 0) {
            parts.add(baseDigits[remaining])
        }

        return parts.joinToString(" pe ")
    }

    fun generateNumbers(): List<NumberEntry> {
        return (1..1000).map {
            NumberEntry(
                digit = it,
                chinuk = getChinukNumber(it)
            )
        }
    }
}

data class NumberEntry(
    val digit: Int,
    val chinuk: String
)
