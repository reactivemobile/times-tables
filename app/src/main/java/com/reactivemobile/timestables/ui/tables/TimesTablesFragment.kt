package com.reactivemobile.timestables.ui.tables

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.fragment.navArgs
import com.reactivemobile.timestables.ui.theme.TimesTablesTheme

class TimesTablesFragment : Fragment() {
    private val args: TimesTablesFragmentArgs by navArgs()

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
    fun Setup()
    {
        TimesTablesTheme {
            Column {
                Text("Hello World Table Fragment for ${args.chosenNumber}")
            }

            /**
             * TODO
             * - ViewModel
             * - M x N = [  ] [GO]
             * [x][o][o][o][o][x][x][x][][]
             *
             */

        }
    }
}