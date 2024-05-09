package net.wandroid.quantummovie.presentation.movie_screen.test_util

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry


private fun getContext(): Context = InstrumentationRegistry.getInstrumentation().getTargetContext()
fun getString(id: Int): String = getContext().getString(id)