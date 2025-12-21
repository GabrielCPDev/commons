package commons

@JvmInline
value class Document private constructor(val value: String) {

    val isCPF: Boolean get() = value.length == 11
    val isCNPJ: Boolean get() = value.length == 14

    fun formatted(): String {
        return if (isCPF) formatCPF(value) else formatCNPJ(value)
    }

    companion object {

        fun of(raw: String): Document {
            val digits = raw.filter { it.isDigit() }

            require(digits.length == 11 || digits.length == 14) {
                "Invalid document length"
            }
            require(!digits.all { it == digits.first() }) {
                "Invalid document"
            }

            if (digits.length == 11) {
                require(isValidCPF(digits)) { "Invalid CPF" }
            } else {
                require(isValidCNPJ(digits)) { "Invalid CNPJ" }
            }

            return Document(digits)
        }

        private fun isValidCPF(v: String): Boolean {
            val dv1 = calcDigit(v.substring(0, 9), intArrayOf(10,9,8,7,6,5,4,3,2))
            val dv2 = calcDigit(v.substring(0, 10), intArrayOf(11,10,9,8,7,6,5,4,3,2))

            return v[9].digitToInt() == dv1 &&
                    v[10].digitToInt() == dv2
        }

        private fun isValidCNPJ(v: String): Boolean {
            val w1 = intArrayOf(5,4,3,2,9,8,7,6,5,4,3,2)
            val w2 = intArrayOf(6,5,4,3,2,9,8,7,6,5,4,3,2)

            val dv1 = calcDigit(v.substring(0, 12), w1)
            val dv2 = calcDigit(v.substring(0, 13), w2)

            return v[12].digitToInt() == dv1 &&
                    v[13].digitToInt() == dv2
        }

        private fun calcDigit(nums: String, weights: IntArray): Int {
            val sum = nums.mapIndexed { i, c ->
                c.digitToInt() * weights[i]
            }.sum()
            val mod = sum % 11
            return if (mod < 2) 0 else 11 - mod
        }

        private fun formatCPF(v: String): String =
            "${v.substring(0, 3)}.${v.substring(3, 6)}.${v.substring(6, 9)}-${v.substring(9, 11)}"

        private fun formatCNPJ(v: String): String =
            "${v.substring(0, 2)}.${v.substring(2, 5)}.${v.substring(5, 8)}/${v.substring(8, 12)}-${v.substring(12, 14)}"
    }
}