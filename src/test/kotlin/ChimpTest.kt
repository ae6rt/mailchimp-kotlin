/**
 @author petrovic -- 3/5/12 6:55 AM
 */

import org.junit.Before
import org.junit.Assert
import org.junit.After
import org.junit.Test
import org.petrovic.mailchimp.Account
import jet.Array
import java.util.Date

class ChimpTest() {

    Before
    fun setup() {
        //        println("hello setup")
    }

    After
    fun teardown() {
        //        println("hello teardown")
    }

    Test
    fun testChimp() {
        val account = Account()
        Assert.assertNotNull(account)
    }

    Test
    fun commandLineHelperTest() {
        val testList : String = "testlist"
        // http://confluence.jetbrains.net/display/Kotlin/Basic+types#Basictypes-Arrays
        val args : Array<String> = array("--list", testList)
        val clh = CommandLineHelper(args)
        val tuple = clh.getRuntimeParams()
        val listName : String = tuple._1
        val date : Date? = tuple._2
        Assert.assertEquals(listName, testList)
        Assert.assertEquals(date, Date(0))
    }
}

