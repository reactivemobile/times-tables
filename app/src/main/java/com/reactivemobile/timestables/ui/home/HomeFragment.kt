package com.reactivemobile.timestables.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.reactivemobile.timestables.R
import com.reactivemobile.timestables.ui.BodyText
import com.reactivemobile.timestables.ui.HeadlineText
import com.reactivemobile.timestables.ui.theme.Blue200
import com.reactivemobile.timestables.ui.theme.Orange200
import com.reactivemobile.timestables.ui.theme.TimesTablesTheme

@ExperimentalFoundationApi
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            RenderHomePage()
        }
    }

    @Composable
    @Preview
    private fun RenderHomePage() =
        TimesTablesTheme {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
                ) {
                HeadlineText(R.string.home_title)

                    BodyText(stringRes = R.string.home_subtitle)

                    RenderNumberChooser()

            }
            }
        }

    @Composable
    private fun RenderNumberChooser() =
        LazyVerticalGrid(
            cells = GridCells.Fixed(3), modifier =
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
                    modifier = Modifier.padding(8.dp)
                )
                {
                    HeadlineText(string = buttonValue.toString())
                }
            }
        }
}