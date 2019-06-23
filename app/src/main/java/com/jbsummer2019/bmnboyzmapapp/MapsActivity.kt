package com.jbsummer2019.bmnboyzmapapp

import android.Manifest
import android.app.DownloadManager
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
import com.jbsummer2019.bmnboyzmapapp.entity.MarkerRepository
import com.jbsummer2019.bmnboyzmapapp.entity.DBHandler
import com.jbsummer2019.bmnboyzmapapp.entity.MarkerEntity
import kotlinx.android.synthetic.main.activity_maps.*
import android.content.res.Resources.NotFoundException
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.maps.model.*
import com.google.maps.android.PolyUtil
import org.json.JSONObject

val repository = MarkerRepository()

val APP_PREFERENCES = "mysettings"
val APP_PREFERENCES_COUNTER = "counter"
var counter = "standart"
val MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION  = 1
val MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1
//var Latitude: Double = 0.toDouble()
//var Longitude: Double = 0.toDouble()
lateinit var mMap:  GoogleMap


lateinit var pref: SharedPreferences  //для работы с настройками

class MapsActivity :  AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {



    private lateinit var locationManager: LocationManager


    init {
        if (repository.getAll().size == 0)
        {
            repository.add(MarkerEntity(1, 59.939191, 30.315777, "Александровская колонна", "Когда колонна была только-только установлена, жители остерегались ходить по площади, потому что думали, что колонна упадёт. Поэтому Монферран (чувак, который её придумал и спроектировал) каждый вечер выходил на площадь погулять со своей собачкой.", R.drawable.colonna, R.drawable.icon_colonna,false,"https://ru.wikipedia.org/wiki/Александровская_колонна"))
            repository.add(MarkerEntity(2, 59.933270, 30.343388, "Аничков мост", "Нет, он назван не в честь Анечки.", R.drawable.anichkov, R.drawable.icon_anichkov,false,"https://ru.wikipedia.org/wiki/Аничков_мост"))
            repository.add(MarkerEntity(3, 59.932219, 30.324958, "Грифоны", "На реставрации в 99 случаях из 100, так что если тебе удалось их встретить, ты просто лакер.", R.drawable.grifons,R.drawable.icon_grifons,false,"https://ru.wikipedia.org/wiki/Банковский_мост"))
            repository.add(MarkerEntity(4, 59.927701, 30.310945, "Дом Раскольникова", "Да, считается, что жил он, по замыслу автора, именно здесь.", R.drawable.rascolnik, R.drawable.icon_rascolnik,false,"https://ru.wikipedia.org/wiki/Дом_Раскольникова"))
            repository.add(MarkerEntity(5, 59.924624, 30.303270, "Дом старухи-процентщицы", "У-у-у, мрачное местечко.", R.drawable.procentninsha, R.drawable.icon_procentsnitsa,false,"https://ru.wikipedia.org/wiki/Дом_старухи-процентщицы"))
            repository.add(MarkerEntity(6, 59.952057, 30.308922, "Зоопарк", "Один из са-а-амых ма-а-а-аленьких зоопарков в Европе.", R.drawable.zoopark, R.drawable.icon_zoopark,false,"http://duma.gov.ru"))
            repository.add(MarkerEntity(7, 59.934027, 30.306340, "Исаакиевский собор", "Это одно из самых высоких зданий центра, так что забраться на колокольню точно стоит, потому что оттуда можно сделать шикарную панораму.", R.drawable.isac_sobor, R.drawable.icon_isak_sobor,false,"https://ru.wikipedia.org/wiki/Исаакиевский_собор"))
            repository.add(MarkerEntity(8, 59.944959, 30.335574, "Летний сад","Пруды с лебедями (или это просто утки?), похожие на лабиринт переплетения дорожек и, конечно же, памятник Крылову И. А. (это тот, который много-много басен написал).", R.drawable.letniy,R.drawable.icon_letniy,false ,"https://ru.wikipedia.org/wiki/Летний_сад"))
            repository.add(MarkerEntity(9, 59.940021, 30.338057, "Михайловский замок", "Вы знали, что там летает призрак Павла Первого?",R.drawable.misha_castle,R.drawable.icon_misha_castle,false,"https://ru.wikipedia.org/wiki/Михайловский_замок"))
            repository.add(MarkerEntity(10, 59.940007, 30.332775, "Михайловский сад", "Чувствуете, как сильно от Летнего сада отличается?", R.drawable.letniy, R.drawable.icon_letniy,false,"https://ru.wikipedia.org/wiki/Михайловский_сад"))
            repository.add(MarkerEntity(11, 59.929853, 30.288179, "Новая Голландия", "Раньше на этом острове находилось здание морской тюрьмы, а сейчас это центр «культурной урбанизации»‎, где можно с удовольствием провести время.", R.drawable.new_goland, R.drawable.icon_new_goland,false,"https://ru.wikipedia.org/wiki/Новая_Голландия"))
            repository.add(MarkerEntity(12, 59.950220, 30.316556, "Петропавловская крепость", "День, в который эта крепость была заложена (27 мая 1703 года), считается днём рождения Питера!", R.drawable.petropavl, R.drawable.icon_petropavl,false,"https://ru.wikipedia.org/wiki/Петропавловская_крепость"))
            repository.add(MarkerEntity(13, 59.938787, 30.332187, "Русский музей", "Крупнейшее собрание российского искусства в мире. Высочайшая концентрация русского духа!", R.drawable.russian_musuin, R.drawable.icon_russian_museim,false,"https://ru.wikipedia.org/wiki/Государственный_Русский_музей"))
            repository.add(MarkerEntity(14, 59.940134, 30.328878, "Храм Спаса на Крови", "Я тебя умоляю, только не путай с московским Собором Василия Блаженного...", R.drawable.spas_krov, R.drawable.icon_spas_crov,false,"https://ru.wikipedia.org/wiki/Спас_на_Крови"))
            repository.add(MarkerEntity(15, 59.945767, 30.372960, "Таврический сад", "Я к Таврическому саду, перепрыгнул через ограду...", R.drawable.tavric, R.drawable.icon_tavric,false,"https://ru.wikipedia.org/wiki/Таврический_сад"))
            repository.add(MarkerEntity(16, 59.941688, 30.338012, "Чижик-пыжик", "Осторожно, можно не рассчитать и нечаянно потратить все деньги, пытаясь попасть в него монеткой.", R.drawable.chiz, R.drawable.icon_chiz,false,"https://ru.wikipedia.org/wiki/Чижик-Пыжик_(памятник)"))
            repository.add(MarkerEntity(17, 59.932253, 30.251321, "Эрарта", "Просто огромная (аж 5 этажей) коллекция картин, скульптур, инсталляций и чего-только-ещё-там-нет современного искусства.", R.drawable.ararta, R.drawable.icon_ararta,false,"https://www.erarta.com"))
            repository.add(MarkerEntity(18, 59.939872, 30.314523, "Эрмитаж", "Если тебе захочется обойти все его залы, останавливаясь у каждого экспоната, придется выделить примерно 11 лет своего времени (можно, например, не ходить в школу).",  R.drawable.hermitage, R.drawable.icon_hermitage,false,"https://ermitazh.org/?yclid=3441625141898677072"))
        }
    }

   // private val LOCATION_PERMS = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
   // private val LOCATION_REQUEST = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION)

    override fun onCreate(savedInstanceState:  Bundle?) {
        super.onCreate(savedInstanceState)

        var localDB = DBHandler(this)

        val rez = repository.getAll()
        if (rez.size > DatabaseUtils.queryNumEntries(localDB.sqlObj, DBHandler.tableName)) {
            rez.forEach {
                addPlace2(it, localDB)
            }
        }

        pref = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)       //cчитываем сохраненные данные

        counter = pref.getString(APP_PREFERENCES_COUNTER, "standart")

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


        locationManager =  getSystemService(LOCATION_SERVICE) as LocationManager

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


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
            mMap.uiSettings.isMyLocationButtonEnabled = true
            val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            mMap.addMarker( MarkerOptions().position(LatLng(location.latitude,location.longitude)).title("ты").icon(
                BitmapDescriptorFactory.fromResource(R.drawable.icon_user)))

        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION)
        }



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
                        ))

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



        val path: MutableList<List<LatLng>> = ArrayList()
        val urlDirections = "https://maps.googleapis.com/maps/api/directions/json?origin=10.3181466,123.9029382&destination=10.311795,123.915864&key=<AIzaSyCMTS15vPj3VGzmhk3Hwm2MOsuGWZB5RVw>"
        val directionsRequest = object : StringRequest(Request.Method.GET, urlDirections, Response.Listener<String> {
                response ->
            val jsonResponse = JSONObject(response)
            // Get routes
            val routes = jsonResponse.getJSONArray("routes")
            val legs = routes.getJSONObject(0).getJSONArray("legs")
            val steps = legs.getJSONObject(0).getJSONArray("steps")
            for (i in 0 until steps.length()) {
                val points = steps.getJSONObject(i).getJSONObject("polyline").getString("points")
                path.add(PolyUtil.decode(points))
            }
            for (i in 0 until path.size) {
               mMap!!.addPolyline(PolylineOptions().addAll(path[i]).color(Color.RED))
            }
        }, Response.ErrorListener {
                _ ->
        }){}
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(directionsRequest)





        val piter = LatLng(59.941688, 30.338012)
        mMap.addAllMarkersEntites(repository.getAll())
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(piter, 13.0f))
        mMap.setOnMarkerClickListener(this)
    }

    override fun onMarkerClick(currentMarker: Marker?): Boolean {
        val results = repository.searchByTitle(currentMarker!!.title)
        if (currentMarker.title =="ты"){
            Log.d("Debug","nepravda")
            currentMarker.showInfoWindow()
        }
        else {
            results.forEach {
                val intent = Intent(this, MarkerActivity::class.java)
                intent.putExtra("position", (LatLng(it.position1, it.position2)).toString())
                intent.putExtra("title", it.title)
                intent.putExtra("text", it.text)
                intent.putExtra("like", it.like)
                intent.putExtra("giper_text", it.giper_text)
                intent.putExtra(MarkerActivity.IMAGE_KEY, it.imageId)
                startActivity(intent)
            }
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
