package com.example.lab8_beta

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import androidx.fragment.app.FragmentTransaction


class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)
        setSupportActionBar(findViewById(R.id.detail_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (savedInstanceState == null) {


            val fragment = DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(DetailFragment.ARG_ITEM_ID,
                            intent.getStringExtra(DetailFragment.ARG_ITEM_ID))
                }
            }
            val stoper = StoperFragment().apply {
                arguments = Bundle().apply {
                    putString(StoperFragment.ARG_ITEM_ID,
                        intent.getStringExtra(StoperFragment.ARG_ITEM_ID))
                }
            }
            val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
            ft.add(R.id.stoper_container,stoper )
            ft.add(R.id.item_detail_container, fragment)
            ft.addToBackStack(null)
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            ft.commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                android.R.id.home -> {

                    navigateUpTo(Intent(this, MainActivity::class.java))
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
}