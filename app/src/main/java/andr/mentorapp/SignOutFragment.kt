package andr.mentorapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_sign_out.view.*

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

        v.sign_out_button.setOnClickListener {
            var intent = Intent(v.context, MainActivity::class.java)
            intent.setAction(ACTION_SIGN_OUT)
            startActivity(intent)
        }

        return v
    }
}