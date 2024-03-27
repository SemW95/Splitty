| Key | Value |
| --- | --- |
| Date: | 19-03-2024 |
| Time: | 13:45 - 14:30 (45 min) |
| Location: | Drebbelweg-PC Hall 1 Cubicle nr. 7|
| Chair | Eva Miesen |
| Minute Taker | Sem van der Weijden |
| Attendees: | Wing Wong |
|            | Xiaoyu Du |
|            | Rens Pols |
|            | Lukas Milieška |
|            | Sem van der Weijden |
|            | Eva Miesen |

## START (17 min)
- **Opening by chair (1 min)**
- **Check-in: How is everyone doing? (2 min)**
    - Any uncertainties?
    - All tasks felt manageable?
- **Announcements by the team (2 min)**
- **Summary of last weeks sprint (3 min)**
    - Updated JPA to 3.2.0-M1 (Lukas Milieška): This update was needed since we use 'Instant' and we started experiencing problems with the database.
    - Nearly finished all tests for the JPA Repository classes:
        - JPA tests for the EventRepository class (Eva Miesen)
        - JPA tests for the TagRepository class (Xiaoyu Du)
        - JPA tests for the PaymentRepository class (and PaymentRepository class made aswell) (Eva Miesen)
    - Tests for the Event, Currency and Payment class (Eva Miesen)
    - Changed the date storage for the Event and Expense class (Rens Pols): 
        - Event class: LocalDate startDate, LocalDate endDate, Instant lastModifiedDateTime
        - Expense class: Instant paymentDateTime
    - Initialized home screen (Sem van der Weijden)
    - JFX internationalisation (Lukas Milieška)
    - Enable GitLab triageBot (Rens Pols)
    - Working first draft of UI Home (Sem van der Weijden)
    - BASIC UI Admin Credentials (Lukas Milieška)
    - BASIC UI Expense (Wing Wong)
- **Issues from the previous sprint which haven't been done (2 min)**
    - Most of the Basic UI issues
    - Issue JPA test Expense class
- **Approval of the agenda - Does anyone have any additions? (1 min)**
- **Approval of last minutes - Did everyone read the minutes from the previous meeting? (1 min)**
    - All clear? Any feedback?
- **Announcements by the TA (2 min)**
- **Presentation of the current app to TA (3 min)**
---
**Notes**:  
Overall tasks felt manageable and everyone was able to get there knockout criteria. According to the "technology" rubric we are a little behind on schedule so we might want to shift gears. Don't work on getting knock-out criteria but finishing the sprint.

There were some problems to discuss in the meeting after the TA-Meeting:
- Money symbols dont show up on windows, might be an UTF 8 problem
- UI issues with loading icons, and css not working correctly

We have discussed our icons to be stored with dimension: 48 x 48 pixels. This has to be retroactively fixed for existing icons.

## Talking Points (20 min)
- **Talking Points: (Inform/ brainstorm/ decision-making/ discuss)**
    - Behind schedule (compared to the planning on Brightspace) (*inform*):
        - Look at the assessment part of the Technology assignment
        - Focus on project completion over lines of code
    - Plan of action for the coming weeks (*discuss and decision-making*)
    - Divide UI issues and deadline (*decision-making and discuss*)
    - Formative deadlines on 22-03-2024 (Friday): HCI/Usability, Testing:
        - Read the corresponding assignments on Brightspace after the meeting (*inform*)
    - Code of Conduct: improve based on feedback from the TA and make changes if needed to ensure alignment with the reality (*discuss*)

---

**Notes:**   

**Connecting UI:**  
Make an issue for connecting the UIs and choose someone to do this to avoid conflicts.

**UI:**    
create/edit event (Sem)     
add/edit participant (Xiaoyu)       
admin overview (Luke)       
expense overview (Wing) Some details below:     
"Add Participant" is a pop op which replaces the expense overview pop-up.     
"Manage Expense" goes to new screen which actually manages the entire expense.
A participant will have format: @Person - Lastname
Event Overview (Eva)        
Finish Design (Rens)

**Endpoints:**      
Try and finish all needed endpoints for basic application, Sem and Luke already have experience in this if you need help. These are distributed based on self-assigning issues.

**Formative deadlines:**    
There is an upcoming formative deadline: for accessibility HCI (22 march) Also we had some previous formative deadlines "Tasks and planning" and "Technology". In order to make sure we keep our eyes on what needs to be done we advise everyone to read these to make sure we are on the same page. Before the end of the week.

**CoC grade:**  
We need to discuss if we need to adjust our CoC, it gets graded based on if you conduct yourself in the way the CoC says, so it needs to stay up to date.

We discussed everyone should read it this weekend so we can discuss its validity the week after.

**Testing:**        
Tests are taking a low-priority and are often not done. Create an issue for your tests after completing a task and assign it to yourself to make sure you do it.

**Different object views:**     
We need to find a way to implement the following: see different info in different screens of the same object. Wing got a beginning for this in the Expense Overview screen. After the meeting he could help us understand and think of a way to implement it.

**Rubrics:**        
We need to keep in mid these rubric items: Websockets/long-polling. Some ideas to implement this would be:        
Long-polling: debts management/server status        
Websocket: Database changes, with a refresh notification 

Will be discussed further afterwards.

## Wrapping Up (8 min)
- **Summarize action points: Who , what , when? (2 min)**
- **Feedback round: What went well and what can be improved next time? (3 min)**
- **Any Questions?: Does anyone have anything to add before the meeting closes? (2 min)**
- **Closure (1 min)**

---
**Notes:**       
For next agenda's there should notes about which tasks are for who and what the deadlines are. Icons are 48x48. And we focus more one sprint than on knock-out. Also keep testing!

Discussion after meeting:       
- Do we need a meeting on friday?
- Discuss long-polling/websockets
- How to fix money symbol issue
- UI issues with loading icons, and css not working correctly
- Explain object views in Java by Wing
- Explain database connections by Luke


Tasks:    
- Read CoC before next TA-Meeting (26 March)
- Work on UI (see notes above for distribution) (24 March)
- Work on endpoints (self-assign through issues) (24 March)
- Read formative rubrics before end of the week (23 March)
- Connecting all UI elements (24 March)
- Start discussion on websockets/long-polling

For next week:      
Chair: Xiaoyu       
MinuteTaker: Luke