News Reader

Read New items ( JSON stream ) from given end point with GSON, process them, 
filter out stale published data and display article, video or slideshow in 
different background color.  Headlinesare also prepend the type of news, 
click on headline with take user tothe URL web page.  

TBD:

News source URL are hardcoded : https://s3.amazonaws.com/shrekendpoint/news.json .
News published more than 21 days ago are considered stale.

TODO: 1. Change it to spinner with populated sources.
	  2. Allow user pick days to be stale in textview or pull down list.