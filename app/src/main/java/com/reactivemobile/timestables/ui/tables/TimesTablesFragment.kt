package com.reactivemobile.timestables.ui.tables

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.reactivemobile.timestables.R
import com.reactivemobile.timestables.ui.HeadlineText
import com.reactivemobile.timestables.ui.theme.TimesTablesTheme

@ExperimentalFoundationApi
class TimesTablesFragment : Fragment() {
    private val viewModel by viewModels<TimesTablesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        ComposeView(requireContext()).apply {
            setContent {
                RenderBoard()
            }
        }

    @Composable
    @Preview
    fun RenderBoard() {
        val state = viewModel.container.stateFlow.collectAsState().value

        var text by remember {
            mutableStateOf("")
        }

        TimesTablesTheme {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    DrawResultsIcons(state)

                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        DrawGrid(state = state)
                    }

                    if (state.inProgress) {
                        Row {
                            HeadlineText(
                                string = getString(
                                    R.string.times_tables_current_question,
                                    state.currentQuestion,
                                    state.chosenNumber
                                )
                            )
                        }

                        TextField(
                            value = text,
                            onValueChange = { text = it },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Go
                            ),
                            keyboardActions = KeyboardActions(
                                onGo = {
                                    viewModel.answer(text)
                                    text = ""
                                },
                            )
                        )
                    } else {
                        Row(Modifier.padding(16.dp)) {
                            HeadlineText(
                                string = getString(
                                    R.string.times_tables_results,
                                    state.correctCount,
                                    state.totalCount
                                )
                            )
                        }

                        Row {
                            Button(onClick = { findNavController().popBackStack() }) {
                                HeadlineText(stringRes = R.string.times_tables_finished)
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun ResultImage(value: Boolean?, current: Boolean) {
        val resourceId = if (current) {
            R.drawable.result_current
        } else when (value) {
            null -> R.drawable.result_neutral
            true -> R.drawable.result_correct
            false -> R.drawable.result_incorrect
        }

        val contentDescription =
            value?.let { result -> if (result) "success" else "failure" }

        Image(
            painter = painterResource(id = resourceId),
            contentDescription = contentDescription
        )
    }

    @Composable
    private fun DrawResultsIcons(state: TimesTablesViewModel.TimesTableState) =
        Row {
            state.results.forEachIndexed { index, result ->
                ResultImage(value = result, state.current == index)
            }
        }

    @Composable
    private fun DrawGrid(state: TimesTablesViewModel.TimesTableState) {
        if (state.inProgress) {
            Column {
                for (x in 0 until state.chosenNumber) {
                    Row {
                        for (y in 0 until state.currentQuestion) {
                            Image(
                                painter = painterResource(id = R.drawable.square),
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }
}

