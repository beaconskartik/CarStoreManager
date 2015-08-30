
1) I have assumed that Network will be available (there was no offline support added) , a loading screen will come if no network is present

2) Loading screen will come as soon as you launch the app and I triggered a AsyncTask in the background to fetch the details such as App name, rating , description price etc , as soon as fetching and parsing competed loading screen will be removed with list view

3) API HIT COUNT and NO OF CARS showing the list view is present in bottom, since header and footer support of list view is not sticky, so I have used stubView to make it visible in spite of the length of list view

4) Search is present in the ACTION BAR, user can search data on the basis of car name, car type and brand.

5) for searching I am using the filters and using listener setQueryChangeListener.

6) In the overflow menu there is another feature that has been added i.e sort by ( NAME, PRICE,  RATING, etc);

7) for fetching API HIT count another async is triggered

8) on click on the row item , Another activity will open which will have all details about app

9) as soon as user click on list , another async task will be triggered to fetch the image

10) BACK , LINK , SMS and SHARE buttons are working well

11) for SMS case , a dialog will appear for entering the phone number to whom we have to send sms. ( Using SMSManager)

12) showing the demographic data in the form of list view as don’t have time to convert into pie chart

13) clicking on LINK will open Quikr site inside the application, for this a wed view has been created and load url will set the content on it.

for more detail please mail me on kartik.sachan@gmail.com !