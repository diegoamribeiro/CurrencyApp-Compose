package domain

import domain.model.CurrencyCode
import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    suspend fun saveLastUpdate(lastUpdate: String)
    suspend fun isDataFresh(currentTimestamp: Long): Boolean
    suspend fun saveSourceCurrencyRepository(code: String)
    suspend fun saveTargetCurrencySode(code: String)
    fun readSourceCurrencyCode() : Flow<CurrencyCode>
    fun readTargetCurrencyCode() : Flow<CurrencyCode>
}