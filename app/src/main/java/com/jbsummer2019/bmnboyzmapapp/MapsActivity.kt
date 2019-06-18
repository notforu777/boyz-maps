package com.jbsummer2019.bmnboyzmapapp

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.jbsummer2019.bmnboyzmapapp.entity.MarkerRepository
import com.google.android.gms.maps.model.Marker
import com.jbsummer2019.bmnboyzmapapp.entity.MarkerEntity
import kotlinx.android.synthetic.main.activity_marker.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap

    val repository = MarkerRepository()
    init {
        repository.add(MarkerEntity(LatLng(59.939191, 30.315777), "Алекасандровская колонна", "Когда колонна была только-только установлена, жители остерегались ходить по площади, потому что думали, что колонна упадёт. Поэтом Монферан (чувак, который её придумал и спроектировал) каждый вечер выходил на площадь погулять со своей собачкой.", R.drawable.chiz, R.drawable.icon_anichkov))
        repository.add(MarkerEntity(LatLng(59.933270, 30.343388), "Аничков мост", "Нет, он назван не в честь Анечки.", R.drawable.anichkov, R.drawable.icon_anichkov))
        repository.add(MarkerEntity(LatLng(59.932219, 30.324958), "Грифоны на Банковском мосту", "На реставрации в 99 случаях из 100, так что если тебе удалось их встретить, ты просто лакер.", R.drawable.chiz, R.drawable.icon_anichkov))
        repository.add(MarkerEntity(LatLng(59.927701, 30.310945), "Дом Раскольникова", "Да, считается, что жил он, по замыслу автора, именно здесь.", R.drawable.chiz, R.drawable.icon_anichkov))
        repository.add(MarkerEntity(LatLng(59.924624, 30.303270), "Дом старухи-процентщицы", "У-у-у, мрачное местечко.", R.drawable.chiz, R.drawable.icon_anichkov))
        repository.add(MarkerEntity(LatLng(59.934027, 30.306340), "Исаакиевский собор", "Это одно из самых высоких зданий центра, поэтому забраться на колокольню точно стоит, потому что оттуда открывается шикарный вид.", R.drawable.chiz, R.drawable.icon_anichkov))
        repository.add(MarkerEntity(LatLng(59.944959, 30.335574), "Летний сад","Пруды с лебедями (или это просто утки?), похожие на лабиринт переплетения дорожек и, конечно же, памятник Крылову И. А. (это тот, который много-много басен написал).", R.drawable.chiz,R.drawable.icon_anichkov ))
        repository.add(MarkerEntity(LatLng(59.940021, 30.338057), "Михайловский замок", "Знаете ли Вы, что там летает призрак Павла Первого?", R.drawable.chiz, R.drawable.icon_anichkov))
        repository.add(MarkerEntity(LatLng(59.940007, 30.332775), "Михайловский сад", "Чувствуете, как сильно от Летнего сада отличается?", R.drawable.chiz, R.drawable.icon_anichkov))
        repository.add(MarkerEntity(LatLng(59.929853, 30.288179), "Новая Голландия", "Раньше на этом острове находилось здание морской тюрьмы, а сейчас это центр «культурной урбанизации»‎", R.drawable.chiz, R.drawable.icon_anichkov))
        repository.add(MarkerEntity(LatLng(59.938787, 30.332187), "Русский музей", "Крупнейшее собрание российского искусства в мире. Высочайшая концентрация русского духа!", R.drawable.chiz, R.drawable.icon_anichkov))
        repository.add(MarkerEntity(LatLng(59.940134, 30.328878), "Храм Спаса на Крови", "Я тебя умоляю, только не путай с московским Собором Василия Блаженного...", R.drawable.chiz, R.drawable.icon_anichkov))
        repository.add(MarkerEntity(LatLng(59.945767, 30.372960), "Таврический сад", "Я к Таврическому саду, перепрыгнул через ограду...", R.drawable.chiz, R.drawable.icon_anichkov))
        repository.add(MarkerEntity(LatLng(59.941688, 30.338012),"Чижик-пыжик", "Осторожно, можно не рассчитать и нечаянно потратить все деньги, пытаясь попасть в него монеткой.", R.drawable.chiz, R.drawable.icon_chiz))
        repository.add(MarkerEntity(LatLng(59.939872, 30.314523), "Эрмитаж", "Если тебе захочется обойти все его залы, останавливаясь у каждого экспоната, придется выделить примерно 11 лет своего времени (можно, например, не ходить в школу).", R.drawable.chiz, R.drawable.icon_anichkov))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val piter = LatLng(59.941688, 30.338012)
//        val a = mMap.addMarker(MarkerOptions().position(piter).title("Чижик-пыжик"))
        mMap.addAllMarkersEntites(repository.getAll())
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(piter, 13.0f))
        mMap.setOnMarkerClickListener(this)

    }


    override fun onMarkerClick(currentMarker: Marker?): Boolean {
        val results = repository.searchByTitle(currentMarker!!.title)
        results.forEach {
            val intent = Intent(this, MarkerActivity::class.java)
            intent.putExtra("position", it.position.toString())
            intent.putExtra("title", it.title)
            intent.putExtra("text", it.text)
            intent.putExtra(MarkerActivity.IMAGE_KEY, it.imageId)
            startActivity(intent)
        }

        return true
    }
}

fun GoogleMap.addAllMarkersEntites(list : ArrayList<MarkerEntity>){
    list.forEach {
        this.addMarker(MarkerOptions().position(it.position).title(it.title).icon(BitmapDescriptorFactory.fromResource(it.iconimageId)))

    }
}
