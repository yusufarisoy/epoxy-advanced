package com.yusufarisoy.core.data.remote.location

import com.yusufarisoy.core.data.entity.location.Location
import com.yusufarisoy.core.data.entity.location.LocationListResponse
import com.yusufarisoy.core.data.remote.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LocationService {

    @GET(Constants.GET_LOCATIONS)
    suspend fun getLocations(
        @Query("name") locationName: String?,
        @Query("type") locationType: String?,
        @Query("dimension") locationDimension: String?,
        @Query("page") page: Int?
    ): Response<LocationListResponse>

    @GET(Constants.GET_LOCATION_BY_ID)
    suspend fun getLocationById(@Path("id") id: Int): Response<Location>
}