package com.android.testable.myapplication.repository

import com.android.testable.myapplication.api.Dog
import com.android.testable.myapplication.storage.room.DogEntity

class DogMapper {

    fun mapServiceToEntity(dog: Dog): List<DogEntity> = dog.urls.map {
        DogEntity(0, it)
    }

    fun mapEntityToUi(dogEntity: DogEntity): DogUi = DogUi(dogEntity.url)
}