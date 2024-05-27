package di

import com.russhwolf.settings.Settings
import data.local.PreferencesImpl
import data.remote.CurrencyApiServiceImpl
import domain.CurrencyApiService
import domain.PreferencesRepository
import domain.model.Currency
import org.koin.core.context.startKoin
import org.koin.dsl.module
import presentation.screen.HomeViewModel

val appModule = module {
    single { Settings() }
    single<PreferencesRepository> { PreferencesImpl(settings = get()) }
    single<CurrencyApiService> { CurrencyApiServiceImpl(preferences = get()) }

    factory<HomeViewModel> {
        HomeViewModel(
            preferencesRepository = get(),
            api = get())
    }
}

fun initializeKoin() {
    startKoin {
        modules(appModule)
    }
}