TO DO LIST:
----

When working on a point, check if there are TODO's adjacent in the code for you.

----
**NON-PROGRAMMING**

Update the CoC: check points in minutes

Make the presentation: What  is the rubric?

----

**BASIC REQ**

##### the system needs to illustrate the use of long-polling via REST and websockets.
- Implement websockets
- Implement long-polling (one example of each)

##### It must be possible to connect at least 10 clients simultaneously and a change between clients should take less than a second
- This depends on how we use sockets or long-polling
##### **To connect to a Splitty server of my choice without recompilation, so I can manage expenses**  
This almost works, the server gets pulled from the config file. 
- The header where you change the server should change the config file to the corresponding server. (for now just different ports).

##### To connect multiple clients at once to one server, so two users can use Splitty simultaneously
No implementation for this, so it should already work. (Logic and web sockets should make sure this works without flaws).

##### To create or join an event, so I can manage expenses for that event  
- Home screen: add a "join event" button which adds that event to the config file and screen.
- Make the "create event" screen
- Add logic to the "add event" button and connect it to "create event"

##### To see an invite code in the overview of an event that I can give to others, so they can join 
- Make the invite code visible and copyable in all the headers (home and eventOverview)

##### To add/edit/remove participants to an event, so I can create an complete and accurate list
- "manage participants" does not yet show the including participants.
- "Delete" should take into account that: participant in an expense cant be deleted OR are also deleted from the expense

##### To give an event a title, so it is easy to differentiate two events AND To edit the title of an event, so I can fix typos
- Make a "title" field in "create event" screen.
- add an "edit" to the event title so it can be changed.

##### To see all created expenses for the current event, so I see what has already been entered 
DONE.

##### To add new expenses to the event, so I can add missing items or activities.  
- Connect "manage" expense button in the event overview to yet to be created screen  "manage expenses"
- add logic to the "+" add expense button and connect it to yet to be created "add expense" screen
- Inside "manage expense" It should be clear which are participants and who is the receiver. ALSO the receiver should not be able to be a payer.

##### To edit or remove existing expenses, so I can correct mistakes  
- Make a "Delete expense" functionality and popup (inside manage expenses?)

##### To see the total sum of all expenses, the share per person and how much each person owes to/is owed by the group, so all participants can meet and settle their debts.  
- Make an "OpenDebts" screen which has sum of all expenses and all existing debts.
- Make it able in "Open debts" to settle a debt
- Show "share per person" (whatever this means?)

##### To switch between events, so I can manage expenses of multiple events in the same session 
- fix the back button in event overview so you can go to a different event

#### To switch between English and Dutch, so I can use my preferred language. 
- Make language icons for dutch and english (or dropdown or something creative)
- Make selecting a language set this language to the config file
- Make the entire program load the correct language (can also be live is not a basic requirement)


**ADMIN**  
##### To login into a password-protected management overview, so I can manage my server instance 
DONE.
(What does server instance mean? Does this change anything?)
##### To see a random password in the server output, so I can login to the management overview.
DONE.
##### To see all events of the server, so I can explore the existing data.  
DONE.
##### To order all existing events by title, creation date, and last activity, so I can find specific events  
DONE.
##### To delete events, so I can clean up the database
DONE.
##### To download a JSON dump of a selected event, including all details, so I can create backups  
DONE.
##### To import an event from a JSON dump, so I can restore backups.  
DONE.

----
RUBRIC IMPORTANT:

TECH
- Both client and server contain at least one example that uses DI to connect dependent components.  
- Static fields and methods are only sparely used to access other components.
- The application contains functionality that uses 1) a REST request AND 2) long-polling AND 3)  
	websocket communication (in different places).
	

TESTING
- All conceptual parts of the application have several automated tests (client, server, commons)
- The project contains unit tests, but only for classes without dependent-on-components
- The project contains at least one exemplary test that goes beyond asserting direct input/output of  
	a system-under-test. For example, by asserting indirect input, indirect output, or behavior.
- The project contains one automated JUnit test that covers one use of one endpoint

USABILITY
- Do the different Scenes consider a sufficient color contrast for visually impaired users?
- he essential functions are reachable via keyboard shortcuts (i.e., back to overview over previous events,  
	create a new event, add an expense to current event, show statistics for current event)
- Are 3+ elements of the interface visualized in a multi-modal fashion, e.g., including appropriate icons in  
	addition to text, or using complementing color coding?
- the navigation logical and consistent throughout the application and across different scenes?
- Application should, at the very least, support the following flows without requiring mouse input. The descrip-  
	tion assumes a start from the home screen (overview over recently edited events):  
	– Creating a new event, adding an expense, returning back to the home screen.  
	– Open one of the recently edited events, edit an expense amount, returning back to the home screen.
- All change operation in expenses should have an undo operation (e.g., amount, payer, title, etc.).  -
- It should be possible to go back more than one step.  
- The remembered operations can be invalidated when the user switches to another event
- least two types of errors should be handled by the application:  
- Invalid entries in the expense form should not get accepted (e.g., negative values for an expense)  
- Unavailability of the server should be caught and the application should be put back to the start screen.  
	If the server is unavailable, users should be informed about this in a popup.  
-  Are application errors presented appropriately to the user, e.g., in a pop-up or via a marker in the input field?  
- Are error messages descriptive and help users to understand and/or resolve the issue?
- Is there feedback for 3+ actions that inform users about the result and effect of the action?  
- The feedback can be provided in several forms: screen content changes, labels in the application show a  
	message, self-destructing notification pop-ups, changed button text, etc.  
- Example actions: expense has been added, email has been sent, deletion was successful, etc
-  Is there a confirmation dialog for 1+ irreversible delete operation (e.g., when deleting an event)?




----
**BUGS**
- euro symbol does not show correctly in 
- back button invisible in admin overview


