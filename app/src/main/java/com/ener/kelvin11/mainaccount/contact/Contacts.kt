package com.ener.kelvin11.mainaccount.contact

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ener.kelvin11.R
import com.ener.kelvin11.databinding.ContactsFragmentBinding
import com.ener.kelvin11.mainaccount.add.CustomerData
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

class Contacts : Fragment() {

    private lateinit var viewModel: ContactsViewModel
    val adaptor = GroupAdapter<GroupieViewHolder>()
    lateinit var binding: ContactsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ContactsFragmentBinding.inflate(layoutInflater, container, false)
        viewModel = ContactsViewModel()
        viewModel.userData.observe(viewLifecycleOwner, {
            viewModel.userData.value?.let { it1 ->
                UserItem(
                    it1
                )
            }?.let { it2 -> adaptor.add(it2) }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchUsers()
        binding.recyclerView.adapter = adaptor
    }


}


class UserItem(private val user: CustomerData) : Item<GroupieViewHolder>() {
    @SuppressLint("SetTextI18n")
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.findViewById<TextView>(R.id.contact_name).text =
            user.name + " " + user.surname
        viewHolder.itemView.findViewById<TextView>(R.id.date).text = user.date
        viewHolder.itemView.findViewById<TextView>(R.id.contact_phone).text = user.phone
        viewHolder.itemView.findViewById<TextView>(R.id.contact_email).text = user.email
    }

    override fun getLayout(): Int {
        return R.layout.activity_recyclerview_temp
    }
}