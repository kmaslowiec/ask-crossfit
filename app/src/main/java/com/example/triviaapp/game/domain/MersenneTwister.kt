package com.example.triviaapp.game.domain

import com.example.triviaapp.common.domain.ShuffleEngine

private const val N = 624
private const val M = 397
private const val F = 1812433253
private const val U = 11
private const val S = 7
private const val B = 0x9D2C5680.toInt()
private const val T = 15
private const val C = 0xEFC60000.toInt()
private const val L = 18
private const val A = 0x9908B0DF.toInt()
private const val UPPER_MASK = -0x80000000
private const val LOWER_MASK = 0x7FFFFFFF

class MersenneTwister(seed: Int = System.currentTimeMillis().toInt()) : ShuffleEngine {
    private var mt = IntArray(N)
    private var index = N + 1

    init {
        init(seed)
    }

    override fun getRandomNumber(sumOfQuestions: Int) = nextInt(sumOfQuestions)

    private fun init(seed: Int) {
        mt[0] = seed
        for (i in 1 until N) {
            mt[i] = F * (mt[i - 1] xor (mt[i - 1] ushr 30)) + i
        }
        index = N
    }

    private fun nextInt(): Int {
        if (index >= N) {
            if (index > N) {
                throw RuntimeException("Generator was never initialized")
            }
            twist()
        }
        var y = mt[index]
        y = y xor (y ushr U)
        y = y xor (y shl S and B)
        y = y xor (y shl T and C)
        y = y xor (y ushr L)
        index++
        return y
    }

    private fun nextInt(until: Int): Int {
        val randomInt = nextInt()
        return (randomInt.toLong() and 0xFFFFFFFFL).rem(until - 0).toInt() + 0
    }

    private fun twist() {
        for (i in 0 until N) {
            val x = mt[i] and UPPER_MASK or (mt[(i + 1) % N] and LOWER_MASK)
            val xA = x ushr 1
            mt[i] = mt[(i + M) % N] xor xA
            if (x and 1 == 1) {
                mt[i] = mt[i] xor A
            }
        }
        index = 0
    }
}
