package com.dicoding.siternaku.ui.calculator

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {

    val hasil = MutableLiveData<String>("Hasil muncul di sini")

    fun hitungAyam(input: Int) {
        hasil.value = "Hasil Ayam: ${input * 2}"
    }

    fun hitungKambing(input: Int) {
        hasil.value = "Hasil Kambing: ${input * 3}"
    }
}



