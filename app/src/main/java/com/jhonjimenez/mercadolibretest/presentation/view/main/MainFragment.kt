package com.jhonjimenez.mercadolibretest.presentation.view.main

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import com.jhonjimenez.mercadolibretest.R
import com.jhonjimenez.mercadolibretest.databinding.FragmentMainBinding
import com.jhonjimenez.mercadolibretest.datasource.remote.model.SearchRequest
import dagger.android.support.AndroidSupportInjection
import timber.log.Timber
import javax.inject.Inject

class MainFragment : Fragment(), SearchView.OnQueryTextListener {


    @Inject
    lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentMainBinding
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        defineObservers()
        setupClickListener()
        return binding.root
    }

    private fun setupClickListener() {

    }

    private fun defineObservers() {

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.view_menu_main, menu)
        searchView = menu.findItem(R.id.item_menu_main_search).actionView as SearchView
        searchView.setIconifiedByDefault(true)
        searchView.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String): Boolean {

        viewModel.searchProduct(
            SearchRequest(
                query = query
            )
        )

        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean = false


    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}