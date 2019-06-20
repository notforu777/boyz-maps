package com.jbsummer2019.bmnboyzmapapp

import android.content.ContentValues
import android.content.Intent
import android.database.DatabaseUtils


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.jbsummer2019.bmnboyzmapapp.entity.MarkerRepository
import com.google.android.gms.maps.model.Marker
import com.jbsummer2019.bmnboyzmapapp.entity.DBHandler
import com.jbsummer2019.bmnboyzmapapp.entity.MarkerEntity
import kotlinx.android.synthetic.main.activity_maps.*

import android.content.res.Resources.NotFoundException

import com.google.android.gms.maps.model.MapStyleOptions

import android.content.SharedPreferences


val repository = MarkerRepository()

val APP_PREFERENCES = "mysettings"
val APP_PREFERENCES_COUNTER = "counter"
var counter = "standart"

lateinit var pref: SharedPreferences  //для работы с настройками

class MapsActivity :  AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap:  GoogleMap




    init {
        if (repository.getAll().size == 0)
        {
          repository.add(MarkerEntity(1, 59.939191, 30.315777, "Александровская колонна", "Когда колонна была только-только установлена, жители остерегались ходить по площади, потому что думали, что колонна упадёт. Поэтому Монферран (чувак, который её придумал и спроектировал) каждый вечер выходил на площадь погулять со своей собачкой.", R.drawable.chiz, R.drawable.icon_anichkov,false,"https://ru.wikipedia.org/wiki/Александровская_колонна"))
            repository.add(MarkerEntity(2, 59.933270, 30.343388, "Аничков мост", "Нет, он назван не в честь Анечки.", R.drawable.anichkov, R.drawable.icon_anichkov,false,"ttt"))
            repository.add(MarkerEntity(3, 59.932219, 30.324958, "Грифоны", "На реставрации в 99 случаях из 100, так что если тебе удалось их встретить, ты просто лакер.", R.drawable.grifons,R.drawable.icon_grifons,false,"https://ru.wikipedia.org/wiki/Александровская_колонна"))
            repository.add(MarkerEntity(4, 59.927701, 30.310945, "Дом Раскольникова", "Да, считается, что жил он, по замыслу автора, именно здесь.", R.drawable.chiz, R.drawable.icon_anichkov,false,"https://ru.wikipedia.org/wiki/Александровская_колонна"))
            repository.add(MarkerEntity(5, 59.924624, 30.303270, "Дом старухи-процентщицы", "У-у-у, мрачное местечко.", R.drawable.chiz, R.drawable.icon_anichkov,false,"https://ru.wikipedia.org/wiki/Александровская_колонна"))
            repository.add(MarkerEntity(6, 59.952057, 30.308922, "Зоопарк", "Один из са-а-амых ма-а-а-аленьких зоопарков в Европе.", R.drawable.chiz, R.drawable.icon_anichkov,false,"https://ru.wikipedia.org/wiki/Александровская_колонна"))
            repository.add(MarkerEntity(7, 59.934027, 30.306340, "Исаакиевский собор", "Это одно из самых высоких зданий центра, так что забраться на колокольню точно стоит, потому что оттуда можно сделать шикарную панораму.", R.drawable.chiz, R.drawable.icon_anichkov,false,"https://ru.wikipedia.org/wiki/Александровская_колонна"))
            repository.add(MarkerEntity(8, 59.944959, 30.335574, "Летний сад","Пруды с лебедями (или это просто утки?), похожие на лабиринт переплетения дорожек и, конечно же, памятник Крылову И. А. (это тот, который много-много басен написал).", R.drawable.chiz,R.drawable.icon_anichkov,false ,"https://ru.wikipedia.org/wiki/Александровская_колонна"))
            repository.add(MarkerEntity(9, 59.940021, 30.338057, "Михайловский замок", "Вы знали, что там летает призрак Павла Первого?",R.drawable.misha_castle,R.drawable.icon_misha_castle,false,"https://ru.wikipedia.org/wiki/Александровская_колонна"))
            repository.add(MarkerEntity(10, 59.940007, 30.332775, "Михайловский сад", "Чувствуете, как сильно от Летнего сада отличается?", R.drawable.chiz, R.drawable.icon_anichkov,false,"https://ru.wikipedia.org/wiki/Александровская_колонна"))
            repository.add(MarkerEntity(11, 59.929853, 30.288179, "Новая Голландия", "Раньше на этом острове находилось здание морской тюрьмы, а сейчас это центр «культурной урбанизации»‎, где можно с удовольствием провести время.", R.drawable.chiz, R.drawable.icon_anichkov,false,"https://ru.wikipedia.org/wiki/Александровская_колонна"))
            repository.add(MarkerEntity(12, 59.950220, 30.316556, "Петропавловская крепость", "День, в который эта крепость была заложена (27 мая 1703 года), считается днём рождения Питера!", R.drawable.chiz, R.drawable.icon_anichkov,false,"https://ru.wikipedia.org/wiki/Александровская_колонна"))
            repository.add(MarkerEntity(13, 59.938787, 30.332187, "Русский музей", "Крупнейшее собрание российского искусства в мире. Высочайшая концентрация русского духа!", R.drawable.chiz, R.drawable.icon_anichkov,false,"https://ru.wikipedia.org/wiki/Александровская_колонна"))
            repository.add(MarkerEntity(14, 59.940134, 30.328878, "Храм Спаса на Крови", "Я тебя умоляю, только не путай с московским Собором Василия Блаженного...", R.drawable.chiz, R.drawable.icon_anichkov,false,"https://ru.wikipedia.org/wiki/Александровская_колонна"))
            repository.add(MarkerEntity(15, 59.945767, 30.372960, "Таврический сад", "Я к Таврическому саду, перепрыгнул через ограду...", R.drawable.chiz, R.drawable.icon_anichkov,false,"https://ru.wikipedia.org/wiki/Александровская_колонна"))
            repository.add(MarkerEntity(16, 59.941688, 30.338012, "Чижик-пыжик", "Осторожно, можно не рассчитать и нечаянно потратить все деньги, пытаясь попасть в него монеткой.", R.drawable.chiz, R.drawable.icon_chiz,false,"https://ru.wikipedia.org/wiki/Александровская_колонна"))
            repository.add(MarkerEntity(17, 59.932253, 30.251321, "Эрарта", "Просто огромная (аж 5 этажей) коллекция картин, скульптур, инсталляций и чего-только-ещё-там-нет современного искусства.", R.drawable.chiz, R.drawable.icon_anichkov,false,"https://ru.wikipedia.org/wiki/Александровская_колонна"))
            repository.add(MarkerEntity(18, 59.939872, 30.314523, "Эрмитаж", "Если тебе захочется обойти все его залы, останавливаясь у каждого экспоната, придется выделить примерно 11 лет своего времени (можно, например, не ходить в школу).",  R.drawable.hermitage, R.drawable.icon_hermitage,false,"https://ru.wikipedia.org/wiki/Александровская_колонна"))

      
        }
    }
  




    override fun onCreate(savedInstanceState:  Bundle?) {
        super.onCreate(savedInstanceState)

        var localDB = DBHandler(this)

        val rez = repository.getAll()
        if (rez.size > DatabaseUtils.queryNumEntries(localDB.sqlObj, DBHandler.tableName)) {
            rez.forEach {
                addPlace2(it, localDB)
                }
            }

//        localDB.listPlacesByLike("%").forEach {
    //        Log.d("COOL_DEBUG", "${it.title} - ${it.like}")
    //    }


        pref = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)       //cчитываем сохраненные данные

        counter = pref.getString(APP_PREFERENCES_COUNTER, "standart");

        setContentView(R.layout.activity_maps)
        val mapFragment =  supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        button_menu.setOnClickListener {
            val intent =  Intent(this, menu::class.java)
            startActivity(intent)
        }
        button_selected!!.setOnClickListener {
            val intent =  Intent(this, LikedActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onPause() {
        super.onPause()

        // Запоминаем данные
        val editor = pref.edit()
        editor.putString(APP_PREFERENCES_COUNTER, counter)
        editor.apply()
    }

    override fun onStop() {
        super.onStop()

        // Запоминаем данные
        val editor = pref.edit()
        editor.putString(APP_PREFERENCES_COUNTER, counter)
        editor.apply()
    }

    override fun onMapReady(googleMap:  GoogleMap) {
        mMap = googleMap
        if(intent.getStringExtra("Style")!=null)
        {
            counter = intent.getStringExtra("Style")
            if (intent.getStringExtra("Style")=="dark")
            {
                try {
                    // Customise the styling of the base map using a JSON object defined
                    // in a raw resource file.
                    val success = googleMap.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_dark
                        )
                    )

                    if (!success) {
                        Log.e("Maps Activity", "Style parsing failed.")
                    }
                } catch (e: NotFoundException) {
                    Log.e("Maps Activity", "Can't find style. Error: ", e)
                }
            }

            if (intent.getStringExtra("Style")=="silver")
            {
                try {
                    // Customise the styling of the base map using a JSON object defined
                    // in a raw resource file.
                    val success = googleMap.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_silver
                        )
                    )

                    if (!success) {
                        Log.e("Maps Activity", "Style parsing failed.")
                    }
                } catch (e: NotFoundException) {
                    Log.e("Maps Activity", "Can't find style. Error: ", e)
                }
            }

            if (intent.getStringExtra("Style")=="retro")
            {
                try {
                    // Customise the styling of the base map using a JSON object defined
                    // in a raw resource file.
                    val success = googleMap.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_retro
                        )
                    )

                    if (!success) {
                        Log.e("Maps Activity", "Style parsing failed.")
                    }
                } catch (e: NotFoundException) {
                    Log.e("Maps Activity", "Can't find style. Error: ", e)
                }
            }
            if (intent.getStringExtra("Style")=="night")
            {
                try {
                    // Customise the styling of the base map using a JSON object defined
                    // in a raw resource file.
                    val success = googleMap.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_night
                        )
                    )

                    if (!success) {
                        Log.e("Maps Activity", "Style parsing failed.")
                    }
                } catch (e: NotFoundException) {
                    Log.e("Maps Activity", "Can't find style. Error: ", e)
                }
            }

            if (intent.getStringExtra("Style")=="aubergine")
            {
                try {
                    // Customise the styling of the base map using a JSON object defined
                    // in a raw resource file.
                    val success = googleMap.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_aubergine
                        )
                    )

                    if (!success) {
                        Log.e("Maps Activity", "Style parsing failed.")
                    }
                } catch (e: NotFoundException) {
                    Log.e("Maps Activity", "Can't find style. Error: ", e)
                }
            }

            if (intent.getStringExtra("Style")=="standart")
            {
                try {
                    // Customise the styling of the base map using a JSON object defined
                    // in a raw resource file.
                    val success = googleMap.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_standart
                        )
                    )

                    if (!success) {
                        Log.e("Maps Activity", "Style parsing failed.")
                    }
                } catch (e: NotFoundException) {
                    Log.e("Maps Activity", "Can't find style. Error: ", e)
                }
            }

        }
        else
        {
            if(counter == "dark")
            {
                try {
                    // Customise the styling of the base map using a JSON object defined
                    // in a raw resource file.
                    val success = googleMap.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_dark
                        )
                    )

                    if (!success) {
                        Log.e("Maps Activity", "Style parsing failed.")
                    }
                } catch (e: NotFoundException) {
                    Log.e("Maps Activity", "Can't find style. Error: ", e)
                }
            }
            if(counter == "silver")
            {
                try {
                    // Customise the styling of the base map using a JSON object defined
                    // in a raw resource file.
                    val success = googleMap.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_silver
                        )
                    )

                    if (!success) {
                        Log.e("Maps Activity", "Style parsing failed.")
                    }
                } catch (e: NotFoundException) {
                    Log.e("Maps Activity", "Can't find style. Error: ", e)
                }
            }
            if(counter == "retro")
            {
                try {
                    // Customise the styling of the base map using a JSON object defined
                    // in a raw resource file.
                    val success = googleMap.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_retro
                        )
                    )

                    if (!success) {
                        Log.e("Maps Activity", "Style parsing failed.")
                    }
                } catch (e: NotFoundException) {
                    Log.e("Maps Activity", "Can't find style. Error: ", e)
                }
            }

            if(counter == "night")
            {
                try {
                    // Customise the styling of the base map using a JSON object defined
                    // in a raw resource file.
                    val success = googleMap.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_night
                        )
                    )

                    if (!success) {
                        Log.e("Maps Activity", "Style parsing failed.")
                    }
                } catch (e: NotFoundException) {
                    Log.e("Maps Activity", "Can't find style. Error: ", e)
                }
            }

            if(counter == "aubergine")
            {
                try {
                    // Customise the styling of the base map using a JSON object defined
                    // in a raw resource file.
                    val success = googleMap.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_aubergine
                        )
                    )

                    if (!success) {
                        Log.e("Maps Activity", "Style parsing failed.")
                    }
                } catch (e: NotFoundException) {
                    Log.e("Maps Activity", "Can't find style. Error: ", e)
                }
            }

            if(counter == "standart")
            {
                try {
                    // Customise the styling of the base map using a JSON object defined
                    // in a raw resource file.
                    val success = googleMap.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_standart
                        )
                    )

                    if (!success) {
                        Log.e("Maps Activity", "Style parsing failed.")
                    }
                } catch (e: NotFoundException) {
                    Log.e("Maps Activity", "Can't find style. Error: ", e)
                }
            }
        }







        val piter = LatLng(59.941688, 30.338012)
        mMap.addAllMarkersEntites(repository.getAll())
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(piter, 13.0f))
        mMap.setOnMarkerClickListener(this)

    }


    override fun onMarkerClick(currentMarker: Marker?): Boolean {
        val results = repository.searchByTitle(currentMarker!!.title)
        results.forEach {
            val intent = Intent(this, MarkerActivity::class.java)
            intent.putExtra("position", (LatLng(it.position1, it.position2)).toString())
            intent.putExtra("title", it.title)
            intent.putExtra("text", it.text)
            intent.putExtra("like", it.like)
            intent.putExtra("giper_text",it.giper_text)
            intent.putExtra(MarkerActivity.IMAGE_KEY, it.imageId)
            startActivity(intent)
        }

        return true
    }


}

fun GoogleMap.addAllMarkersEntites(list : ArrayList<MarkerEntity>) {
    list.forEach {
        this.addMarker(
            MarkerOptions().position(LatLng(it.position1, it.position2)).title(it.title).icon(
                BitmapDescriptorFactory.fromResource(it.iconimageId)
            )
        )

    }
}

fun addPlace2(marker: MarkerEntity, local: DBHandler){
    var values = ContentValues()
    values.put(DBHandler.placePosition1, marker.position1)
    values.put(DBHandler.placePosition2, marker.position2)
    values.put(DBHandler._id, marker.id)
    values.put(DBHandler.placeTitle, marker.title)
    values.put(DBHandler.placeText, marker.text)
    values.put(DBHandler.placeLike, marker.like.toString())
    values.put(DBHandler.placeIconImageID, marker.iconimageId)
    values.put(DBHandler.placeImageID, marker.imageId)
    values.put(DBHandler.placeGiper_text, marker.giper_text)
    local.addPlace(values)
}


