package com.example.recyclerfrutas.adapter

import android.app.Activity
import android.view.*
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recyclerfrutas.Fruta
import com.example.recyclerfrutas.R
import com.example.recyclerfrutas.databinding.ItemFrutaBinding

class FrutaAdapter(private val frutas:MutableList<Fruta>, private val activity:Activity ,private val onClickListener:(Fruta) -> Unit) : RecyclerView.Adapter<FrutaAdapter.FrutaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FrutaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FrutaViewHolder(layoutInflater.inflate(R.layout.item_fruta, parent, false))
    }

    override fun onBindViewHolder(holder: FrutaViewHolder, position: Int) {
        val item = frutas[position]
        holder.render(item, onClickListener)
    }

    override fun getItemCount(): Int {
        return frutas.size
    }

    inner class FrutaViewHolder(view:View): RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        val binding = ItemFrutaBinding.bind(view)

        fun render(frutaModel:Fruta, onClickListener:(Fruta) -> Unit) {
            binding.tvFruta.text = frutaModel.nombre
            binding.tvDescripcion.text = frutaModel.descripcion
            binding.tvCantidad.text = frutaModel.cantidad.toString()
            Glide.with(binding.ivFruta.context).load(frutaModel.imagen).into(binding.ivFruta)
            binding.ivFruta.setOnClickListener {onClickListener(frutaModel)}
        }

        init {
            view.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {

            val frutaSeleccionada: Fruta = frutas[adapterPosition]

            menu!!.setHeaderTitle(frutaSeleccionada.nombre)

            val inflater: MenuInflater = activity.menuInflater

            inflater.inflate(R.menu.menu_contextual, menu)

            for (i in 0 until menu.size()) {
                menu.getItem(i).setOnMenuItemClickListener(this)
            }
        }

        override fun onMenuItemClick(item: MenuItem?): Boolean {
            when (item?.itemId) {
                R.id.borrar -> {
                    frutas.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                    true
                }
                R.id.reset -> {
                    frutas[adapterPosition].cantidad = 0
                    notifyItemChanged(adapterPosition)
                    false
                }
                else -> false
            }
            return true
        }


    }



}