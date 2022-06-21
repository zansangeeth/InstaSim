package com.zasa.instasim

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.zasa.instasim.models.Post

private const val TAG = "PostActivity"

class PostActivity : AppCompatActivity() {

    private lateinit var firebaseDb: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        firebaseDb = FirebaseFirestore.getInstance()
        val postReference = firebaseDb
            .collection("posts")
            .limit(20)
            .orderBy("creation_time_in_ms", Query.Direction.DESCENDING)
        postReference.addSnapshotListener { snapshot, error ->
            if (error != null || snapshot == null) {
                Log.i(TAG, "Exception when getting data", error)
                return@addSnapshotListener
            }
            val postList = snapshot.toObjects(Post::class.java)
            for (post in postList) {
                Log.i(TAG, "Post $post")
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_posts, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mProfile) {
            val profileIntent = Intent(this, ProfileActivity::class.java)
            startActivity(profileIntent)
        }
        return super.onOptionsItemSelected(item)
    }
}