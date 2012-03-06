/**
 @author petrovic -- 3/5/12 7:04 AM
 */

import java.util.Date
import org.slf4j.LoggerFactory
import org.slf4j.Logger
import java.text.SimpleDateFormat

class CommandLineHelper(val args : Array<String>) {
    val  LOG : Logger? = LoggerFactory.getLogger("CommandLineHelper")

    fun getRuntimeParams() : #(String, Date?, Opcode?) {
        var t = 0
        var targetListName : String = ""
        var since : String = ""
        var opcode : Opcode? = null
        while(t < args.size) {
            if( args[t].equals("--list")) {
                targetListName = args[++t]
            }
            if( args[t].equals("--since")) {
                since = args[++t]
            }
            if( args[t].equals("--opcode")) {
                val oc = args[++t]
                // todo need idiom for Java equivalent of Enum.valueOf(string)
                opcode = Opcode.unsubscribe
            }
            ++t
        }
        if( targetListName.length == 0 ){
            val msg : String = "A list name must be provided with --list <listname>"
            throw RuntimeException(msg)
        }
        if( opcode == null) {
            val msg : String = "An opcode must be provided with --opcode [unsubscribe]"
            throw RuntimeException(msg)
        }
        val date = dateForSince(since)
        return #(targetListName, date, opcode)
    }

    private fun dateForSince(s : String) : Date? {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = if(s.length != 0) {
            formatter.parse(s)
        } else {
            Date(0)
        }
        return date
    }
}
