package com.example.widgetsexplore

import android.os.Build
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.unit.dp
import androidx.test.filters.SdkSuppress
import com.example.widgetsexplore.components.WidgetsExplore
import org.junit.Rule
import org.junit.Test

@ExperimentalTestApi
@SdkSuppress(minSdkVersion = Build.VERSION_CODES.O)
class WidgetExploreTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun messageTest() {
        composeTestRule.setContent {
            WidgetsExplore(
                Modifier
                .fillMaxWidth()
                .padding(16.dp))
        }

        composeTestRule
            .onNodeWithText("Name")
            .assertExists()

        composeTestRule
            .onNodeWithText("Name")
            .performTextInput("Yuting")

        composeTestRule
            .onNodeWithText("Submit")
            .performClick()

        composeTestRule
            .onNode(hasText ("Your name is Yuting", true))
            .assertExists()



    }
}