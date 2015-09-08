This project was created in Android Studio and tested on a Vivo Blu phone.

    EcommerceWrap is a very basic mockup of a shopping cart activity in Android,
and ways to intercept various touch and click events. Examples include injecting 
a Sunstone method into an existing View.OnClickListener, overriding the 
Activity's dispatchTouchEvent() method, and wrapping instances where a public
function was defined (ex: Activitymain.onClickAddItem()).
While the added SunStone features only add to a log for viewing, eventually
we could keep an internal state and see if click events are humanly possible
i.e. is the element on screen? is the click too fast or too slow for a human?
We could also compare the app's internal model of the cart contents against
the displayed contents of the cart, and cross-reference with however many
click events we noticed (ex: cart has 3 separate items, but SunStone only observed
2 button clicks...suspicious). In general, any visible elements with a black
background and neon-green text aka "hacker style" are meant to be for getting
a behind the scenes look at the demo, and they are not essential for the program.

Usage:
    -Scroll through the different items and add or remove them from your cart with
    the associated +/- buttons. 
    -View your cart details by clicking on the 'Cart Details' button. 
    -Complete your purchase by clicking on the 'BUY' button.
    -View the most recent logged touch event in the black box at the bottom
    of the screen, or see all the logged events by opening the options menu
    either through the hardware context button or the 3 dots at the top of the
    actvitiy. 
    -Clear the log by clicking on the faint 'RESET' button in the bottom section
    -NOTE: the app should be exited either by using the Android BACK button, or
    clicking on the 'EXIT' button after completing a purchase. This ensures everything
    gets reset properly.

ActivityMain.java
    The app's main activity. It utilizes some fragments for displaying popup (DialogFragment)
    windows to the user.
    
SnSt.java
    This file contains the definition of a static class that has all the methods
    that I "added to the source". So one could, remove SnSt.java from the project,
    and do a find-in-files for "SunStone_" to see where the injections and wrappings
    were added.