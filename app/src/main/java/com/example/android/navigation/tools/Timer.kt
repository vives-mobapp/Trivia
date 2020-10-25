/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigation.tools

import android.os.Handler
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.*
import androidx.navigation.NavController

/**
 * This is a class representing a timer that you can start or stop. The secondsCount outputs a count of
 * how many seconds since it started, every one second.
 *
 * -----
 *
 * Handler and Runnable are beyond the scope of this lesson. This is in part because they deal with
 * threading, which is a complex topic that will be covered in a later lesson.
 *
 * If you want to learn more now, you can take a look on the Android Developer documentation on
 * threading:
 *
 * https://developer.android.com/guide/components/processes-and-threads
 *
 */
class Timer(private val start: Int) {

    private var handler = Handler()
    private var isRunning = false

    var secondsCount = MutableLiveData<Int>()
    lateinit var timerRunnable : Runnable

    init {
        this.timerRunnable = Runnable {
            secondsCount.value = secondsCount.value?.minus(1)
            Log.i("Timer","Timer is at : $secondsCount.value")
            handler.postDelayed(timerRunnable, 1000)
        }
        secondsCount.value = start
    }

    fun startTimer() {
        secondsCount.value = start
        resumeTimer()
    }

    fun stopTimer() {
        isRunning = false
        handler.removeCallbacks(timerRunnable)
    }

    fun resumeTimer() {
        if (!isRunning) {
            handler.postDelayed(timerRunnable, 1000)
        }
        isRunning = true
    }
}