package edu.bu.projectportal

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class ProjectEditNavigationTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)
    val orgTitle = "Weather Forecast"
    val orgDescription = "Weather Forcast is an app ..."

    val title = "project portal1"
    val description = "this is project portal1"

    @Test
    fun testEditFragmentNavigation() {
        //click edit button
        onView(withId(R.id.editProj)).perform(click())

        // verify navigate to edit fragment
        onView(withId(R.id.editFragment))
            .check(matches(isDisplayed()))

        pressBack()

        // verify navigate back to detail fragment
        onView(withId(R.id.detailFragment))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testCancelEditProject() {
        //click edit button
        onView(withId(R.id.editProj)).perform(click())
        //modify project title
        onView(withId(R.id.projTitleEdit)).perform(replaceText(title))
        // modify project description
        onView(withId(R.id.projDescEdit)).perform(replaceText(description))
        // click cancel button
        onView(withId(R.id.cancel)).perform(click())

        //verify information changed in detail page
        onView(withId(R.id.detailFragment))
            .check(matches(isDisplayed()))

        onView(withId(R.id.projTitle))
            .check(matches(withText(orgTitle)))

        onView(withId(R.id.projDesc))
            .check(matches(withText(orgDescription)))

    }

    @Test
    fun testEditProject() {
        //click edit button
        onView(withId(R.id.editProj)).perform(click())
        //modify project title
        onView(withId(R.id.projTitleEdit)).perform(replaceText(title))
        // modify project description
        onView(withId(R.id.projDescEdit)).perform(replaceText(description))
        // click submit button
        onView(withId(R.id.submit)).perform(click())

        //verify information changed in detail page
        onView(withId(R.id.detailFragment))
            .check(matches(isDisplayed()))
        onView(withId(R.id.projTitle))
            .check(matches(withText(title)))
        onView(withId(R.id.projDesc))
            .check(matches(withText(description)))
    }

}