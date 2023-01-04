package com.example.tvguide

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.tvguide.api.TelevisionService
import com.example.tvguide.database.TVDao
import com.example.tvguide.database.TVDatabase
import com.example.tvguide.model.TVResponse
import com.example.tvguide.model.TVShow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class TVShowRepositoryTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun fetchCachedTVShows() {
        val cachedTVShows = listOf(TVShow(id = 1), TVShow(id = 2))

        val dao = object : TVDao {
            override fun addTVShows(tvShows: List<TVShow>) {

            }

            override fun getTVShows(): List<TVShow> {
                return cachedTVShows
            }

        }

        val tvService: TelevisionService = mock()

        val tvDatabase: TVDatabase = mock {
            on { tvDao() } doReturn dao
        }

        val tvShowRepository = TVShowRepository(tvService, tvDatabase)

        runTest {
            tvShowRepository.fetchTVShows().test {
                assertEquals(cachedTVShows, awaitItem())
                awaitComplete()
            }
        }
    }

    @Test
    fun fetchRemoteTVShows() {
        val remoteTVShows = listOf(TVShow(id = 3), TVShow(id = 4))
        val remoteResponse = TVResponse(1, remoteTVShows)

        val emptyDao = object : TVDao {
            override fun addTVShows(tvShows: List<TVShow>) {

            }

            override fun getTVShows(): List<TVShow> = emptyList()

        }

        val tvDatabase: TVDatabase = mock {
            on { tvDao() } doReturn emptyDao
        }

        val tvService: TelevisionService = mock {
            onBlocking { getTVShows(anyString()) } doReturn remoteResponse
        }

        val tvShowRepository = TVShowRepository(tvService, tvDatabase)

        runTest {
            tvShowRepository.fetchTVShows().test {
                assertEquals(remoteTVShows, awaitItem())
                awaitComplete()
            }
        }
    }

    @Test
    fun fetchRemoteTVShowsError() {
        val exception = "TV Show Exception"

        val emptyDao = object : TVDao {
            override fun addTVShows(tvShows: List<TVShow>) {

            }

            override fun getTVShows(): List<TVShow> = emptyList()

        }

        val tvDatabase: TVDatabase = mock {
            on { tvDao() } doReturn emptyDao
        }

        val tvService: TelevisionService = mock {
            onBlocking { getTVShows(anyString()) } doThrow RuntimeException(exception)
        }

        val tvShowRepository = TVShowRepository(tvService, tvDatabase)

        runTest {
            tvShowRepository.fetchTVShows().test {
                assertEquals(exception, awaitError().message)
            }
        }
    }

    @Test
    fun fetchTVShowsFromNetwork() {
        val tvShows = listOf(TVShow(id = 3), TVShow(id = 4))
        val response = TVResponse(1, tvShows)

        val dao = object : TVDao {
            val daoTVShows = mutableListOf<TVShow>()

            override fun addTVShows(tvShows: List<TVShow>) {
                daoTVShows.addAll(tvShows)
            }

            override fun getTVShows(): List<TVShow> = daoTVShows

        }

        val tvDatabase: TVDatabase = mock {
            on { tvDao() } doReturn dao
        }

        val tvService: TelevisionService = mock {
            onBlocking { getTVShows(anyString()) } doReturn response
        }

        val tvShowRepository = TVShowRepository(tvService, tvDatabase)

        runTest {
            tvShowRepository.fetchTVShowsFromNetwork()
            assertEquals(tvShows, dao.getTVShows())
        }
    }
}