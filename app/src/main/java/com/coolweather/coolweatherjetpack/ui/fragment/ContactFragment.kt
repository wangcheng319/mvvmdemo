package com.coolweather.coolweatherjetpack.ui.fragment

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.coolweather.coolweatherjetpack.R
import com.coolweather.coolweatherjetpack.util.LogUtils
import kotlinx.android.synthetic.main.fragment_contact.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ContactFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ContactFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var activity:Context


    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        button10.setOnClickListener { getContact() }
    }

    private fun getContact() {
        val intent = Intent()
        intent.action = "android.intent.action.PICK"
        intent.addCategory("android.intent.category.DEFAULT")
        intent.type = "vnd.android.cursor.dir/phone_v2"
        startActivityForResult(intent, REQUEST_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val uri = data!!.data
        val contact = arrayOfNulls<String>(2)
        val cr: ContentResolver = activity.contentResolver
        val cursor = cr.query(uri!!, null, null, null, null)

        if (cursor != null){
            if (cursor.moveToNext()){
                contact[1] = cursor.getString(cursor.getColumnIndex("data1"))
                contact[0] = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                cursor.close()
            }
        }

        LogUtils.i(contact[0]+contact[1])

    }

    companion object {

        private val REQUEST_CODE = 1000

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContactFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
