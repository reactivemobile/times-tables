package com.reactivemobile.timestables.ui.tables

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.reactivemobile.timestables.ui.tables.TimesTablesViewModel.TimesTableState
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

const val TOTAL = 10

class TimesTablesViewModel @Inject constructor(
    state: SavedStateHandle
) : ContainerHost<TimesTableState, Nothing>, ViewModel() {

    private val chosenNumber: Int? = state.get("chosenNumber")

    override val container = container<TimesTableState, Nothing>(
        TimesTableState(chosenNumber ?: 0)
    )

    fun answer(answer: String) {
        intent {
            reduce {
                val correct = if(answer.isBlank()) false else (answer.toInt() == state.currentQuestion * state.chosenNumber)

                state.copy(
                    chosenNumber = state.chosenNumber,
                    current = state.current + 1,
                    currentQuestion = state.current+2,
                    results = List(TOTAL) {
                        if (it == state.current) {
                            correct
                        } else {
                            state.results[it]
                        }
                    },
                    resultsString = createResultsString(state.results),
                    inProgress = state.current < TOTAL - 1,
                    correctCount = if(correct) state.correctCount+1 else state.correctCount
                )
            }
        }
    }

    private fun createResultsString(results: List<Boolean?>): String =
        results.joinToString("") {
            it?.let { if (it) "✓" else "✘" } ?: "-"
        }

    data class TimesTableState(
        val chosenNumber: Int,
        val current: Int = 0,
        val currentQuestion: Int = 1,
        val results: List<Boolean?> = List(TOTAL) { null },
        val resultsString: String = "-".repeat(TOTAL),
        var inProgress: Boolean = true,
        val correctCount: Int = 0,
        val totalCount: Int = TOTAL
    )
}