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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    HeadlineText(
                        string = getString(
                            R.string.times_tables_question_title,
                            state.chosenNumber
                        )
                    )

                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth().padding(16.dp)
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

