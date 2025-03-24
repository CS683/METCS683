package com.example.widgetsexplore.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.widgetsexplore.Profile
import com.example.widgetsexplore.R
import com.example.widgetsexplore.ui.theme.WidgetsExploreTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WidgetsExplore(modifier: Modifier) {
    val padding = 8.dp
    val countryOptions = listOf("USA", "China", "Japan", "India")
    val radioOptions = listOf("Male", "Female")

    // remember is a function to pertain state across re-composition
    // the expression after by is a delegate that should
    // implement getValue() and setValue() for the variable
    // mutableStateof() returns a mutableState object

    var name by remember { mutableStateOf("") }
    var comments by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var public by remember { mutableStateOf(false) }
    var adult by remember { mutableStateOf(true) }
    var keyWords by remember { mutableStateOf(listOf<String>()) }

    var (selectedOption, onOptionSelected) = remember { mutableStateOf("") }
    var selectedCountry by remember { mutableStateOf(countryOptions[0]) }
    var expanded by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    // make the Column layout scrollable through the modifier
    Column(verticalArrangement = Arrangement.SpaceEvenly,
        modifier =  Modifier.verticalScroll(state= scrollState,enabled = true)) {
        //Title Row
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Profile",
                fontSize = 48.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
            )
            Image(
                painter = painterResource(R.drawable.profile),
                contentDescription = "profile picture",
                modifier = Modifier
                    .size(128.dp)
                    .clip(CircleShape)
            )
        }

        Spacer(modifier = Modifier.size(16.dp))
        HorizontalDivider(modifier = modifier, thickness = 1.dp, color = Color.Black)

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = modifier
        )

        // checkbox Row
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier){
            Checkbox(
                modifier = Modifier.padding(end = padding),
                checked = adult,
                onCheckedChange = { adult = it })
            Text("18 years old",
                fontSize = 18.sp)
        }

        // Switch button Row
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier

        ) {
            Text("Made to public",
                modifier = Modifier.padding(end = padding))
            Switch(
                checked = public,
                onCheckedChange = { public = it }
            )
        }

        KeywordChips (label = "keywords",
            onKeywordsChange = {keyWords = it})

        //Radio group Row
        MyRadioGroup(radioOptions, selectedOption, onOptionSelected,
            modifier = modifier)

        // a drop-down menu for country
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }) {
            OutlinedTextField (
                modifier = Modifier.fillMaxWidth().menuAnchor(),
                readOnly = true,
                value = selectedCountry,
                onValueChange = {},
                label = { Text("country") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded)
                }
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }) {
                countryOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            selectedCountry = option
                            expanded = false})
                }
            }
        }

        // comment input
        OutlinedTextField(
            value = comments,
            modifier = modifier,
            onValueChange = { comments = it },
            label = { Text("comments") },
            maxLines = 3,
            textStyle = MaterialTheme.typography.bodyLarge
        )

        // submit button
        Button(
            onClick = {
                setProfile(name,selectedCountry,selectedOption, adult, public, comments)
                message =  if (name == "") "Please enter your name" else " Your name is ${name} \n" +
                        ( if (adult) "you are 18 year or older." else "you are younger than 18 ") + "\n" +
                        (if (public) "your record is made public"  else "your info is private") + "\n" +
                        "You are a $selectedOption in $selectedCountry\n " + "Your comments: $comments\n" +
                        "Your keywords: " +  keyWords.joinToString()

                name = ""
                comments = ""
                selectedOption = ""
                adult = true
                public = false },
            modifier = modifier
        ) {
            Text("Submit")
        }
        // display the summary message
        Text(message, modifier=modifier)
    }
}


fun setProfile(name:String, country:String,gender: String = "",
               adult:Boolean, isPublic:Boolean,
               comments: String )
{
    Profile.profile.name = name
    Profile.profile.country = country
    Profile.profile.gender = gender
    Profile.profile.adult = adult
    Profile.profile.isPublic = isPublic
    Profile.profile.comments = comments

}

@Composable
fun MyRadioGroup(
    radioOptions: List<String>,
    selectedOption: String,
    setSelected: (selected: String) -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier,
    ) {
        radioOptions.forEach { item ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedOption == item,
                    onClick = {
                        setSelected(item)
                    },
                    enabled = true
                )
                Text(text = item, modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WidgetPreview() {
    WidgetsExploreTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            WidgetsExplore(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp))

        }
    }
}