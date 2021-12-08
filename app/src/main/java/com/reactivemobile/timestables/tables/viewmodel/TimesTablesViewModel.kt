package com.reactivemobile.timestables.tables.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.reactivemobile.timestables.tables.viewmodel.TimesTablesViewModel.SideEffect
import com.reactivemobile.timestables.tables.viewmodel.TimesTablesViewModel.SideEffect.Correct
import com.reactivemobile.timestables.tables.viewmodel.TimesTablesViewModel.SideEffect.Incorrect
import com.reactivemobile.timestables.tables.viewmodel.TimesTablesViewModel.State
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

class TimesTablesViewModel @Inject constructor(
    state: SavedStateHandle
) : ContainerHost<State, SideEffect>, ViewModel() {

    private val chosenNumber: Int? = state.get("chosenNumber")

    private val total = 10

    override val container = container<State, SideEffect>(
        State(total, chosenNumber ?: 0)
    )

    fun answer(answer: String) {
        intent {
            val correct = if (answer.isBlank()) {
                false
            } else {
                (answer.toInt() == state.currentQuestion * state.chosenNumber)
            }

            postSideEffect(if (correct) Correct else Incorrect)

            reduce {
                state.copy(
                    chosenNumber = state.chosenNumber,
                    current = state.current + 1,
                    currentQuestion = state.current + 2,
                    results = List(total) {
                        if (it == state.current) {
                            correct
                        } else {
                            state.results[it]
                        }
                    },
                    resultsString = createResultsString(state.results),
                    inProgress = state.current < total - 1,
                    correctCount = if (correct) state.correctCount + 1 else state.correctCount
                )
            }
        }
    }

    private fun createResultsString(results: List<Boolean?>): String =
        results.joinToString("") {
            it?.let { if (it) "✓" else "✘" } ?: "-"
        }

    data class State(
        val totalCount: Int,
        val chosenNumber: Int,
        val current: Int = 0,
        val currentQuestion: Int = 1,
        val results: List<Boolean?> = List(totalCount) { null },
        val resultsString: String = "-".repeat(totalCount),
        var inProgress: Boolean = true,
        val correctCount: Int = 0
    )

    sealed interface SideEffect {
        object Correct : SideEffect
        object Incorrect : SideEffect
    }
}