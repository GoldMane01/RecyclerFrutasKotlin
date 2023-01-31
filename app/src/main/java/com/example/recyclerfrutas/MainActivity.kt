package com.example.recyclerfrutas

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerfrutas.adapter.FrutaAdapter
import com.example.recyclerfrutas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    var frutas:MutableList<Fruta> = mutableListOf<Fruta>()
    private lateinit var binding: ActivityMainBinding
    private var itemView: MenuItem? = null
    var contFrutaNueva:Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        frutas = cargar_lista()
        initRecyclerView()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        itemView = menu?.findItem(R.id.añadir)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.añadir -> {

                val fruta = Fruta("Plum "+contFrutaNueva, R.drawable.plum_bg,"PLUUUUUUUUUUM", 0)
                frutas.add(fruta)
                contFrutaNueva++
                binding.rvFruta.adapter?.notifyDataSetChanged()
            }
        }
        return true
    }

    fun initRecyclerView() {
        val manager = LinearLayoutManager(this)
        val decoration = DividerItemDecoration(this, manager.orientation)
        binding.rvFruta.layoutManager = manager
        binding.rvFruta.adapter = FrutaAdapter(frutas, this) { fruta -> onItemSelected(fruta) }
        binding.rvFruta.addItemDecoration(decoration)
    }

    fun onItemSelected(fruta: Fruta) {
        if(fruta.cantidad < 10) {
            fruta.cantidad += 1
            binding.rvFruta.adapter?.notifyDataSetChanged()
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {

        val info: AdapterView.AdapterContextMenuInfo = menuInfo as AdapterView.AdapterContextMenuInfo

        if (menu != null) {
            menu.setHeaderTitle(frutas[info.position].nombre)
        }

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_contextual, menu)

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.borrar -> {
                val menuInfo: AdapterView.AdapterContextMenuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
                frutas.removeAt(menuInfo.position)
                binding.rvFruta.adapter?.notifyDataSetChanged()
            }
            R.id.reset -> {
                val menuInfo: AdapterView.AdapterContextMenuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
                frutas[menuInfo.position].cantidad = 0
                binding.rvFruta.adapter?.notifyDataSetChanged()
            }
        }
        return true
    }

    fun cargar_lista():MutableList<Fruta> {

        for (i in frutas.indices) {
            frutas.removeAt(0)
        }

        val imagenes = intArrayOf(
            R.drawable.apple_bg, R.drawable.banana_bg, R.drawable.cherry_bg, R.drawable.orange_bg,
            R.drawable.pear_bg, R.drawable.raspberry_bg
        )
        val nombres = arrayOf(
            "Manzana","Platano","Cereza","Naranja","Pera","Frambuesa"
        )
        val descripciones = arrayOf(
            "La manzana aleja al medico","El platano entra por el culo to perfe","Las cerezas parecen mis huevos","La naranja esta to acida","La inferior manzana","Tiene pelos"
        )
        for(i in nombres.indices) {
            val fruta = Fruta(nombres[i],imagenes[i],descripciones[i],0)
            frutas.add(fruta)
        }
        return frutas
    }

}