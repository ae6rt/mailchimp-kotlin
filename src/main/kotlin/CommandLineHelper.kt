/**
 @author petrovic -- 3/5/12 7:04 AM
 */

import java.util.Date
import org.slf4j.LoggerFactory
import org.slf4j.Logger
import java.text.SimpleDateFormat

class CommandLineHelper(val args : Array<String>) {
    val  LOG : Logger? = LoggerFactory.getLogger("CommandLineHelper")

    fun getRuntimeParams() : #(String, Date?) {
        var t = 0
        var targetListName : String = ""
        var since : String = ""
        while(t < args.size) {
            if( args[t].equals("--list")) {
                targetListName = args[++t]
            }
            if( args[t].equals("--since")) {
                since = args[++t]
            }
            ++t
        }
        if( targetListName.length == 0 ){
            LOG?.info("A list name must be provided with --list <listname>")
            System.exit(-1)
        }
        val date = dateForSince(since)
        return #(targetListName, date)
    }

    fun dateForSince(s : String) : Date? {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = if( s != null && s.length != 0) {
            formatter.parse(s)
        } else {
            Date(0)
        }
        return date
    }
}