Airbrake Notifier for Java
==========================

A fork of Luca Marrocco's [hoptoad notifier](http://code.google.com/p/hoptoad/).

Licensed under the Apache License, Version 2.0.


Code Changes
------------
* endpoint configuration property added. Defaults to new API end point (airbrake.io/notifier_api/v2/notices).
* secure configuration property added. Defaults to false.


Example Airbrake Appender log4j.xml
-----------------------------------

    <appender name="AIRBRAKE" class="hoptoad.HoptoadAppender">
        <param name="api_key" value="YOUR_API_KEY_HERE"/>
        <param name="env" value="production"/>
        <param name="enabled" value="true"/>
        <param name="endpoint" value="airbrake.io/notifier_api/v2/notices"/>
        <param name="secure" value="false"/>
    </appender>

Note: `endpoint` and `secure` params are shown with default values and may be omitted.
