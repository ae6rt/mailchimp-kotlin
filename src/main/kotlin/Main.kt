import org.petrovic.mailchimp.Account
import mailjimp.service.impl.MailJimpJsonService
import mailjimp.dom.response.list.MailingList
import java.util.Date
import mailjimp.dom.enums.MemberStatus
import java.util.List
import mailjimp.dom.response.list.MemberResponseInfo
import org.petrovic.mailchimp.MailChimp
import java.text.SimpleDateFormat
import org.slf4j.LoggerFactory
import org.slf4j.Logger

val  LOG : Logger? = LoggerFactory.getLogger("MCDriver")

fun main(args: Array<String>) {
    val commandLineHelper = CommandLineHelper(args)
    val params : #(String, Date?, Opcode?) = commandLineHelper.getRuntimeParams()
    val targetListName : String = params._1
    val date : Date? = params._2
    val opcode : Opcode? = params._3
    LOG?.info("target list name: ${targetListName}")
    LOG?.info("since: ${date}")

    val mailChimp = MailChimp()

    when (opcode) {
        Opcode.unsubscribe -> mailChimp.unsubscribeByListSince(targetListName, date)
    }
}