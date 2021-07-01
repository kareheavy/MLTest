package com.jhonjimenez.mercadolibretest.presentation.view.main

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.jhonjimenez.mercadolibretest.R
import com.jhonjimenez.mercadolibretest.databinding.RecyclerViewItemBinding
import com.jhonjimenez.mercadolibretest.datasource.remote.model.Results
import com.jhonjimenez.mercadolibretest.presentation.utils.GlideApp
import com.jhonjimenez.mercadolibretest.presentation.utils.convertToCurrencyFormat
import com.xwray.groupie.viewbinding.BindableItem
import timber.log.Timber

class MainGroupie(
    private val product: Results,
    private val clickListener: ClickListener
) :
    BindableItem<RecyclerViewItemBinding>() {

    interface ClickListener {
        fun onClickDetail(product: Results, imageView: ImageView)
    }

    override fun bind(viewBinding: RecyclerViewItemBinding, position: Int) {

        val context = viewBinding.root.context
        viewBinding.textViewDescription.text = product.title
        viewBinding.textViewPrice.text = product.price.convertToCurrencyFormat()

        viewBinding.imageViewProduct.apply {
            transitionName = product.thumbnail

            GlideApp.with(context)
                .load(product.thumbnail)
                .apply(
                    RequestOptions()
                        .error(R.drawable.all_image_24dp)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .priority(Priority.HIGH)
                )
                .into(this)
        }

        viewBinding.root.setOnClickListener {

            clickListener.onClickDetail(product, viewBinding.imageViewProduct)
        }
    }

    override fun getLayout(): Int = R.layout.recycler_view_item

    override fun initializeViewBinding(view: View): RecyclerViewItemBinding =
        RecyclerViewItemBinding.bind(view)
}