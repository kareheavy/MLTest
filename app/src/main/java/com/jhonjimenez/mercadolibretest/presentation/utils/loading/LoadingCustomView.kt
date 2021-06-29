package com.jhonjimenez.mercadolibretest.presentation.utils.loading

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.jhonjimenez.mercadolibretest.R
import com.jhonjimenez.mercadolibretest.databinding.CustomViewBinding

import timber.log.Timber
import java.lang.Exception

class LoadingCustomView(context: Context, attrs: AttributeSet) :
    ConstraintLayout(context, attrs), IContractLoading {

    private lateinit var binding: CustomViewBinding
    private var imageId: Int = 0
    private var rawResId: Int = 0
    private var title: String = ""
    private var description: String = ""

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomView,
            0, 0
        ).apply {
            try {
                val layoutInflater =
                    getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

                binding = CustomViewBinding.inflate(
                    layoutInflater,
                    this@LoadingCustomView,
                    true
                )

                rawResId = getResourceId(R.styleable.CustomView_animation, 0)

                if (rawResId != 0) {
                    binding.lottieAnimationView.visibility = View.VISIBLE
                    binding.lottieAnimationView.setAnimation(rawResId)
                } else {
                    binding.imageView.visibility = View.VISIBLE
                    imageId = getResourceId(
                        R.styleable.CustomView_image,
                        R.mipmap.ic_launcher
                    )
                    setImageCustomView(imageId)
                }

                title = getString(R.styleable.CustomView_title) ?: ""
                description = getString(R.styleable.CustomView_description) ?: ""

                binding.textViewTitle.text = title
                binding.textViewDescription.text = description


            } catch (ex: Exception) {
                Timber.e(ex)
            }
        }
    }

    override fun setImageCustomView(drawable: Int) {
        binding.imageView.setImageDrawable(ContextCompat.getDrawable(context, drawable))
    }

    override fun setTitleCustomView(string: Int) {
        binding.textViewTitle.text = context.getString(string)
    }

    override fun setDescriptionCustomView(string: Int) {
        binding.textViewDescription.text = context.getString(string)
    }

}