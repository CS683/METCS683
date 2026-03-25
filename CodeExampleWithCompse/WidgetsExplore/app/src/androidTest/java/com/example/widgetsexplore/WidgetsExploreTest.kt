package com.example.widgetsexplore

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.widgetsexplore.ui.theme.WidgetsExploreTheme
import org.junit.Rule
import org.junit.Test

class WidgetsExploreTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testSubmitWithEmptyNameShowsError() {
        composeTestRule.setContent {
            WidgetsExploreTheme {
                WidgetsExplore(modifier = Modifier)
            }
        }

        // Click submit without entering a name
        composeTestRule.onNodeWithText("Submit").performClick()

        // Check for error message
        composeTestRule.onNodeWithText("Please enter your name").assertIsDisplayed()
    }

    @Test
    fun testSubmitWithNameAndGender() {
        composeTestRule.setContent {
            WidgetsExploreTheme {
                WidgetsExplore(modifier = Modifier)
            }
        }

        // Enter name
        composeTestRule.onNodeWithText("Name").performTextInput("Alice")

        // Select Male (assuming default is empty or something else)
        composeTestRule.onNodeWithText("Male").performClick()

        // Click submit
        composeTestRule.onNodeWithText("Submit").performClick()

        // Check if summary message contains the name and gender
        composeTestRule.onNodeWithText(" Your name is Alice \nyou are 18 year olds.\nyour info is private\nYou are a Male in USA\n Your comments: ", substring = true).assertIsDisplayed()
    }

    @Test
    fun testCheckboxAndSwitch() {
        composeTestRule.setContent {
            WidgetsExploreTheme {
                WidgetsExplore(modifier = Modifier)
            }
        }

        // Enter name
        composeTestRule.onNodeWithText("Name").performTextInput("Bob")

        // Toggle checkbox (it's true by default in code: var adult by remember { mutableStateOf(true) })
        composeTestRule.onNodeWithText("18 years old").performClick()

        // Toggle switch (it's false by default in code: var public by remember { mutableStateOf(false) })
        composeTestRule.onNodeWithText("Made to public").performClick()

        // Click submit
        composeTestRule.onNodeWithText("Submit").performClick()

        // Check summary: should say younger than 18 and record is made public
        composeTestRule.onNodeWithText("you are younger than 18 ", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("your record is made public", substring = true).assertIsDisplayed()
    }

    @Test
    fun testDropdownAndComments() {
        composeTestRule.setContent {
            WidgetsExploreTheme {
                WidgetsExplore(modifier = Modifier)
            }
        }

        // Enter name
        composeTestRule.onNodeWithText("Name").performTextInput("Charlie")

        // Enter comments
        composeTestRule.onNodeWithText("comments").performTextInput("Hello World")

        // Open dropdown (label is "country")
        composeTestRule.onNodeWithText("country").performClick()
        
        // Select China
        composeTestRule.onNodeWithText("China").performClick()

        // Click submit
        composeTestRule.onNodeWithText("Submit").performClick()

        // Check summary
        composeTestRule.onNodeWithText("In China", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Your comments: Hello World", substring = true).assertIsDisplayed()
    }
}
