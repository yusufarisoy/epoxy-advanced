package com.yusufarisoy.core.data.repository

import com.yusufarisoy.core.data.entity.location.Location
import com.yusufarisoy.core.data.entity.location.LocationListResponse
import com.yusufarisoy.core.data.remote.BaseDataSource
import com.yusufarisoy.core.data.remote.NetworkResponse
import com.yusufarisoy.core.data.remote.location.LocationService
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class LocationRepository @Inject constructor(private val locationService: LocationService) : BaseDataSource() {

    suspend fun getLocations(
        locationName: String? = null,
        locationType: String? = null,
        locationDimension: String? = null,
        page: Int? = null
    ): Flow<NetworkResponse<LocationListResponse>> = flow {
        emit(sendRequest { locationService.getLocations(locationName, locationType, locationDimension, page) })
    }.flowOn(Dispatchers.IO)

    suspend fun getLocationById(id: Int): Flow<NetworkResponse<Location>> = flow {
        emit(sendRequest { locationService.getLocationById(id) })
    }.flowOn(Dispatchers.IO)
}