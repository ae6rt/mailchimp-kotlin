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
    var t = 0;
    var targetListName : String = ""
    var since : String = ""
    while(t < args.size) {
        if( args[t].equals("--list")) {
            targetListName = args[++t]
        }
        if( args[t].equals("--since")) {
            since = args[++t]
        }
        ++t;
    }
    if( targetListName.length == 0 ){
        println("A list name must be provided with --list <listname>");
        System.exit(-1);
    }

    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    val date = if( since.length != 0 ) {
        formatter.parse(since);
    } else {
        Date(0)
    }

    println("target list name: " + targetListName)
    println("since: " + date)

    val mailChimp = MailChimp()
    val driver = mailChimp.mcService()

    val lists = driver.lists();
    var targetList : MailingList? = null;
    for (list in  lists) {
        if (list?.getName().equals(targetListName)) {
            targetList = list
            break;
        }
    }
    val members = driver.listMembers(targetList?.getId(), MemberStatus.unsubscribed, date, 0, 100)
    doUnsubscribe(members)
}

fun doUnsubscribe(members : List<MemberResponseInfo?>?) : Unit {
    for (t in members) {
        println("@@@ do unsubscribe on: " + t?.getEmail())
    }
}
