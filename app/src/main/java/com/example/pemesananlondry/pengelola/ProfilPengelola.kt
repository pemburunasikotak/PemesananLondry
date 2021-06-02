package com.example.pemesananlondry.pengelola

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pemesananlondry.R
import com.example.pemesananlondry.pemesan.Pemesan
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_profil_pemesan.*

class ProfilPengelola : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil_pengelola)
        getUserProfil()
    }

    private fun getUserProfil() {
        val dataRef = FirebaseDatabase.getInstance().getReference("pengelola")
        var user: Pemesan? = null
        val currentuser = FirebaseAuth.getInstance().currentUser?.email.toString()

        dataRef.orderByChild("email").equalTo(currentuser)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    //Log.d("apaini", user?.nama!!)
                }

                override fun onDataChange(p0: DataSnapshot) {
                    for (snap in p0.children) {
                        user = snap.getValue(Pemesan::class.java)
                    }
                    //Log.d("apaini", user?.nama!!)
                    // tvalamat_profil_pemilik_bawah.text = user!!.nama
                    tv_alamatpemesanprofil.text = user!!.alamat
                    tv_emailprofilpemesan.text= user!!.email
                    tv_emailprofilpemesan1.text = user!!.email
                    tv_namaPemesan.text = user!!.nama
                }
            })
    }
}