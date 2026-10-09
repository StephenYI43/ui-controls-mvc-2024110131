package com.songjunyi.programadviser

/** Model: no Android UI objects or Activity state are held here. */
class ProgramAdviserModel {
    enum class Direction { ANDROID, WEB, DATA, AI }

    fun recommendationFor(direction: Direction): Int = when (direction) {
        Direction.ANDROID -> R.string.advice_android
        Direction.WEB -> R.string.advice_web
        Direction.DATA -> R.string.advice_data
        Direction.AI -> R.string.advice_ai
    }
}
