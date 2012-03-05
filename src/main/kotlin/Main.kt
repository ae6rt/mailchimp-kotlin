import org.petrovic.mailchimp.Account
import mailjimp.service.impl.MailJimpJsonService
import mailjimp.dom.response.list.MailingList
import java.util.Date
import mailjimp.dom.enums.MemberStatus
import java.util.List
import mailjimp.dom.response.list.MemberResponseInfo
import org.petrovic.mailchimp.MailChimp
import java.text.SimpleDateFormat

fun main(args: Array<String>) {
    val params : #(String?, String?) = getRuntimeParams(args)
    val targetListName : String? = params._1
    val since : String? = params._2

    if( targetListName == null ){
        println("A list name must be provided with --list <listname>")
        System.exit(-1)
    }

    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val date = if( since != null ) {
        formatter.parse(since)
    } else {
        Date(0)
    }

    println("target list name: ${targetListName}")
    println("since: ${date}")

    val mailChimp = MailChimp()
    val driver = mailChimp.mcService()

    val lists = driver.lists()
    var targetList : MailingList? = null
    for (list in  lists) {
        if (list?.getName().equals(targetListName)) {
            targetList = list
            break
        }
    }
    if( targetList == null ) {
        println("No list found with name ${targetListName}")
        System.exit(-1)
    }

    val pagesize = 100
    var page = 0
    var members : List<MemberResponseInfo?>?
    do {
        members = driver.listMembers(targetList?.getId(), MemberStatus.unsubscribed, date, page++, pagesize)
        doUnsubscribe(members)
    } while (members?.size() == pagesize)
}

fun doUnsubscribe(members : List<MemberResponseInfo?>?) : Unit {
    for (t in members) {
        println("@@@ do unsubscribe on: ${t?.getEmail()}")
    }
}

fun getRuntimeParams(args: Array<String>) : #(String?, String?) {
    var t = 0
    var targetListName : String? = null
    var since : String? = null
    while(t < args.size) {
        if( args[t].equals("--list")) {
            targetListName = args[++t]
        }
        if( args[t].equals("--since")) {
            since = args[++t]
        }
        ++t
    }
    val x : #(String?, String?) = #(targetListName, since)
    return x
}