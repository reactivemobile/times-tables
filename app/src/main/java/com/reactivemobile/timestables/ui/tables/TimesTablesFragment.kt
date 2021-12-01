package com.reactivemobile.timestables.ui.tables

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.reactivemobile.timestables.ui.theme.TimesTablesTheme

@ExperimentalFoundationApi
class TimesTablesFragment : Fragment() {
    private val viewModel by viewModels<TimesTablesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(this.requireContext()).apply {
            setContent {
                RenderBoard()
            }
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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {

                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    state.results.forEach {
                        Text(text = it?.let { if (it) "✓" else "✘" } ?: "?",
                            color = it?.let { if (it) Color.Green else Color.Red } ?: Color.Gray,
                            fontSize = 30.sp,
                            modifier = Modifier.padding(all = 2.dp))
                    }
                }

                if (state.inProgress) {
                    Row {
                        Text(
                            text = "${state.currentQuestion} x ${state.chosenNumber}",
                            fontSize = 30.sp
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
                        Text(text = "${state.correctCount} / ${state.totalCount}", fontSize = 40.sp)

                    }

                    Row {
                        Button(onClick = { findNavController().popBackStack() }) {
                            Text(text = "Finished")
                        }
                    }
                }
            }
        }
    }
}