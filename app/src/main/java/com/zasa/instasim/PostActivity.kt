package com.zasa.instasim

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.zasa.instasim.models.Post
import com.zasa.instasim.models.User
import kotlinx.android.synthetic.main.activity_post.*

private const val TAG = "PostActivity"
private const val EXTRA_USERNAME = "EXTRA_USERNAME"

open class PostActivity : AppCompatActivity() {

    private lateinit var firebaseDb: FirebaseFirestore
    private lateinit var posts : MutableList<Post>
    private lateinit var adapter: PostAdapter
    private var signedInUser : User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_InstaSim)
        setContentView(R.layout.activity_post)

        //create a layout file that represent the one post - DONE

        firebaseDb = FirebaseFirestore.getInstance()

        // create data source
        posts = mutableListOf()

        // create the adapter
        adapter = PostAdapter(this, posts)

        // Bind the adapter and the layout manager to the Recycler view
        rvPosts.adapter = adapter
        rvPosts.layoutManager = LinearLayoutManager(this)

        firebaseDb.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid as String)
            .get()
            .addOnSuccessListener { userSnapshot->
                signedInUser = userSnapshot.toObject(User::class.java)
                Log.i(TAG, "signed in user : $signedInUser")
            }
            .addOnFailureListener {exception->
                Log.i(TAG, "Failure of fetching signed in user", exception)
            }

        var postReference = firebaseDb
            .collection("posts")
            .limit(20)
            .orderBy("creation_time_in_ms", Query.Direction.DESCENDING)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        if (username != null){
            supportActionBar?.title = username
            postReference = postReference.whereEqualTo("user.username", username)
        }
        postReference.addSnapshotListener { snapshot, error ->
            if (error != null || snapshot == null) {
                Log.i(TAG, "Exception when getting data", error)
                return@addSnapshotListener
            }
            val postList = snapshot.toObjects(Post::class.java)
            posts.clear()
            posts.addAll(postList)
            adapter.notifyDataSetChanged()
            for (post in postList) {
                Log.i(TAG, "Post $post")
            }
        }

        fabCreate.setOnClickListener {
            val createIntent = Intent(this, CreateActivity::class.java)
            startActivity(createIntent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_posts, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mProfile) {
            val profileIntent = Intent(this, ProfileActivity::class.java)
            profileIntent.putExtra(EXTRA_USERNAME, signedInUser?.username)
            startActivity(profileIntent)
        }
        return super.onOptionsItemSelected(item)
    }
}