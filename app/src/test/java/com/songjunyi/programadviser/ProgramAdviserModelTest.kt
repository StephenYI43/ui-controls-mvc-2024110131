package com.songjunyi.programadviser

import org.junit.Assert.assertEquals
import org.junit.Test

class ProgramAdviserModelTest {
    private val model = ProgramAdviserModel()

    @Test fun everyDirectionReturnsItsOwnAdvice() {
        assertEquals(R.string.advice_android, model.recommendationFor(ProgramAdviserModel.Direction.ANDROID))
        assertEquals(R.string.advice_web, model.recommendationFor(ProgramAdviserModel.Direction.WEB))
        assertEquals(R.string.advice_data, model.recommendationFor(ProgramAdviserModel.Direction.DATA))
        assertEquals(R.string.advice_ai, model.recommendationFor(ProgramAdviserModel.Direction.AI))
    }
}
