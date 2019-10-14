package andr.mentorapp

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class SignOutFragment : Fragment() {
    companion object {
        fun newInstance(): SignOutFragment {
            return SignOutFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v = inflater.inflate(R.layout.fragment_sign_out, container, false)
        var button = v.findViewById<View>(R.id.sign_out_button)

        button.setOnClickListener {
            var intent = Intent(v.context, MainActivity::class.java)
            intent.setAction(ACTION_SIGN_OUT)
            startActivity(intent)
        }

        return v
    }
}