package co.touchlab.droidconandroid.network.dao

/**
 * Created by kgalligan on 7/26/14.
 */
data class UserInfoResponse(var user: UserAccount, var speaking: Array<EventInfo>, var attending: Array<EventInfo>)

data class UserAccount(var id: Long, var uuid: String, var name: String, var profile: String,
                       var avatarKey: String, var userCode: String, var company: String,
                       var twitter: String, var linkedIn: String, var website: String, var following: Boolean)

data class EventInfo(var id: Long, var name: String, var description: String)

fun userAccountToDb(ua: UserAccount, dbUa: co.touchlab.droidconandroid.data.UserAccount)
{
    dbUa.id = ua.id
    dbUa.uuid = ua.uuid
    dbUa.name = ua.name
    dbUa.profile = ua.profile
    dbUa.avatarKey = ua.avatarKey
    dbUa.userCode = ua.userCode
    dbUa.company = ua.company
    dbUa.twitter = ua.twitter
    dbUa.linkedIn = ua.linkedIn
    dbUa.website = ua.website
    dbUa.following = ua.following
}