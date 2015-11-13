package co.touchlab.droidconandroid

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import co.touchlab.droidconandroid.data.AppPrefs
import co.touchlab.droidconandroid.data.DatabaseHelper
import co.touchlab.droidconandroid.tasks.persisted.GetTalkSubmissionPersisted
import co.touchlab.droidconandroid.tasks.persisted.PersistedTaskQueueFactory
import co.touchlab.droidconandroid.ui.DrawerAdapter
import co.touchlab.droidconandroid.ui.DrawerClickListener
import co.touchlab.droidconandroid.ui.NavigationItem
import java.util.*

/**
 *
 * Created by toidiu on 7/23/15.
 */
public class VotingActivity : AppCompatActivity() {

    public companion object {
        public fun callMe(c: Context) {
            val i = Intent(c, VotingActivity::class.java)
            c.startActivity(i)
        }
    }

    var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vote);

        toolbar = findViewById(R.id.toolbar) as Toolbar;
        setSupportActionBar(toolbar);
        toolbar!!.setBackgroundColor(resources.getColor(R.color.droidcon_green))

        val fragment = VoteFragment.newInstance()
        supportFragmentManager
                .beginTransaction()
                .add(R.id.container, fragment, VoteFragment.Tag)
                .commit()

        PersistedTaskQueueFactory.getInstance(this).execute(GetTalkSubmissionPersisted())
        setUpDrawers()
    }

    private fun setUpDrawers() {
        val drawerLayout = findViewById(R.id.drawer_layout) as DrawerLayout;
        var drawerToggle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        drawerLayout!!.setDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerToggle.syncState();
        //
        val navigationRecycler = findView(R.id.drawer_list) as RecyclerView
        val drawerAdapter = DrawerAdapter(getDrawerItems(), object : DrawerClickListener {
            override fun onNavigationItemClick(position: Int, titleRes: Int) {
                drawerLayout!!.closeDrawer(navigationRecycler)
                when (titleRes) {
                    R.string.vote -> VotingActivity.callMe(this@VotingActivity)
                    R.string.profile -> EditUserProfile.callMe(this@VotingActivity)
                    R.string.sponsors -> startActivity(WelcomeActivity.getLaunchIntent(this@VotingActivity, true))
                    R.string.about -> AboutActivity.callMe(this@VotingActivity)
                }
            }

            override fun onHeaderItemClick() {
                val userId = AppPrefs.getInstance(this@VotingActivity).getUserId()
                if (userId != null) {
                    val ua = DatabaseHelper.getInstance(this@VotingActivity).getUserAccountDao().queryForId(userId)
                    if (ua != null && ua.userCode != null && !TextUtils.isEmpty(ua.userCode)) {
                        drawerLayout!!.closeDrawer(navigationRecycler)
                        UserDetailActivity.callMe(this@VotingActivity, ua.userCode)
                    }
                }
            }

        })
        navigationRecycler!!.setAdapter(drawerAdapter)
        navigationRecycler!!.setLayoutManager(LinearLayoutManager(this))

    }

    private fun getDrawerItems(): List<Any> {

        var drawerItems = ArrayList<Any>()
        drawerItems.add("header_placeholder")
        drawerItems.add(NavigationItem(R.string.vote, R.drawable.ic_myschedule))
        drawerItems.add("divider_placeholder")
        drawerItems.add(NavigationItem(R.string.profile, R.drawable.ic_settings))
        drawerItems.add(NavigationItem(R.string.sponsors, R.drawable.ic_website))
        drawerItems.add(NavigationItem(R.string.about, R.drawable.ic_info))
        return drawerItems;
    }


}
