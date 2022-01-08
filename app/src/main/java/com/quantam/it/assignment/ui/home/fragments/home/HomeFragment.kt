package com.quantam.it.assignment.ui.home.fragments.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.quantam.it.assignment.R
import com.quantam.it.assignment.databinding.FragmentHomeBinding
import com.quantam.it.assignment.modal.CategoriesData
import com.quantam.it.assignment.modal.ProductCatalog
import com.quantam.it.assignment.ui.home.adapters.CategoryRecyclerAdapter
import com.quantam.it.assignment.ui.home.adapters.ProductCatalogueRecyclerAdapter

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var fragmentContext: Context
    private lateinit var fragmentActivity: FragmentActivity

    private val categoryList = ArrayList<CategoriesData>()
    private val backToCityList = ArrayList<ProductCatalog>()
    private val clothAndShoesList = ArrayList<ProductCatalog>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        fragmentContext = requireContext()
        fragmentActivity = requireActivity()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createCategoryList()
        setUpCategoryRecyclerView()
        createBackToCityList()
        setUpBackToListRecyclerView()
        createClothShoesList()
        setUpClothShoesRecyclerView()
    }

    private fun setUpClothShoesRecyclerView(){

        binding.clothAndShoesRecyclerView.apply {
            layoutManager = LinearLayoutManager(fragmentContext)

            val clothAdapter = ProductCatalogueRecyclerAdapter(clothAndShoesList, fragmentContext)

            val horizontalLayout = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )

            layoutManager = horizontalLayout

            adapter = clothAdapter
        }
    }

    private fun setUpCategoryRecyclerView(){

        binding.recyclerViewCategory.apply {
            layoutManager = LinearLayoutManager(fragmentContext)

            val categoryAdapter = CategoryRecyclerAdapter(categoryList, fragmentContext)

            val horizontalLayout = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )

            layoutManager = horizontalLayout

            adapter = categoryAdapter
        }

    }

    private fun setUpBackToListRecyclerView(){

        binding.backRecyclerView.apply {
            layoutManager = LinearLayoutManager(fragmentContext)

            val backAdapter = ProductCatalogueRecyclerAdapter(backToCityList, fragmentContext)

            val horizontalLayout = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )

            layoutManager = horizontalLayout

            adapter = backAdapter
        }
    }

    private fun createClothShoesList(){

        var data = ProductCatalog(R.drawable.levis_cloth, 30)
        clothAndShoesList.add(data)

        data = ProductCatalog(R.drawable.lehenga, 5)
        clothAndShoesList.add(data)

        data = ProductCatalog(R.drawable.shoes, 5)
        clothAndShoesList.add(data)

    }

    private fun createCategoryList() {

        var data = CategoriesData(R.drawable.art_craft_icon, "Art and Crafts")
        categoryList.add(data)

        data = CategoriesData(R.drawable.automotive_steering_icon, "Automotive")
        categoryList.add(data)

        data = CategoriesData(R.drawable.teddy_bear_icon, "Baby")
        categoryList.add(data)

        data = CategoriesData(R.drawable.computer_icon, "Computer")
        categoryList.add(data)

        data = CategoriesData(R.drawable.digital_music_icon, "Digital News")
        categoryList.add(data)

    }

    private fun createBackToCityList(){
        var data = ProductCatalog(R.drawable.watch, 30)
        backToCityList.add(data)

        data = ProductCatalog(R.drawable.apple_mobile, 5)
        backToCityList.add(data)

        data = ProductCatalog(R.drawable.shoes, 5)
        backToCityList.add(data)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}