# README #

Pop up Project - for students on SET07102 December 2014

* Java and JavaScript chess game
* Mostly by Adrian Blades, Napier University

Getting started in the JKCC at Edinburgh Napier University. If you are on another machine you will need two downloads on your local machine:

* Eclipse Luna, EE https://eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/lunasr1
* Apache Tomcat Version 7 http://tomcat.apache.org/download-70.cgi

* Start Eclipse Luna Enterprise Edition
![02.png](https://bitbucket.org/repo/Rnb8bz/images/3533242785-02.png)
* Use a new workspace – for example h:\eclipse luna\
![04.png](https://bitbucket.org/repo/Rnb8bz/images/3691989143-04.png)
* Open the Git perspective: Windows / Open Perspective / Other…
![06.png](https://bitbucket.org/repo/Rnb8bz/images/48450011-06.png)
* Clone a Git repository at https://bitbucket.org/cs66/popupproject.git
![08.png](https://bitbucket.org/repo/Rnb8bz/images/3596563849-08.png)![10.png](https://bitbucket.org/repo/Rnb8bz/images/2390898774-10.png)
* Import the project by right clicking from this screen:
![12.png](https://bitbucket.org/repo/Rnb8bz/images/2559199152-12.png)
![14.png](https://bitbucket.org/repo/Rnb8bz/images/185387995-14.png)
* Switch back to the JavaEE perspective:
![16.png](https://bitbucket.org/repo/Rnb8bz/images/1945319356-16.png)
* In project Explorer; Open DWP/ Java Resource / src / pup / ChessGameServlet.java
* You may find that the project has compile errors - the next step might fix this. Try running anyway...
![18.png](https://bitbucket.org/repo/Rnb8bz/images/3537900154-18.png)
* Right click:
* Run as.. / Run on Server
* Choose Apache Tomcat v7.0 Server
![20.png](https://bitbucket.org/repo/Rnb8bz/images/1110525221-20.png)
* Set the Tomcat Installation directory to k:\Xampp\tomcat
![22.png](https://bitbucket.org/repo/Rnb8bz/images/3213684351-22.png)
* Don't worry about the warning, it’s a good sign
![24.png](https://bitbucket.org/repo/Rnb8bz/images/2292632223-24.png)
* This looks bad... but it isn't. Have faith!
![26.png](https://bitbucket.org/repo/Rnb8bz/images/1679159635-26.png)
* Find DWP / WebContent / game.html and run that instead:
![28.png](https://bitbucket.org/repo/Rnb8bz/images/3798679131-28.png)
* Harrah!
* Try that URL in your favourite browser - http://localhost:8088/DWP/game.html
![30.png](https://bitbucket.org/repo/Rnb8bz/images/3216836640-30.png)
* You'll want to use a browser with a good debugger: Chrome, Firefox (with firebug), Internet Explorer 11