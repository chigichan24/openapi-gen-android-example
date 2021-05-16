package net.chigita.openapigenexample.ui.main

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.chigita.openapigenexample.gen.infrastructure.ApiClient
import net.chigita.openapigenexample.gen.pet.api.PetApi
import net.chigita.openapigenexample.gen.pet.model.Pet
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class MainViewModel : ViewModel() {

    private val _petLiveData = MutableLiveData<Pet>()
    val petLiveData: LiveData<Pet>
        get() = _petLiveData

    private var apiClient: ApiClient = ApiClient(
        "http://10.0.2.2:4567/v2/",
        OkHttpClient.Builder()
            .addNetworkInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
    )

    private val service: PetApi by lazy {
        apiClient.createService(PetApi::class.java)
    }


    fun getPetData() {
        viewModelScope.launch {
            val pet = getPetFromApi() ?: return@launch
            _petLiveData.postValue(pet)
        }
    }

    private suspend fun getPetFromApi(): Pet? {
        return withContext(Dispatchers.IO) {
            service.getPetById(0).body()
        }
    }
}
