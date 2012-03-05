package org.petrovic.mailchimp

import mailjimp.service.impl.MailJimpJsonService
import java.util.Properties
import org.slf4j.LoggerFactory
import org.slf4j.Logger

class Account() {
    val LOG : Logger? = LoggerFactory.getLogger("Account")

    public var userName : String? = ""
    public  var password : String? = ""
    public var apiKey : String? = ""

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
    val account = Account()

    fun mcService() : MailJimpJsonService {
        val t = MailJimpJsonService(account.userName, account.password, account.apiKey, "1.3", false)
        t.init()
        return t
    }
}