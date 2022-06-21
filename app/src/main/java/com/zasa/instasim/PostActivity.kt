package com.zasa.instasim

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

private const val TAG = "PostActivity"
class PostActivity : AppCompatActivity() {

    private lateinit var firebaseDb : FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        firebaseDb = FirebaseFirestore.getInstance()
        val postReference = firebaseDb.collection("posts")
        postReference.addSnapshotListener { snapshot, error ->
            if (error != null || snapshot == null){
                Log.i(TAG, "Exception when getting data", error)
                return@addSnapshotListener
            }
            for (document in snapshot.documents){
                Log.i(TAG, "Document ${document.id} : ${document.data}")
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_posts, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mProfile){
            val profileIntent = Intent(this, ProfileActivity::class.java)
            startActivity(profileIntent)
        }
        return super.onOptionsItemSelected(item)
    }
}