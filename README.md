
# Overview

This simple project demonstrates the use of the Kotlin programming language to make calls into the MailChimp API

[Kotlin]: (http://confluence.jetbrains.net/display/Kotlin/Welcome)

[MailChimp]: (http://mailchimp.com)

The Kotlin code uses the [MailJimp Java implementation] (https://github.com/mlaccetti/MailJimp.git) to make calls into MailChimp.

# Runtime

* To run the app, you need a MailChimp account
* Modify src/main/resource/mailchimp.properties to reflect your MailChimp username, password, and API key.
* Then execute:

$ mvn compile exec:java -Dexec.mainClass="namespace" -Dexec.args="--list oneofyourlists --since '2011-12-12 23:21:33'"

The script will show you which users of the named list have unsubscribed since the date provided.
