package com.stanleymesa.detail_domain.use_case

import com.stanleymesa.core.network.Resource
import com.stanleymesa.core.shared_data.model.User
import com.stanleymesa.detail_domain.model.UserRepos
import com.stanleymesa.detail_domain.repository.DetailRepository
import com.stanleymesa.shared_test.BaseTest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class DetailUseCasesTest : BaseTest() {
    private val repository: DetailRepository = mock()

    private lateinit var useCases: DetailUseCases

    private val dummyUsername = "stanleymesa"
    val dummyUser = User(
        twitterUsername = "dummyTwitter",
        bio = "This is a dummy biography for a test user. Loves coding and Android.",
        createdAt = "2023-01-15T10:30:00Z",
        login = dummyUsername,
        blog = "www.dummyblog.com",
        publicGists = 5,
        privateGists = 2,
        totalPrivateRepos = 10,
        followers = 150,
        avatarUrl = "https://example.com/avatar/dummy.png",
        updatedAt = "2024-03-10T12:00:00Z",
        htmlUrl = "https://github.com/dummyUserLogin",
        following = 30,
        name = "Dummy User Name",
        collaborators = 3,
        company = "Dummy Company Inc.",
        location = "Dummy City, DC",
        ownedPrivateRepos = 8,
        id = 12345,
        publicRepos = 25,
        email = "dummy.user@example.com"
    )
    val dummyRepos = listOf(
        UserRepos(
            fork = false,
            fullName = "stanleymesa/cool-android-app",
            updatedAt = "2024-03-10T10:15:30Z",
            stargazersCount = 125,
            pushedAt = "2024-03-09T18:05:00Z",
            name = "cool-android-app",
            description = "A really cool Android application built with Jetpack Compose and Kotlin.",
            createdAt = "2023-01-20T08:30:00Z",
            language = "Kotlin",
            id = 54321,
            watchersCount = 125,
            forksCount = 15,
            owner = UserRepos.Owner(
                login = "stanleymesa",
                id = 101
            )
        ),
        UserRepos(
            fork = true,
            fullName = "stanleymesa/some-forked-library",
            updatedAt = "2024-02-15T11:00:00Z",
            stargazersCount = 5,
            pushedAt = "2024-02-14T09:20:00Z",
            name = "some-forked-library",
            description = "My forked version with custom modifications. Original: otheruser/some-library",
            createdAt = "2024-01-05T16:45:00Z",
            language = "Java",
            id = 67890,
            watchersCount = 5,
            forksCount = 1,
            owner = UserRepos.Owner(
                login = "stanleymesa",
                id = 101
            )
        )
    )

    val dummyErrorMessage = "An error occurred"

    /** Success and Error flows for Get User */
    private val successGetUserFlow = flowOf(Resource.Success(dummyUser))
    private val errorGetUserFlow = flowOf(Resource.Error(dummyErrorMessage, null))

    /** Success and Error flows for Get User Repos */
    private val successGetUserReposFlow = flowOf(Resource.Success(dummyRepos))
    private val errorGetUserReposFlow = flowOf(Resource.Error(dummyErrorMessage, null))


    @Before
    fun setUp() {
        useCases = DetailUseCases(
            getUser = GetUser(repository),
            getUserRepos = GetUserRepos(repository)
        )
    }

    @Test
    fun `get user success`() = runTest {
        whenever(repository.getUser(dummyUsername)).thenReturn(successGetUserFlow)
        val result = useCases.getUser(dummyUsername)

        result.collect {
            assertTrue(it is Resource.Success)
            assertNotNull((it as Resource.Success).data)
            assertEquals(dummyUsername, it.data?.login)
        }
    }

    @Test
    fun `get user error`() = runTest {
        whenever(repository.getUser(dummyUsername)).thenReturn(errorGetUserFlow)
        val result = useCases.getUser(dummyUsername)

        result.collect {
            assertTrue(it is Resource.Error)
            assertNull((it as Resource.Error).data)
            assertEquals(dummyErrorMessage, it.message)
        }
    }

    @Test
    fun `get user repos success`() = runTest {
        whenever(repository.getUserRepos(dummyUsername)).thenReturn(successGetUserReposFlow)
        val result = useCases.getUserRepos(dummyUsername)

        result.collect {
            assertTrue(it is Resource.Success)
            assertNotNull((it as Resource.Success).data)
            assertEquals(dummyRepos, it.data)
            assertEquals(dummyRepos.size, it.data?.size)
        }
    }

    @Test
    fun `get user repos error`() = runTest {
        whenever(repository.getUserRepos(dummyUsername)).thenReturn(errorGetUserReposFlow)
        val result = useCases.getUserRepos(dummyUsername)

        result.collect {
            assertTrue(it is Resource.Error)
            assertNull((it as Resource.Error).data)
            assertEquals(dummyErrorMessage, it.message)
        }
    }
}