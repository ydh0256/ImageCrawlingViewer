package com.ydh.example.kakaointerview.ui.main

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.ydh.example.kakaointerview.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Testing for [MainActivity]
 * 메뉴의 설정 변경이 잘 반영되는지 테스트
 */

@RunWith(AndroidJUnit4::class)
class MainActivityScreenTest {
    @Rule
    @JvmField var mainActivityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun openMenu_changeToList() {
        Espresso.openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext())
        Espresso.onView(ViewMatchers.withText(R.string.action_list)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.imageList))
            .check(
                ViewAssertions.matches(
                    RecyclerViewTestMatcher.withLayoutManager(
                        LinearLayoutManager(InstrumentationRegistry.getTargetContext())
                    )
                )
            )
    }

    @Test
    fun openMenu_changeToGrid() {
        Espresso.openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext())
        Espresso.onView(ViewMatchers.withText(R.string.action_grid)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.imageList))
            .check(
                ViewAssertions.matches(
                    RecyclerViewTestMatcher.withLayoutManager(
                        StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                    )
                )
            )
    }
}

class RecyclerViewTestMatcher {
    companion object {
        fun withLayoutManager(layoutManager: RecyclerView.LayoutManager): Matcher<View> {
            return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
                override fun describeTo(description: Description?) {
                    description?.appendText("RecyclerView with layoutManager: $layoutManager")
                }

                override fun matchesSafely(item: RecyclerView?): Boolean {
                    if (layoutManager is StaggeredGridLayoutManager) {
                        return item?.layoutManager is StaggeredGridLayoutManager
                    } else if(layoutManager is LinearLayoutManager) {
                        return item?.layoutManager is LinearLayoutManager
                    }
                    return false
                }
            }

        }
    }
}