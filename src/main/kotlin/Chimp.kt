package org.petrovic.mailchimp

import mailjimp.service.impl.MailJimpJsonService
import java.util.Properties
import org.slf4j.LoggerFactory
import org.slf4j.Logger
import mailjimp.dom.response.list.MailingList
import java.util.Date
import mailjimp.dom.response.list.MemberResponseInfo
import java.util.List
import mailjimp.dom.enums.MemberStatus

class Account() {
    val LOG : Logger? = LoggerFactory.getLogger("Account")

    public val userName : String?
    public val password : String?
    public val apiKey : String?

    {
        val clazz = javaClass
        val inputStream = clazz.getResourceAsStream("/mailchimp.properties")

        val p = Properties()
        p.load(inputStream)
        userName = p.getProperty("username")
        password = p.getProperty("password")
        apiKey = p.getProperty("apikey")
        LOG?.info(this.toString())
    }

    fun toString() : String? = String.format("MailChimp account:  username=%s, apiKey=%s", userName, apiKey)
}

class MailChimp() {
    val LOG : Logger? = LoggerFactory.getLogger("MailChimp")
    val pagesize = 100
    val driver : MailJimpJsonService

    {
        val account = Account()
        driver = MailJimpJsonService(account.userName, account.password, account.apiKey, "1.3", false)
        driver.init()
    }

    fun listIdByName(listName : String) : MailingList? {
        val lists = driver.lists()
        var targetList : MailingList? = null
        for (list in  lists) {
            if (list?.getName().equals(listName)) {
                targetList = list
                break
            }
        }
        if( targetList == null ) {
            throw  RuntimeException("No list found with name ${listName}")
        }
        return targetList
    }

    fun unsubscribeByListSince(list : String, since : Date?) {
        var targetList : MailingList? = listIdByName(list)
        var page = 0
        var members : List<MemberResponseInfo?>?
        do {
            members = driver.listMembers(targetList?.getId(), MemberStatus.unsubscribed, since, page++, pagesize)
            doUnsubscribe(members)
        } while (members?.size() == pagesize)
    }


    private fun doUnsubscribe(members : List<MemberResponseInfo?>?) : Unit {
        for (t in members) {
            LOG?.info("@@@ do unsubscribe on: ${t?.getEmail()}")
        }
    }
}