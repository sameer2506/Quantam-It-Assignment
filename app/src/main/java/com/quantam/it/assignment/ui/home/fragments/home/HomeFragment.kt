package com.quantam.it.assignment.ui.home.fragments.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.quantam.it.assignment.R
import com.quantam.it.assignment.databinding.FragmentHomeBinding
import com.quantam.it.assignment.modal.CategoriesData
import com.quantam.it.assignment.modal.ProductCatalog

import java.text.DecimalFormat
import java.text.NumberFormat
import android.os.CountDownTimer
import com.quantam.it.assignment.AppPreferences
import com.quantam.it.assignment.modal.DealsData
import com.quantam.it.assignment.modal.FlashDealData
import com.quantam.it.assignment.ui.home.adapters.*
import com.quantam.it.assignment.utils.log

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
    private val flashDealList = ArrayList<FlashDealData>()
    private val bestSellersList = ArrayList<ProductCatalog>()
    private val dealsList = ArrayList<DealsData>()

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
        createFlashDealList()
        setUpFlashRecyclerView()
        createBestSellerList()
        setUpBestSellerRecyclerView()
        createDealsList()
        setUpDealsRecyclerView()

        object : CountDownTimer(5000000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Used for formatting digit to be in 2 digits only
                val f: NumberFormat = DecimalFormat("00")
                val hour = millisUntilFinished / 3600000 % 24
                val min = millisUntilFinished / 60000 % 60
                val sec = millisUntilFinished / 1000 % 60
                binding.countDown.text =
                    f.format(hour).toString() + ":" + f.format(min) + ":" + f.format(sec)
            }

            // When the task is over it will print 00:00:00 there
            override fun onFinish() {
                binding.countDown.text = "00:00:00"
            }
        }.start()

    }

    private fun setUpDealsRecyclerView(){

        binding.dealsOfTheDayRecyclerView.apply {
            layoutManager = LinearLayoutManager(fragmentContext)

            val dealsAdapter = DealsRecyclerAdapter(dealsList, fragmentContext)

            val horizontalLayout = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )

            layoutManager = horizontalLayout

            adapter = dealsAdapter
        }

    }

    private fun createDealsList(){

        var data = DealsData(R.drawable.girl, "BIG OFFER DAY", "Offer 35% Off")
        dealsList.add(data)

        data = DealsData(R.drawable.camera, "Digital Cameras", "Offer 5% Off")
        dealsList.add(data)

    }

    private fun setUpBestSellerRecyclerView(){
        binding.bestSellerRecyclerView.apply {
            layoutManager = LinearLayoutManager(fragmentContext)

            val bestSellerAdapter = BestSellerRecyclerAdapter(bestSellersList, fragmentContext)

            val horizontalLayout = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )

            layoutManager = horizontalLayout

            adapter = bestSellerAdapter
        }
    }

    private fun createBestSellerList(){

        var data = ProductCatalog(R.drawable.mobiles, 20)
        bestSellersList.add(data)

        data = ProductCatalog(R.drawable.watches, 50)
        bestSellersList.add(data)

        data = ProductCatalog(R.drawable.bags, 30)
        bestSellersList.add(data)

    }

    private fun createFlashDealList() {

        var data = FlashDealData(R.drawable.syndal, 220.00)
        flashDealList.add(data)

        data = FlashDealData(R.drawable.syndal, 220.00)
        flashDealList.add(data)

        data = FlashDealData(R.drawable.syndal, 220.00)
        flashDealList.add(data)

    }

    private fun setUpFlashRecyclerView() {

        binding.flashDealsRecyclerView.apply {
            layoutManager = LinearLayoutManager(fragmentContext)

            val flashAdapter = FlashDealRecyclerAdapter(flashDealList, fragmentContext)

            val horizontalLayout = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )

            layoutManager = horizontalLayout

            adapter = flashAdapter
        }

    }


    private fun setUpClothShoesRecyclerView() {

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

    private fun setUpCategoryRecyclerView() {

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

    private fun setUpBackToListRecyclerView() {

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

    private fun createClothShoesList() {

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

    private fun createBackToCityList() {
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