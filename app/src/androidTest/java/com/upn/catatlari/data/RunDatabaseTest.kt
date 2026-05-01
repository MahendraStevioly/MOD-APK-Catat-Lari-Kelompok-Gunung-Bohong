package com.upn.catatlari.data

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.upn.catatlari.model.Run
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class RunDatabaseTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var runDao: RunDao
    private lateinit var db: RunDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, RunDatabase::class.java
        ).allowMainThreadQueries().build()
        runDao = db.runDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeRunAndReadInList() = runBlocking {
        val run = Run(
            runDate = "2024-03-20",
            runDistance = 5000,
            runDuration = 1800
        )
        runDao.insert(run)
        
        // Manual observation of LiveData
        val allRuns = runDao.getAllRuns()
        allRuns.observeForever { runs ->
            if (runs.isNotEmpty()) {
                assertEquals(runs[0].runDate, "2024-03-20")
                assertEquals(runs[0].runDistance, 5000)
            }
        }
    }
}
