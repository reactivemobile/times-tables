package com.reactivemobile.timestables.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.reactivemobile.timestables.R
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

            Column {
                Text(text = "Choose a number", Modifier.padding(all = Dp(2f)))

                LazyVerticalGrid(
                    cells = GridCells.Fixed(3),
                    Modifier.padding(all = Dp(2f))
                )
                {
                    items(9) { number ->
                        val buttonValue = number + 1
                        Button(
                            onClick = { findNavController().navigate(
                                HomeFragmentDirections.actionHomeFragmentToTimesTablesFragment(buttonValue)
                            ) },
                            Modifier.padding(all = Dp(2f))
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