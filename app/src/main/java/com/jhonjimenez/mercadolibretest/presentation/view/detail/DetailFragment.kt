package com.jhonjimenez.mercadolibretest.presentation.view.detail

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.jhonjimenez.mercadolibretest.R
import com.jhonjimenez.mercadolibretest.databinding.FragmentDetailBinding
import com.jhonjimenez.mercadolibretest.datasource.remote.model.Results
import com.jhonjimenez.mercadolibretest.presentation.utils.Constants
import com.jhonjimenez.mercadolibretest.presentation.utils.GlideApp
import com.jhonjimenez.mercadolibretest.presentation.utils.convertToCurrencyFormat
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import timber.log.Timber

class DetailFragment : Fragment() {

    companion object {
        var product: Results? = null
    }

    private val args: DetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailBinding
    private lateinit var adapter: GroupAdapter<GroupieViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        setDataUI()
        return binding.root
    }

    private fun setDataUI() {
        if (product != null) {
            Timber.i("Load detail product")
            val condition =
                if (product?.condition == "new") getString(R.string.common_new) else getString(R.string.common_used)

            binding.textViewConditionAndSold.text = String.format(
                getString(R.string.detail_condition_and_sold),
                condition,
                product?.soldQuantity
            )

            binding.textViewTitle.text = product?.title

            binding.textViewPrice.text = product?.price?.convertToCurrencyFormat()

            if (product?.installments != null) {
                binding.textViewInstallments.text = String.format(
                    getString(R.string.detail_installment),
                    product?.installments?.quantity,
                    product?.installments?.amount?.convertToCurrencyFormat()
                )
            }

            binding.textViewStockAvailable.text = String.format(
                getString(R.string.detail_quantity_available),
                product?.availableQuantity
            )

            if (product?.attributes != null) {

                adapter = GroupAdapter()

                product?.attributes?.forEach {
                    adapter.add(DetailGroupie(it))
                }

                binding.recyclerView.also { recyclerView ->
                    recyclerView.layoutManager = LinearLayoutManager(binding.root.context)
                    recyclerView.setHasFixedSize(true)
                    recyclerView.adapter = adapter
                }
            }


        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (product == null) {
            Timber.i("error detail product")
            Snackbar.make(binding.constraintLayout, Constants.error, Snackbar.LENGTH_LONG).show()
            findNavController().navigateUp()
        } else {
            binding.imageViewProduct.apply {
                transitionName = args.uri

                GlideApp.with(context)
                    .load(product?.thumbnail)
                    .apply(
                        RequestOptions()
                            .error(R.drawable.all_image_24dp)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .priority(Priority.HIGH)
                    )
                    .into(this)
            }
        }
    }

}