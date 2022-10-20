package edu.bu.projectportal

import androidx.test.espresso.DataInteraction
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import edu.bu.projectportal.adapter.MyProjListRecyclerViewAdapter
import junit.framework.Assert.assertEquals
import org.hamcrest.CoreMatchers.anything
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.regex.Pattern.matches

@RunWith(AndroidJUnit4ClassRunner::class)
class UINavigationTest {
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)
    val curPos = 1
    val curProj = Project.projects[curPos]
    val newTitle = "connect1"

    fun checkRecyclerView(){
        onView(ViewMatchers.withId(R.id.projlist))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

//    fun checkRecyclerViewTitle(curPos:Int, title:String){
//        onData(instanceOf(Project::class.java))
//            .inAdapterView(withId(R.id.projlist))
//            .atPosition(curPos)
//            .onChildView(withId(R.id.projTitleinCard))
//            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
//
//     //       .check(matches((withText(title))))
//
//    }


    fun selectProject(pos:Int){
        onView(ViewMatchers.withId(R.id.projlist))
            .perform(actionOnItemAtPosition<MyProjListRecyclerViewAdapter.ViewHolder>
                (curPos, ViewActions.click()))

    }

    fun clickEdit(){
        onView(ViewMatchers.withId(R.id.editProj))
            .perform(ViewActions.click())
    }

    fun checkNavigateToDetailFragment(title:String, desc:String){
        onView(ViewMatchers.withId(R.id.detailFragment))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        onView(ViewMatchers.withId(R.id.projTitle))
            .check(ViewAssertions.matches(ViewMatchers.withText(title)))

        onView(ViewMatchers.withId(R.id.projDesc))
            .check(ViewAssertions.matches(ViewMatchers.withText(desc)))
    }

    fun checkNavigateToEditFragment(title:String, desc:String){
        onView(ViewMatchers.withId(R.id.editFragment))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        onView(ViewMatchers.withId(R.id.projTitleEdit))
            .check(ViewAssertions.matches(ViewMatchers.withText(title)))

        onView(ViewMatchers.withId(R.id.projDescEdit))
            .check(ViewAssertions.matches(ViewMatchers.withText(desc)))
    }

    fun editSubmit(newTitle: String, newDesc:String, submit:Boolean) {
        onView(ViewMatchers.withId(R.id.editFragment))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        onView(ViewMatchers.withId(R.id.projTitleEdit))
            .perform(ViewActions.replaceText(newTitle))

        onView(ViewMatchers.withId(R.id.projDescEdit))
            .perform(ViewActions.replaceText(newDesc))

        if (submit) {
            onView(ViewMatchers.withId(R.id.submit))
                .perform(ViewActions.click())
        } else {
            onView(ViewMatchers.withId(R.id.cancel))
                .perform(ViewActions.click())
        }
    }

    @Test
    fun testShowRecyclerView() {
        checkRecyclerView()
    }
    @Test
    fun testSelectProject() {
        selectProject(curPos)
        val curProj = Project.projects[curPos]
        checkNavigateToDetailFragment(curProj.title, curProj.description)
        pressBack()
        checkRecyclerView()
    }

    @Test
    fun testEdit() {
        selectProject(curPos)
        val curProj = Project.projects[curPos]
        checkNavigateToDetailFragment(curProj.title, curProj.description)
        clickEdit()
        checkNavigateToEditFragment(curProj.title, curProj.description)
        pressBack()
        checkNavigateToDetailFragment(curProj.title, curProj.description)
        //pressBack()
        checkRecyclerView()
    }

    @Test
    fun testEditProject() {
        selectProject(curPos)
        val curTitle = Project.projects[curPos].title
        val curDesc = Project.projects[curPos].description
        checkNavigateToDetailFragment(curTitle, curDesc)
        clickEdit()
        checkNavigateToEditFragment(curTitle, curDesc)
        val newTitle = "newTitle"
        val newDesc = "newDesc"
        editSubmit(newTitle, newDesc,false)
        checkNavigateToDetailFragment(curTitle, curDesc)
        clickEdit()
        checkNavigateToEditFragment(curTitle, curDesc)
        editSubmit(newTitle, newDesc,true)
        checkNavigateToDetailFragment(newTitle, newDesc)
        pressBack()
        checkRecyclerView()
        assertEquals(Project.projects[curPos].title, newTitle)
        assertEquals(Project.projects[curPos].description, newDesc)

    }
}
