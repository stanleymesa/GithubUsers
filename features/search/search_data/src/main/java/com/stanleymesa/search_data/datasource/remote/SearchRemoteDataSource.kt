package com.stanleymesa.search_data.datasource.remote

import javax.inject.Inject

class SearchRemoteDataSource @Inject constructor(
    private val remoteService: SearchRemoteService
) {
//    suspend fun getAnnouncementList(
//        page: Int,
//        pageSize: Int,
//        payload: AnnouncementPayload
//    ) = remoteService.getAnnouncementList(
//        page = page,
//        pageSize = pageSize,
//        orderBy = if (payload.orderBy is SortType.OldestToNewest) AppConstants.OLD_TO_NEW_SORT_ANNOUNCEMENT else null,
//        search = payload.search.ifBlank { null }
//    )
//
//    suspend fun getAnnouncementDetail(id: Int) = remoteService.getAnnouncementDetail(id)
}