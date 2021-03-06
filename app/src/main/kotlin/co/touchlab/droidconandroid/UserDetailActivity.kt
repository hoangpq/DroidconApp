package co.touchlab.droidconandroid

import android.app.Activity
import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem

/**
 * Created by kgalligan on 7/27/14.
 */
class UserDetailActivity : AppCompatActivity(), UserDetailFragment.Companion.FinishListener
{
    companion object
    {
        val USER_CODE = "USER_CODE"
        fun callMe(a: Activity, userCode: String)
        {
            val i = Intent(a, UserDetailActivity::class.java)
            i.putExtra(USER_CODE, userCode)
            a.startActivity(i)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.action))
        {
            processIntent(intent)
        }

        setContentView(R.layout.activity_user_detail)
    }

    override fun onFragmentFinished()
    {
        if (isTaskRoot)
        {
            startScheduleActivity(this)
        }

        finish()
    }

    override fun onResume()
    {
        super.onResume()
        // Check to see that the Activity started due to an Android Beam
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.action))
        {
            processIntent(intent)
        }
    }

    override fun onNewIntent(intent: Intent)
    {
        setIntent(intent)
    }

    fun processIntent(intent: Intent)
    {
        val rawMsgs = intent.getParcelableArrayExtra(
                NfcAdapter.EXTRA_NDEF_MESSAGES)

        val msg = rawMsgs[0] as NdefMessage

        val newIntent = Intent()

        newIntent.putExtra(USER_CODE, String(msg.records[0].payload))
        setIntent(newIntent)
    }

    override fun onBackPressed()
    {
        onFragmentFinished()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        if (item.itemId == android.R.id.home)
            onFragmentFinished()

        return super.onOptionsItemSelected(item)
    }
}