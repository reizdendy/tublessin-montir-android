package com.example.final_project.domain.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.tublessin_montir.config.RetrofitBuilder
import okhttp3.MultipartBody

class UserViewModel : ViewModel() {
    val userRepository: UserRepository

    init {
        val userAPI = RetrofitBuilder.createRetrofit().create(UserAPI::class.java)
        userRepository = UserRepository(userAPI)
    }

    fun getUserAccountInfo() = userRepository.userAccountInfo as LiveData<UserResponseMessage>

    fun registerUser(userAccount: UserAccount) {
        userRepository.registerUser(userAccount)
    }

    fun requestGetUserDetail(id: String) {
        userRepository.requestGetUserDetail(id)
    }

    fun uploadUserProfilePicture(id: String, image: MultipartBody.Part) {
        userRepository.uploadUserProfilePicture(id, image)
    }

    fun updateUserLocation(id: String, userLocation: UserLocation) {
        userRepository.updateUserLocation(id, userLocation)
    }

    fun updateUserProfile(id: String, userProfile: UserProfile) {
        userRepository.updateUserProfile(id, userProfile)
    }
}