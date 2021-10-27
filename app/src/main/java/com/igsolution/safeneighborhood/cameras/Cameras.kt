package com.igsolution.safeneighborhood.cameras

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.igsolution.safeneighborhood.MapsActivity
import com.igsolution.safeneighborhood.databinding.ActivityCamerasBinding
import com.pedro.vlc.VlcListener
import com.pedro.vlc.VlcVideoLibrary

class Cameras : AppCompatActivity(), VlcListener {

    private lateinit var binding: ActivityCamerasBinding
    var vlcVideoLibrary: VlcVideoLibrary? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCamerasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        val cam_toolbar = binding.camerasToolbar
        cam_toolbar.setNavigationOnClickListener {
            onKeyDown()
        }

        val title = intent.getStringExtra("Condominio")
        val snip = intent.getStringExtra("Camera")
        val surfaceView = binding.view
        //rtsp condominios
        val rtsp_quinta = "rtsp://real2020:real2020@quinta2021.ddns-intelbras.com.br:5554/cam/realmonitor?channel=1&subtype=1"
        val rtsp_premier = "rtsp://real:real@lepremier18.ddns-intelbras.com.br:5554/cam/realmonitor?channel=3&subtype=1"
        val rtsp_fseason = "rtsp://real2020:real2020@fourseason21.ddns-intelbras.com.br:5554/cam/realmonitor?channel=1&subtype=1"
        val rtsp_martes = "rtsp://real2020:real2020@moradadasartes1.ddns-intelbras.com.br:5554/cam/realmonitor?channel=2&subtype=1"
        val rtsp_blumen = "rtsp://real2020:real2020@blumen21.ddns-intelbras.com.br:554/cam/realmonitor?channel=6&subtype=1"
        val rtsp_actuale = "rtsp://USUÁRIO:SENHA@actuale2016.ddns-intelbras.com.br:PORTA/cam/realmonitor?channel=1&subtype=1" //incompleto
        val rtsp_vinglesa = "rtsp://real2021:real2021@vilainglesa2021.ddns-intelbras.com.br:5554/cam/realmonitor?channel=1&subtype=1"
        val rtsp_pfino = "rtsp://real19:real19@portocordeiro017.ddns-intelbras.com.br:5554/cam/realmonitor?channel=1&subtype=1"
        val rtsp_palmeiras = "rtsp://real2021:real2021@palmeiras19.ddns-intelbras.com.br:5554/cam/realmonitor?channel=1&subtype=1"
        val rtsp_mantova = "rtsp://real2020:real2020@viladimantova21.ddns-intelbras.com.br:5554/cam/realmonitor?channel=5&subtype=1"
        val rtsp_pseguro = "portoreal.ddns-intelbras.com.br" //incompleto
        val rtsp_stephanie = "rtsp://real:real@stephaniecond.ddns-intelbras.com.br:5554/cam/realmonitor?channel=7&subtype=1"
        val rtsp_modena = "rtsp://real:real@modenareal1.ddns-intelbras.com.br:5554/cam/realmonitor?channel=12&subtype=1"
        val rtsp_vsole = "rtsp://USUÁRIO:SENHA@delsolereal19.ddns-intelbras.com.br:554/cam/realmonitor?channel=1&subtype=1" //incompleto
        val rtsp_cparis = "rtsp://real20:real20@joblane1178.ddns-intelbras.com.br:5554/cam/realmonitor?channel=3&subtype=1"
        val rstp_sgerman = "rtsp://real:real@sangerman197.ddns-intelbras.com.br:5554/cam/realmonitor?channel=17&subtype=1"
        val rtsp_magnolia = "rtsp://real2020:real2020@magnolia20.ddns-intelbras.com.br:5554/cam/realmonitor?channel=3&subtype=1"
        val rtsp_jatoba = "rtsp://USUÁRIO:SENHA@jatobareal.ddns-intelbras.com.br:PORTA/cam/realmonitor?channel=1&subtype=1" //incompleto
        val rtsp_araucarias = "rtsp://real2020:real2020@araucaria20.ddns-intelbras.com.br:5554/cam/realmonitor?channel=3&subtype=1"
        val rtsp_villagio = "rtsp://real2020:real2020@condcamposdejordao2019.ddns-intelbras.com.br:5554/cam/realmonitor?channel=6&subtype=1"
        val rtsp_jerivas = "rtsp://real18:real18@jerivas20.ddns-intelbras.com.br:5554/cam/realmonitor?channel=15&subtype=1"
        val rtsp_parcv = ""



        val rtsp_cond = arrayListOf<String>()
        var cond = 0
        var cam = 0
        rtsp_cond.add(rtsp_quinta)
        rtsp_cond.add(rtsp_premier)
        rtsp_cond.add(rtsp_fseason)
        rtsp_cond.add(rtsp_martes)
        rtsp_cond.add(rtsp_blumen)
        rtsp_cond.add(rtsp_palmeiras)
        rtsp_cond.add(rtsp_pseguro)
        rtsp_cond.add(rtsp_mantova)
        rtsp_cond.add(rtsp_modena)
        rtsp_cond.add(rtsp_stephanie)
        rtsp_cond.add(rtsp_vsole)
        rtsp_cond.add(rstp_sgerman)
        rtsp_cond.add(rtsp_cparis)
        rtsp_cond.add(rtsp_vinglesa)
        rtsp_cond.add(rtsp_pfino)
        rtsp_cond.add(rtsp_magnolia)
        rtsp_cond.add(rtsp_jatoba)
        rtsp_cond.add(rtsp_araucarias)
        rtsp_cond.add(rtsp_villagio)
        rtsp_cond.add(rtsp_jerivas)
        rtsp_cond.add(rtsp_parcv)
        when(title){
            "Quinta da Boa Vista" -> cond = 0
            "Le Premier" -> cond = 1
            "Four Seasons" -> cond = 2
            "Morada das Artes" -> cond = 3
            "BlumenPark" -> cond = 4
            "Palmeiras" -> cond = 5
            "Porto Seguro" -> cond = 6
            "Mantova" -> cond = 7
            "Modena" -> cond = 8
            "Stephanie" -> cond = 9
            "Vila de Sole" -> cond = 10
            "Sant German" -> cond = 11
            "Chácara Paris" -> cond = 12
            "Vila Inglesa" -> cond = 13
            "Porto Fino" -> cond = 14
            "Magnolia" -> cond = 15
            "Jatoba" -> cond = 16
            "Araucárias" -> cond = 17
            "Villagio Campos do Jordão" -> cond = 18
            "Jerivas" -> cond = 19
            "Parc Vignoble" -> cond = 20
            "Vila de Palme" -> cond = 21
        }
        when(snip){
            "Visualização de 1 câmera" -> cam = 0
                "Visualização de 2 câmeras" -> cam = 1
            "Visualização de 3 câmeras" -> cam = 2
            "Visualização de 4 câmeras" -> cam = 3
        }

        vlcVideoLibrary = VlcVideoLibrary(this, this, surfaceView)
        vlcVideoLibrary?.play(rtsp_cond[cond])
        Toast.makeText(applicationContext,snip, Toast.LENGTH_LONG).show()

    }

    private fun onKeyDown() {
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onComplete() {
        Toast.makeText(this, "Carregando", Toast.LENGTH_LONG).show()
    }

    override fun onError() {
        Toast.makeText(this, "Erro", Toast.LENGTH_LONG).show()
    }

}