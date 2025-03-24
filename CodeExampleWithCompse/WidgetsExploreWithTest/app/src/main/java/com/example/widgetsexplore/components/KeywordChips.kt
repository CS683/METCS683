package com.example.widgetsexplore.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.text.isNotBlank

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KeywordChips(label:String,
                 onKeywordsChange: (List<String>) -> Unit) {
    var keywords by remember { mutableStateOf(listOf<String>()) }
    var currentKeyword by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Row (modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = currentKeyword,
                onValueChange = { currentKeyword = it },
                label = { Text("Add " + label) },
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = {
                    if (currentKeyword.isNotBlank()) {
                        keywords = keywords + currentKeyword
                        currentKeyword = ""
                        onKeywordsChange(keywords) // Notify parent about the change
                    }
                })
            {
                Text("+")
            }
        }
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(keywords) { keyword ->
                InputChip(
                    selected = false,
                    onClick = { keywords = keywords - keyword
                              onKeywordsChange(keywords)/* Handle chip click (e.g., remove keyword) */ },
                    label = { Text(keyword) },
                    modifier = Modifier.padding(end = 8.dp),
                    trailingIcon = {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "remove keyword",
                            Modifier.size(InputChipDefaults.AvatarSize)
                        )
                    },

                )
            }
        }
    }
}

@Preview
@Composable
fun KeywordChipsPreview() {
    KeywordChips("keywords", onKeywordsChange = {})
}