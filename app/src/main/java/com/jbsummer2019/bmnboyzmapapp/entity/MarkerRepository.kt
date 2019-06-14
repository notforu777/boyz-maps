package com.jbsummer2019.bmnboyzmapapp.entity

class MarkerRepository {
    private var markerEntities : ArrayList<MarkerEntity> = ArrayList<MarkerEntity>()

    fun searchByTitle(id : String) = markerEntities.filter {it.title.equals(id)}

    fun add(markerEntity : MarkerEntity) = markerEntities.add(markerEntity)

    fun getAll() = markerEntities
}