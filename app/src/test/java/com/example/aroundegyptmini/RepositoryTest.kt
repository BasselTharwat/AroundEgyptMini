package com.example.aroundegyptmini

import com.example.aroundegyptmini.data.NetworkExperienceRepository
import com.example.aroundegyptmini.model.Experience
import com.example.aroundegyptmini.model.ExperienceResponse
import com.example.aroundegyptmini.model.ExperiencesResponse
import com.example.aroundegyptmini.model.LikeResponse
import com.example.aroundegyptmini.model.toEntity
import com.example.aroundegyptmini.network.ExperienceApiService
import com.example.aroundegyptmini.network.ExperienceDao
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.JsonObject
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.anyList
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoInteractions
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RepositoryTest {

    @Mock
    private lateinit var experienceApiService: ExperienceApiService

    @Mock
    private lateinit var experienceDao: ExperienceDao

    @InjectMocks
    private lateinit var repository: NetworkExperienceRepository

    private lateinit var sampleExperience: Experience

    @Before
    fun setUp() {
        sampleExperience = Experience(
            id = "123",
            title = "Great Pyramid of Giza",
            coverPhoto = "https://example.com/image.jpg",
            description = "Ancient wonder",
            viewsNo = 1000,
            likesNo = 100,
            recommended = 1,
            detailedDescription = "The last standing wonder of the ancient world",
            address = "Giza, Egypt",
            isLiked = false
        )
    }

    @Test
    fun `getRecentExperiences returns local data when available`() = runBlocking {
        `when`(experienceDao.getAllExperiences()).thenReturn(listOf(sampleExperience.toEntity()))

        val result = repository.getRecentExperiences()

        assertEquals(1, result.size)
        assertEquals("Great Pyramid of Giza", result[0].title)
        verify(experienceDao).getAllExperiences()
        verifyNoInteractions(experienceApiService)
    }

    @Test
    fun `getRecentExperiences fetches from API when no local data exists`() = runBlocking {
        `when`(experienceDao.getAllExperiences()).thenReturn(emptyList())

        val apiResponse = ExperiencesResponse(
            meta = mock(), data = listOf(sampleExperience),
            pagination = JsonObject(emptyMap())
        )
        `when`(experienceApiService.getRecentExperiences()).thenReturn(apiResponse)

        val result = repository.getRecentExperiences()

        assertEquals(1, result.size)
        assertEquals("Great Pyramid of Giza", result[0].title)
        verify(experienceDao).insertAllExperiences(anyList())
    }

    @Test
    fun `getExperience returns local data if available`() = runBlocking {
        `when`(experienceDao.getExperienceById("123")).thenReturn(sampleExperience.toEntity())

        val result = repository.getExperience("123")

        assertNotNull(result)
        assertEquals("Great Pyramid of Giza", result.title)
        verify(experienceDao).getExperienceById("123")
        verifyNoInteractions(experienceApiService)
    }


    @Test
    fun `getExperience fetches from API if not in cache`() = runBlocking {
        `when`(experienceDao.getExperienceById("123")).thenReturn(null)

        val apiResponse = ExperienceResponse(meta = mock(), data = sampleExperience, pagination = mock())
        `when`(experienceApiService.getExperience("123")).thenReturn(apiResponse)

        val result = repository.getExperience("123")

        assertNotNull(result)
        assertEquals("Great Pyramid of Giza", result.title)

    }




    @Test
    fun `likeExperience calls API and updates local DB`() = runBlocking {
        val likeResponse = LikeResponse(meta = mock(), data = 1, pagination = mock())

        `when`(experienceApiService.likeExperience("123")).thenReturn(likeResponse)

        val result = repository.likeExperience("123")

        assertEquals(1, result.data)
        verify(experienceDao).likeExperience("123") // Verify local update
    }
}
