# Selenium - Firefox - Demo

When I started experimenting with remote controlling Firefox with Selenium, I found very few examples, and almost no documentation. I decided that if I ever figure out how to do it, I will write a demo to help others. After a few weeks of experimenting, I think I know enough to write and publish this demo.

## Introduction

Selenium does not control Firefox directly. It sends WebDriver commands a geckodriver, geckodriver translates them to marionette commands and sends them to Firefox.

So the first thing you need is a geckodriver, which is neither part of Selenium, nor part of Firefox, but separately downloadable from [here](https://github.com/mozilla/geckodriver/releases). The latest Selenium package includes Selenium Manager, which can download the geckodriver on-demand, but download takes a while, and there is no feedback, so it seems that your program hangs, and you might cancel it before it gets the driver. Besides, checking for the latest driver every time considerably slows down software development. It is better to download the latest geckodriver manually, and copy it into the same directory where Firefox is normally installed (usually 'C:\Program Files\Mozilla Firefox' on Windows).

The next surprise was that Selenium was unable to connect to a running Firefox instance. It always opened a new Firefox, and opened it from a temporary directory, without loading any of the existing profiles. This means the Firefox was very different from what I used to. No add-ons, no ad-blockers, no customization, no cookies and no history.    

## Highlights of this demo

[ExGeckoDriverService](demo/src/eu/pintergabor/demo/web/ExGeckoDriverService.java) extends Selenium GeckoDriverService, and allows the connection to a running Firefox. **To make it work, Firefox must be started with the poorly documented --marionette switch.** [Browser](demo/src/eu/pintergabor/demo/web/Browser.java) uses this to allow the main program to connect to either an existing, or to a new Firefox instance.

There is a simple Java Swing GUI in the [main](demo/src/eu/pintergabor/demo/main) package which controls the tests. Browser control tasks are time-consuming, so they cannot be run in the event-dispatching thread. All demos in the [demo](demo/src/eu/pintergabor/demo/demo) package are the extensions of [WebWorker](demo/src/eu/pintergabor/demo/web/WebWorker.java), which is the extension of SwingWorker, and they execute the time-consuming tasks a separate thread, and display the results in the event-dispatching thread.  

## License

Standard MIT license. Feel free to learn from it and incorporate it in your own projects.

## Source code

Available on [GitLab](https://gitlab.com/pintergabor/selenium-firefox-demo.git) or on [GitHub](https://github.com/pinter-gabor-at/selenium-firefox-demo.git).

## Known problems

- When I start Firefox with the --marionette switch, there are ugly pink/gray stripes over the address bar  
**Is there any way to hide them?**
- I suspect that some websites can detect if Firefox is running in remote controllable state    
**How do they do it? And how can it be prevented?**

If you know the answer, please contact me directly [Pintér Gábor <pinter.gabor@gmx.at>](mailto:pinter.gabor@gmx.at)   
Any help will be appreciated. 

