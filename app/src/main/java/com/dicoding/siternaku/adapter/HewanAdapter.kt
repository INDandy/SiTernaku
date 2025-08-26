package com.dicoding.siternaku.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.siternaku.R
import com.dicoding.siternaku.data.HewanInput

class HewanAdapter(
    private val data: MutableList<HewanInput>
) : RecyclerView.Adapter<HewanAdapter.HewanViewHolder>() {

    inner class HewanViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val etJumlah = view.findViewById<EditText>(R.id.etJumlah)
        val etUsia = view.findViewById<EditText>(R.id.etUsia)
        val spinnerJenis = view.findViewById<Spinner>(R.id.spJenis)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HewanViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_hewan_input, parent, false)
        return HewanViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: HewanViewHolder, position: Int) {
        val item = data[position]

        // Spinner pilihan ayam/kambing
        ArrayAdapter.createFromResource(
            holder.view.context,
            R.array.jenis_hewan, // ["Ayam","Kambing"]
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            holder.spinnerJenis.adapter = adapter
        }
        holder.spinnerJenis.setSelection(if (item.jenis == "Ayam") 0 else 1)

        holder.etJumlah.setText(item.jumlah.toString())
        holder.etUsia.setText(item.usia.toString())

        // Update data tiap kali user edit
        holder.spinnerJenis.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, v: View?, pos: Int, id: Long) {
                val currentPos = holder.adapterPosition
                if (currentPos != RecyclerView.NO_POSITION) {
                    data[currentPos].jenis = parent.getItemAtPosition(pos).toString()
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        holder.etJumlah.addTextChangedListener {
            val currentPos = holder.adapterPosition
            if (currentPos != RecyclerView.NO_POSITION) {
                data[currentPos].jumlah = it.toString().toIntOrNull() ?: 0
            }
        }

        holder.etUsia.addTextChangedListener {
            val currentPos = holder.adapterPosition
            if (currentPos != RecyclerView.NO_POSITION) {
                data[currentPos].usia = it.toString().toIntOrNull() ?: 0
            }
        }
    }

}
