package edu.bu.metcs.projectportallab

import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToLog
import org.junit.Rule
import org.junit.Test

@ExperimentalTestApi
class ProjectPortalTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun projectPortalTest() {
        composeTestRule.setContent {
            NavGraph()
        }
        // Log the full semantics tree
        composeTestRule.onRoot().printToLog("MY TAG")

        val myButton = SemanticsMatcher.expectValue(
            SemanticsProperties.Role, Role.Button
        )
        composeTestRule
            .onNodeWithText("Edit")
            .assertExists()

        composeTestRule
          //  .onNodeWithText("Edit")
            .onNode(myButton)
            .performClick()

        composeTestRule
            .onNode(hasText("Project Portal"), true)
            .performTextInput("Project Portal1")

        composeTestRule
            .onNodeWithText("Submit")
            .performClick()

        composeTestRule
            .onNode(hasText("Project Portal1", true))
            .assertExists()


    }
}