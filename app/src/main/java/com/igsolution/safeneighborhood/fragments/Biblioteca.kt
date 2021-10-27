package com.igsolution.safeneighborhood.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.igsolution.safeneighborhood.R
import com.igsolution.safeneighborhood.databinding.FragmentBibliotecaBinding

class Biblioteca : Fragment() {

    private lateinit var binding: FragmentBibliotecaBinding

    companion object{
        fun newInstance(): Biblioteca{
            val fragmentBiblioteca = Biblioteca()
            val argumentos = Bundle ()
            fragmentBiblioteca.arguments = argumentos
            return fragmentBiblioteca
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_biblioteca, container, false)

    }

}