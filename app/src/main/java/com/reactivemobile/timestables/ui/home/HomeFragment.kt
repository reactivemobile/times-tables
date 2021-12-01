package com.reactivemobile.timestables.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.reactivemobile.timestables.ui.theme.TimesTablesTheme

@ExperimentalFoundationApi
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(this.requireContext()).apply {
            setContent {
                Setup()
            }
        }
    }

    @Composable
    @Preview
    private fun Setup() {
        TimesTablesTheme {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "Times Tables",
                    fontSize = 30.sp,
                    modifier = Modifier.padding(all = 8.dp)
                )
                Text(text = "Choose a number", modifier = Modifier.padding(all = 8.dp))

                LazyVerticalGrid(
                    cells = GridCells.Fixed(3),
                    Modifier.padding(all = 8.dp)
                )
                {
                    items(9) { number ->
                        val buttonValue = number + 1
                        Button(
                            onClick = {
                                findNavController().navigate(
                                    HomeFragmentDirections.actionHomeFragmentToTimesTablesFragment(
                                        buttonValue
                                    )
                                )
                            },
                            Modifier.padding(all = 8.dp)
                        )
                        {
                            Text(buttonValue.toString())
                        }
                    }
                }
            }
        }
    }
}