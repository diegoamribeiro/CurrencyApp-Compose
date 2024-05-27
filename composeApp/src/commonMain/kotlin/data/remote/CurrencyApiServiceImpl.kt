package data.remote

import domain.CurrencyApiService
import domain.PreferencesRepository
import domain.model.ApiResponse
import domain.model.Currency
import domain.model.CurrencyCode
import domain.model.RequestState
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class CurrencyApiServiceImpl(
    private val preferences: PreferencesRepository
) : CurrencyApiService{

    companion object {
        const val ENDPOINT = "https://api.currencyapi.com/v3/latest"
        const val API_KEY = "cur_live_YTrqNWphk1vMZwD9DIDYtDqCJaPxsb6bNBZp7F2s"
    }

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 15000
        }
        install(DefaultRequest) {
            headers {
                append("apikey", API_KEY)
            }
        }
    }

    override suspend fun getLatestExchangeRates(): RequestState<List<Currency>> {
        return try {
            val response = httpClient.get(ENDPOINT)
            if (response.status.value == 200){
                println("***API Response: ${response.body<String>()}")
                val apiResponse = Json.decodeFromString<ApiResponse>(response.body())

                val availableCurrencyCodes = apiResponse.data.keys
                    .filter {
                        CurrencyCode.entries
                            .map { code -> code.name }
                            .toSet()
                            .contains(it)
                    }

                val availableCurrencies = apiResponse.data.values
                    .filter { currency ->
                        availableCurrencyCodes.contains(currency.code)
                    }

                // Persist a timestamp of the last update
                val lastUpdate = apiResponse.meta.lastUpdatedAt
                preferences.saveLastUpdate(lastUpdate)

                RequestState.Success(data = availableCurrencies)
            }else{
                RequestState.Error(message = "HTTP Error Code: ${response.status}")
            }
        } catch (e: Exception) {
            RequestState.Error(message =  e.message.toString())
        }
    }
}