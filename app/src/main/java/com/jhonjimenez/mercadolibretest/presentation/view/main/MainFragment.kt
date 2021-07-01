package com.jhonjimenez.mercadolibretest.presentation.view.main

import android.content.Context
import android.nfc.tech.MifareUltralight
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.jhonjimenez.mercadolibretest.R
import com.jhonjimenez.mercadolibretest.databinding.FragmentMainBinding
import com.jhonjimenez.mercadolibretest.datasource.remote.model.Results
import com.jhonjimenez.mercadolibretest.datasource.remote.model.SearchRequest
import com.jhonjimenez.mercadolibretest.presentation.utils.hideKeyboard
import com.jhonjimenez.mercadolibretest.presentation.view.detail.DetailFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.android.support.AndroidSupportInjection
import timber.log.Timber
import javax.inject.Inject

class MainFragment : Fragment(), SearchView.OnQueryTextListener {


    @Inject
    lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentMainBinding
    private lateinit var searchView: SearchView
    private lateinit var adapter: GroupAdapter<GroupieViewHolder>
    private lateinit var linearLayoutManager: LinearLayoutManager

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
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        setupAdapterGroupie()
        setupRecycler()
        val products = viewModel.getProducts()
        if (products.isNotEmpty()) {
            setDataInList(products)
        } else {
            binding.emptyStateCustomView.isVisible = true
            binding.emptyStateCustomView.setImageEmptyState(R.drawable.main_welcome_empty_state)
            binding.emptyStateCustomView.setTitleEmptyState(R.string.main_welcome_title)
            binding.emptyStateCustomView.setDescriptionEmptyState(R.string.main_welcome_description)
        }
    }

    private fun defineObservers() {

        viewModel.products.observe(
            viewLifecycleOwner,
            {
                if (it.isEmpty()) {
                    binding.emptyStateCustomView.setImageEmptyState(R.drawable.main_empty_state)
                    binding.emptyStateCustomView.setTitleEmptyState(R.string.main_empty_title)
                    binding.emptyStateCustomView.setDescriptionEmptyState(R.string.main_welcome_description)
                    binding.emptyStateCustomView.isVisible = true
                }

                setDataInList(it)
            }
        )

        viewModel.isLoading.observe(
            viewLifecycleOwner,
            {
                binding.progressBar.isVisible = it
            }
        )

        viewModel.mainLoading.observe(
            viewLifecycleOwner,
            {
                binding.loadingCustomView.isVisible = it
            }
        )

        viewModel.message.observe(
            viewLifecycleOwner,
            {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
            }
        )
    }

    private fun setDataInList(products: List<Results>) {
        Timber.i("load data in recycler")
        products.forEach {
            adapter.add(MainGroupie(it, object : MainGroupie.ClickListener {
                override fun onClickDetail(product: Results, imageView: ImageView) {
                    Timber.i("show detail product")
                    DetailFragment.product = product

                    val extras = FragmentNavigatorExtras(
                        imageView to product.thumbnail
                    )

                    val action =
                        MainFragmentDirections.actionMainFragmentToDetailFragment(product.thumbnail)
                    findNavController().navigate(action, extras)
                }

            }))
        }

        binding.textViewTotal.text = String.format(
            getString(R.string.main_total),
            viewModel.getProducts().size,
            viewModel.getTotal()
        )

        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.view_menu_main, menu)
        searchView = menu.findItem(R.id.item_menu_main_search).actionView as SearchView
        searchView.setIconifiedByDefault(true)
        searchView.setOnQueryTextListener(this)
    }

    private fun setupAdapterGroupie() {
        adapter = GroupAdapter()
    }

    private fun setupRecycler() {
        binding.recyclerView.also { recyclerView ->
            linearLayoutManager = LinearLayoutManager(binding.root.context)
            recyclerView.layoutManager = linearLayoutManager
            recyclerView.addItemDecoration(
                DividerItemDecoration(
                    recyclerView.context,
                    LinearLayoutManager.VERTICAL
                )
            )
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val visibleItemCount: Int = linearLayoutManager.childCount
                    val totalItemCount: Int = linearLayoutManager.itemCount
                    val firstVisibleItemPosition: Int =
                        linearLayoutManager.findFirstVisibleItemPosition()


                    if (!viewModel.isLoading.value!!) {
                        if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= MifareUltralight.PAGE_SIZE
                        ) {
                            viewModel.searchWithSameQuery()
                        }
                    }
                }
            })
        }
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        Timber.i("submit query")
        binding.root.hideKeyboard()

        searchView.setQuery("", false)
        searchView.isIconified = true
        searchView.clearFocus()

        adapter.clear()

        viewModel.searchProduct(
            SearchRequest(
                query = query
            ),
            isFromScroll = false
        )

        binding.emptyStateCustomView.isVisible = false

        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean = true

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}