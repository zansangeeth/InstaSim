package com.zasa.instasim.models

import com.google.firebase.firestore.PropertyName

/**
 **@Project -> InstaSim
 **@Author -> Sangeeth on 6/22/2022
 */
data class Post(
    var description : String = "",
    @get:PropertyName("creation_time_in_ms") @set:PropertyName("creation_time_in_ms") var createdTimeInMs : Long = 0,
    @get:PropertyName("image_url") @set:PropertyName("image_url")var imageUrl : String = "",
    var user: User? = null
)