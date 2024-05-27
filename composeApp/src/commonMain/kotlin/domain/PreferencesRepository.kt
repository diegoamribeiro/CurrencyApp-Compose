package domain

interface PreferencesRepository {
    suspend fun saveLastUpdate(lastUpdate: String)
    suspend fun isDataFresh(currentTimestamp: Long): Boolean
}