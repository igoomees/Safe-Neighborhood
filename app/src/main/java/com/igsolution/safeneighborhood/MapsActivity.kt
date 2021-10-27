package com.igsolution.safeneighborhood

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.igsolution.safeneighborhood.cameras.Cameras

import com.igsolution.safeneighborhood.databinding.ActivityMapsBinding
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*

class MapsActivity : AppCompatActivity(), GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener, OnMapReadyCallback,
    ActivityCompat.OnRequestPermissionsResultCallback, GoogleMap.OnInfoWindowClickListener {

    private var permissionDenied = false
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private val permissoes = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )
    private var locationManager: LocationManager? = null
    private var locationListener: LocationListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()
        //validar permissoes
        Permissions.validarpermissoes(permissoes, this, 1)
        //objeto responsavel por gerenciar a localização do usuário
        locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        /*val maps_toolbar = binding.mapsToolbar
        maps_toolbar.setNavigationOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }*/
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.isMyLocationEnabled = true
        mMap.setOnMyLocationButtonClickListener(this)
        mMap.setOnMyLocationClickListener(this)

        //variavel que herda a atualização de localização
        locationListener = object : LocationListener{


            override fun onLocationChanged(location: Location) {
                var latitude = location.latitude
                var longitude = location.longitude

                val locusuario = LatLng(latitude, longitude)
            }
            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?){

            }

            override fun onProviderEnabled(provider: String) {
            }

            override fun onProviderDisabled(provider: String) {

            }
        }
        //nome dos condominios
        val cond_cordeiro = arrayListOf<String>("Le Premier","Porto Fino", "Vila Inglesa","Quinta da Boa Vista","Morada das Artes","BlumenPark",
            "Palmeiras","Porto Seguro","Mantova","Modena","Stephanie","Vila de Sole","Sant German","Chácara Paris", "Four Seasons",
            "Magnolia","Jatoba","Araucárias","Villagio Campos do Jordão", "Jerivas")

        val arraycond = arrayListOf<LatLng>()

        val visualizacao = arrayListOf<String>("Visualização de 1 câmera", "Visualização de 2 câmeras", "Visualização de 3 câmeras", "Visualização de 4 câmeras")

        //LatLng dos Condominios
        val cordeiro = LatLng(-23.6377555,-46.6820983)//Cordeiro
        val premier = LatLng(-23.6389797,-46.6776936) //Le Premier[0]
        val pfino = LatLng(-23.6388309,-46.677818) //Porto Fino[1]
        val vinglesa = LatLng(-23.6386069,-46.6779939) //Vila Inglesa[2]
        val quinta = LatLng(-23.6383898,-46.6781789) // Quinta da Boa Vista[3]
        val martes = LatLng(-23.6380219,-46.6784728) //Morada das Artes[4]
        val blumen = LatLng(-23.6396736,-46.6787059) //BlumenPark[5]
        val palmeiras = LatLng(-23.6400473,-46.6798915) //Palmeiras[6]
        val pseguro = LatLng(-23.6400995,-46.6801436)//Porto Seguro[7]
        val mantova = LatLng(-23.6395557,-46.680059)//Mantova[8]
        val modena = LatLng(-23.6386129,-46.680808)//Modena[9]
        val stephanie = LatLng(-23.6389936,-46.6813107)//Stephanie[10]
        val vsole = LatLng(-23.6392237,-46.6816201)//Vila De Sole[11]
        val sgerman = LatLng(-23.6357467,-46.6851304)//Sant German[12]
        val cparis = LatLng(-23.6354787,-46.685612)//Chacara Paris[13]
        val fseasons = LatLng(-23.6360442,-46.6853019)//Four Seasons[14]
        val magnolia = LatLng(-23.6343899,-46.6858906)//Magnolia[15]
        val jatoba = LatLng(-23.6338604,-46.6854139)//Jatoba[16]
        val araucarias = LatLng(-23.6331689,-46.6838275)//Araucarias[17]
        val villagio = LatLng(-23.6342642,-46.683751)//Villagio Campos do Jordao[18]
        val jerivas = LatLng(-23.6371741,-46.6851606)

        arraycond.add(premier)
        arraycond.add(pfino)
        arraycond.add(vinglesa)
        arraycond.add(quinta)
        arraycond.add(martes)
        arraycond.add(blumen)
        arraycond.add(palmeiras)
        arraycond.add(pseguro)
        arraycond.add(mantova)
        arraycond.add(modena)
        arraycond.add(stephanie)
        arraycond.add(vsole)
        arraycond.add(sgerman)
        arraycond.add(cparis)
        arraycond.add(fseasons)
        arraycond.add(magnolia)
        arraycond.add(jatoba)
        arraycond.add(araucarias)
        arraycond.add(villagio)
        arraycond.add(jerivas)

        //modificando estilo do mapa
        try{
            val success: Boolean = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.mapstyle))

            if (!success){
                Log.e("MapsActivity", "Style parsing failed.")
            }
        }catch (e: Resources.NotFoundException){
            Log.e("MapsActivity", "Can't find style. Error: ", e)
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cordeiro,15.5f))
        mMap.addMarker(MarkerOptions().position(arraycond[0]).title(cond_cordeiro[0]).snippet(visualizacao[0]))
            .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.securitycamera))
        mMap.addMarker(MarkerOptions().position(arraycond[1]).title(cond_cordeiro[1]).snippet(visualizacao[0]))
            .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.securitycamera))
        mMap.addMarker(MarkerOptions().position(arraycond[2]).title(cond_cordeiro[2]).snippet(visualizacao[0]))
            .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.securitycamera))
        mMap.addMarker(MarkerOptions().position(arraycond[3]).title(cond_cordeiro[3]).snippet(visualizacao[0]))
            .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.securitycamera))
        mMap.addMarker(MarkerOptions().position(arraycond[4]).title(cond_cordeiro[4]).snippet(visualizacao[0]))
            .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.securitycamera))
        mMap.addMarker(MarkerOptions().position(arraycond[5]).title(cond_cordeiro[5]).snippet(visualizacao[0]))
            .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.securitycamera))
        mMap.addMarker(MarkerOptions().position(arraycond[6]).title(cond_cordeiro[6]).snippet(visualizacao[0]))
            .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.securitycamera))
        mMap.addMarker(MarkerOptions().position(arraycond[7]).title(cond_cordeiro[7]).snippet(visualizacao[0]))
            .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.securitycamera))
        mMap.addMarker(MarkerOptions().position(arraycond[8]).title(cond_cordeiro[8]).snippet(visualizacao[0]))
            .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.securitycamera))
        mMap.addMarker(MarkerOptions().position(arraycond[9]).title(cond_cordeiro[9]).snippet(visualizacao[0]))
            .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.securitycamera))
        mMap.addMarker(MarkerOptions().position(arraycond[10]).title(cond_cordeiro[10]).snippet(visualizacao[0]))
            .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.securitycamera))
        mMap.addMarker(MarkerOptions().position(arraycond[11]).title(cond_cordeiro[11]).snippet(visualizacao[0]))
            .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.securitycamera))
        mMap.addMarker(MarkerOptions().position(arraycond[12]).title(cond_cordeiro[12]).snippet(visualizacao[0]))
            .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.securitycamera))
        mMap.addMarker(MarkerOptions().position(arraycond[13]).title(cond_cordeiro[13]).snippet(visualizacao[0]))
            .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.securitycamera))
        mMap.addMarker(MarkerOptions().position(arraycond[14]).title(cond_cordeiro[14]).snippet(visualizacao[0]))
            .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.securitycamera))
        mMap.addMarker(MarkerOptions().position(arraycond[15]).title(cond_cordeiro[15]).snippet(visualizacao[0]))
            .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.securitycamera))
        mMap.addMarker(MarkerOptions().position(arraycond[16]).title(cond_cordeiro[16]).snippet(visualizacao[0]))
            .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.securitycamera))
        mMap.addMarker(MarkerOptions().position(arraycond[17]).title(cond_cordeiro[17]).snippet(visualizacao[0]))
            .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.securitycamera))
        mMap.addMarker(MarkerOptions().position(arraycond[18]).title(cond_cordeiro[18]).snippet(visualizacao[0]))
            .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.securitycamera))
        mMap.addMarker(MarkerOptions().position(arraycond[19]).title(cond_cordeiro[19]).snippet(visualizacao[0]))
            .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.securitycamera))
        mMap.setOnInfoWindowClickListener(this)

        //checando permissões
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

        }
        locationManager!!.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            0,
            0f,
            locationListener!!
        )

        binding.btCordeiro.setOnClickListener {
            binding.btCordeiro.visibility = View.GONE
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cordeiro,15.5f))
        }
    }//Chave Google Maps

    override fun onMyLocationClick(location: Location) {

    }

    override fun onMyLocationButtonClick(): Boolean {
        binding.btCordeiro.visibility = View.VISIBLE
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false
    }

    override fun onInfoWindowClick(p0: Marker) {

        var titulo: String = p0.title
        var snip = p0.snippet

        val intent = Intent(this, Cameras::class.java)
        intent.putExtra("Condominio", titulo)
        intent.putExtra("Camera", snip)
        startActivity(intent)
        finish()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        for (permissaoResultado in grantResults){
            if (permissaoResultado == PackageManager.PERMISSION_DENIED){
                AlertaPermissao()

            }else if(permissaoResultado == PackageManager.PERMISSION_GRANTED){

                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    locationManager!!.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        0,
                        0f,
                        locationListener!!
                    )
                }
            }
        }
    }

    private fun AlertaPermissao() {
        var dialog = AlertDialog.Builder(this)
        dialog.setTitle("Permissão negada")
        dialog.setMessage("Você precisa aceitar as permissões")
        dialog.setCancelable(false)

        dialog.setPositiveButton("Aceitar"){dialogInterface, i ->
            finish()
        }
        dialog.create()
        dialog.show()
    }
}
