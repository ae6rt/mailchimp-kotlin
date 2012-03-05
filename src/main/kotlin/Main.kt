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
    val commandLineHelper = CommandLineHelper(args)
    val params : #(String, Date?) = commandLineHelper.getRuntimeParams()
    val targetListName : String = params._1
    val date : Date? = params._2
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
