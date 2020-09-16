package com.example.final_project.domain.user

import androidx.lifecycle.MutableLiveData
import com.pixplicity.easyprefs.library.Prefs
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(val userAPI: UserAPI) {
    var userAccountInfo = MutableLiveData<UserResponseMessage>()
    var userLocationInfo = MutableLiveData<UserLocation>()
    val token = "Bearer ${Prefs.getString("token", "0")}"


    fun registerUser(userAccount: UserAccount) {
        userAPI.registerUser(userAccount).enqueue(object : Callback<UserResponseMessage> {
            override fun onFailure(call: Call<UserResponseMessage>, t: Throwable) {
                println("REGISTER FAILED")
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<UserResponseMessage>,
                response: Response<UserResponseMessage>
            ) {
                if (response.code() == 200) {
                    println("REGISTER SUCCESS")
                    println(response.code())
                    println(response.body())
                    userAccountInfo.value = response.body()
                } else {
                    println("REGISTER FAILED")
                    println(response.code())
                    println(response.body())
                    userAccountInfo.value = response.body()
                }
            }

        })
    }

    fun requestGetUserDetail(id: String) {
        userAPI.getUserByID(token, id).enqueue(object : Callback<UserResponseMessage> {
            override fun onFailure(call: Call<UserResponseMessage>, t: Throwable) {
                println("REQUEST DETAIL USER FAILED")
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<UserResponseMessage>,
                response: Response<UserResponseMessage>
            ) {
                if (response.code() == 200) {
                    println("REQUEST USER DETAIL SUCCESS")
                    println(response.code())
                    println(response.body())
                    userAccountInfo.value = response.body()
                } else {
                    println("REQUEST USER DETAIL FAILED")
                    println(response.code())
                    println(response.body())
                }
            }

        })
    }

    fun uploadUserProfilePicture(id: String, image: MultipartBody.Part) {
        userAPI.uploadUserProfilePicture(token, image, id).enqueue(object : Callback<UserResponseMessage> {
            override fun onFailure(call: Call<UserResponseMessage>, t: Throwable) {
                println("UPLOAD PICTURE USER FAILED")
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<UserResponseMessage>,
                response: Response<UserResponseMessage>
            ) {
                if (response.code() == 200) {
                    println("UPLOAD PICTURE USER SUCCESS")
                    println(response.code())
                    println(response.body())
                } else {
                    println("UPLOAD PICTURE USER FAILED")
                    println(response.code())
                    println(response.body())
                }
            }

        })
    }

    fun updateUserLocation(id: String, userLocation: UserLocation) {
        userAPI.updateUserLocationByID(token, id, userLocation).enqueue(object : Callback<UserResponseMessage> {
            override fun onFailure(call: Call<UserResponseMessage>, t: Throwable) {
                println("UPDATE LOCATION USER FAILED")
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<UserResponseMessage>,
                response: Response<UserResponseMessage>
            ) {
                println(response.code())
                println(response.body())
            }

        })
    }

    fun updateUserProfile(id: String, userProfile: UserProfile) {
        userAPI.updateUserProfileByID(token, id, userProfile).enqueue(object : Callback<UserResponseMessage> {
            override fun onFailure(call: Call<UserResponseMessage>, t: Throwable) {
                println("UPDATE PROFILE USER FAILED")
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<UserResponseMessage>,
                response: Response<UserResponseMessage>
            ) {
                println(response.code())
                println(response.body())
            }

        })
    }
}