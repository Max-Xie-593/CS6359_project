package andr.mentorapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_sign_out.view.*

/*
 * This class creates a Fragment representing the Sign Out button on each User's home page
 */
class SignOutFragment : Fragment() {
    // Keep info about creating Fragment here to maintain modularity
    companion object {
        // Activity can call this function to create new sign out fragment
        fun newInstance(): SignOutFragment {
            return SignOutFragment()
        }
    }

    // When the Fragment is created, set the sign out button's action when clicked
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v = inflater.inflate(R.layout.fragment_sign_out, container, false)

        v.sign_out_button.setOnClickListener {
            var intent = Intent(v.context, MainActivity::class.java)
            intent.action = ACTION_SIGN_OUT
            startActivity(intent)
        }

        return v
    }
}