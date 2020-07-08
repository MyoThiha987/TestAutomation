package com.zg.burgerjoint.uitests.burgerlists

import android.content.Intent
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.zg.burgerjoint.activities.MainActivity
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class AddToCartTest {

    private val activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp(){
        activityTestRule.launchActivity(Intent())
    }




}