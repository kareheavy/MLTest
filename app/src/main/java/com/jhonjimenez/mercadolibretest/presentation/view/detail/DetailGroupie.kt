package com.jhonjimenez.mercadolibretest.presentation.view.detail

import android.view.View
import com.jhonjimenez.mercadolibretest.R
import com.jhonjimenez.mercadolibretest.databinding.RecyclerViewAttributeBinding
import com.jhonjimenez.mercadolibretest.datasource.remote.model.Attributes
import com.xwray.groupie.viewbinding.BindableItem

class DetailGroupie(private val attribute: Attributes) :
    BindableItem<RecyclerViewAttributeBinding>() {

    override fun bind(viewBinding: RecyclerViewAttributeBinding, position: Int) {
        val context = viewBinding.root.context
        viewBinding.textViewAttribute.text = String.format(context.getString(R.string.detail_attribute), attribute.name, attribute.valueName)
    }

    override fun getLayout(): Int = R.layout.recycler_view_attribute

    override fun initializeViewBinding(view: View): RecyclerViewAttributeBinding =
        RecyclerViewAttributeBinding.bind(view)

}