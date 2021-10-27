package com.igsolution.safeneighborhood

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.igsolution.safeneighborhood.Model.Dados
import com.igsolution.safeneighborhood.databinding.ActivityPerfilBinding
import com.igsolution.safeneighborhood.fragments.Home
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class perfil : AppCompatActivity() {

    private var SelecionarUri: Uri? = null
    lateinit var binding: ActivityPerfilBinding
    val db = FirebaseFirestore.getInstance()
    var urlimage = ""
    private var Content: FrameLayout? = null
    private var mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_inicio -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_mapa -> {
                val intent = Intent(this, MapsActivity::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
                finish()
            }
            R.id.nav_biblioteca -> {

                return@OnNavigationItemSelectedListener true
                finish()
            }
        }
        false
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.content, fragment, fragment.javaClass.simpleName)
            .commit()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)
        CarregarFotodoPerfil()

        Content = binding.content
        binding.bottomMenu.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val fragment = Home.newInstance()
        addFragment(fragment)

    binding.txtFotoPerfil.setOnClickListener {
        SelecionarFotoDaGaleria()
    }
        binding.btSalvarmod.setOnClickListener {
            SalvarDadosNoFirebase()
            CarregarFotodoPerfil()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 0){
            SelecionarUri = data?.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, SelecionarUri)
            binding.imgPerfil.setImageBitmap(bitmap)
        }
    }

    private fun SelecionarFotoDaGaleria() {
        binding.btSalvarmod.isEnabled = true
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 0)
    }

    private fun SalvarDadosNoFirebase() {
        val nomeArquivo = FirebaseAuth.getInstance().uid //UUID.randomUUID().toString()
        val referencia = FirebaseStorage.getInstance().getReference(
            "/imagens/${nomeArquivo}")

        SelecionarUri?.let {
            referencia.putFile(it)
                .addOnSuccessListener {
                    referencia.downloadUrl.addOnSuccessListener {
                        val url = it.toString()
                        val uid = FirebaseAuth.getInstance().uid
                        val dados = Dados(url)
                        urlimage = dados.toString()
                        FirebaseFirestore.getInstance().collection("Users").document(uid.toString())
                            .update("url",url)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Imagem salva com sucesso!",Toast.LENGTH_SHORT).show()
                            }.addOnFailureListener {  }
                    }
                }
        }
    }

    override fun onStart() {
        super.onStart()

        val usuarioID:String = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val email = FirebaseAuth.getInstance().currentUser?.email.toString()
        val documentReference: DocumentReference
        
        documentReference = db.collection("Users").document(usuarioID)
        documentReference.addSnapshotListener { documentSnapshot, error ->

            if(documentSnapshot != null){
                binding.editNome.setText(documentSnapshot.getString("Nome"))
                binding.editSobrenome.setText(documentSnapshot.getString("Sobrenome"))
                binding.ediTelefone.setText(documentSnapshot.getString("Telefone"))
                //binding.txtuid.setText(documentSnapshot.getString("url"))
                binding.editEmail.setText(email)
            }
        }
    }

    private fun CarregarFotodoPerfil(){
        val imageName = FirebaseAuth.getInstance().uid
        val storageRef = FirebaseStorage.getInstance().reference.child("imagens/$imageName")

        val localfile = File.createTempFile("tempImage", "jpeg")
        storageRef.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            binding.imgPerfil.setImageBitmap(bitmap)
        }.addOnFailureListener {
            Toast.makeText(this, "Falha ao carregar imagem", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflate = menuInflater
        inflate.inflate(R.menu.menu_principal, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.Logoff -> {
                FirebaseAuth.getInstance().signOut()
                logoff()
            }

        }

        return super.onOptionsItemSelected(item)
    }

    private fun logoff(){
        val intent = Intent(this, formLogin::class.java)
        startActivity(intent)
        finish()
    }
}