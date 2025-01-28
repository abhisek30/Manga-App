package com.abhisek.manga.app.data.remote.service

import com.abhisek.manga.app.data.remote.dto.MangaDto
import retrofit2.Response
import retrofit2.http.GET

interface MangaService {

    @GET("/b/KEJO")
    suspend fun getMangaList() : Response<List<MangaDto>>
}