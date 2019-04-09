package com.ydh.example.kakaointerview.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ydh.example.kakaointerview.R
import com.ydh.example.kakaointerview.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewDataBinding: ActivityMainBinding
    val mainViewModel: MainViewModel by viewModel()
    val imageAdapter: ImageAdapter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindViewModel()
        setSupportActionBar(toolbar)
        imageList.adapter = imageAdapter
        mainViewModel.loadImage()
        setListLayoutToGrid()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_grid -> {
                setListLayoutToGrid()
                true
            }
            R.id.action_list -> {
                setListLayoutToList()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun bindViewModel() {
        viewDataBinding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        with(viewDataBinding) {
            this.viewmodel = mainViewModel
            this.lifecycleOwner = this@MainActivity
        }
        mainViewModel.errorMessage.observe(this@MainActivity, Observer{ errMessage ->
            Snackbar.make(viewDataBinding.root, errMessage, Snackbar.LENGTH_LONG).show()
        })
    }

    fun setListLayoutToGrid() {
        if(imageAdapter.viewMode == ImageAdapter.ViewMode.GRID) {
            return
        }
        imageAdapter.viewMode = ImageAdapter.ViewMode.GRID
        imageList.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        imageList.adapter = imageAdapter
    }

    fun setListLayoutToList() {
        if(imageAdapter.viewMode == ImageAdapter.ViewMode.LIST) {
            return
        }
        imageAdapter.viewMode = ImageAdapter.ViewMode.LIST
        imageList.layoutManager = LinearLayoutManager(this@MainActivity)
        imageList.adapter = imageAdapter
    }
}
