| Key | Value |
| --- | --- |
| Date: | 9-04-2024 |
| Time: | 13:45 - 14:30 (45 min) |
| Location: | Drebbelweg-PC Hall 1 Cubicle nr. 7|
| Chair | Eva Miesen |
| Minute Taker | Wing Wong |
| Attendees: | Sem van der Weijden |
|            | Xiaoyu Du |
|            | Rens Pols |
|            | Lukas Milieška |
|            | Wing Wong |
|            | Eva Miesen |

## START (15 min)
- **Opening by chair (1 min)**
- **Check-in: How is everyone doing? (2 min)**
    - Any uncertainties?
    - All tasks felt manageable?
- **Announcements by the team (2 min)**
- **Summary of last weeks sprint (3 min)**
    - Create endpoints and some tests for the endpoints (Rens Pols)
    - Event Creation UI (Eva Miesen)
    - Changed all paths for the icons (Eva Miesen)
    - Add TagEdit (Xiaoyu Du)
    - Join an event by its invite code (Sem van der Weijden)
    - Expense error confirm alerts (Wing Wong)
    - Long-polling for server status (Sem van der Weijden) (was actually polling)
    - Keyboard Navigation (Wing Wong)
    - Language Switching (Lukas Milieška)
    - Websockets (Lukas Milieška, Sem van der Weijden, Eva Miesen)
    - Update the Event Screen Overview (Xiaoyu Du)
    - Add functionality for creating expenses (Lukas Milieška)
    - Language selection (Sem van der Weijden)
    - Add a label in Event Overview for amount spent per event (Lukas Milieška)
    - Undo user actions with Ctrl+Z for updating (so not for everything) (Lukas Milieška)
    - Convert polling to long-polling (Lukas Milieška)
- **Issues from the previous sprint which haven't been done (2 min)**
    - Update code of conduct (Eva Miesen)
    - ExpenseOverview, -Manage, -AddParticipant clientside testing (Wing Wong)
    - Open debt UI (Lukas Milieška)
    - ManageExpenseList screen (Xiaoyu Du)
- **Approval of the agenda - Does anyone have any additions? (1 min)**
- **Approval of last minutes - Did everyone read the minutes from the previous meeting? (1 min)**
    - All clear? Any feedback?
- **Announcements by the TA (2 min)**
- **Presentation of the current app to TA (3 min)**

## Talking Points (25 min)
- **Talking Points: (Inform/ brainstorm/ decision-making/ discuss)**
    - **Extra Features**
        - How much time can everyone invest this week? (Formal deadling: Friday 12th of April) (_inform_)
        - Which extra features do we want to finish? (_discuss + dicision-making_)
            - Live Language Switch: achievable
                - To generate an empty language template from the language selection, so I can contribute a new language to the project
            - Detailed Expenses: achievable
                - Still need to implement filters --> add a dropdown with all participants and radio buttons
            - Foreign Currency: not achievable
            - Open Debts: achievable
                - UI still has to be done
                - All logic is done
            - Statistics: achievable
                - To have the three standard tags 'food', 'entrance fees', 'travel' for each new event, so I can classify the expenses. (Event)
                - To add new tags, so I can define my own expense types (logic done).
                - Set up logic for Statistics.
                - To define or change the colour of a tag, so I can use my preferred colour coding.
                - To rename or delete tags, so I can pick tags that are most useful for my event.
            - Email Notification: not achievable
    - **Finish for basic requirements**
        - Decide if mouse icon changes on hover on a button (1) (_discuss + dicision-making_)
        - ExpenseManagement: Make it more clear that you have to press enter to submit the change --> maybe add a buton? (2) (_discuss + dicion-making_)
        - UI Finishing touches:
            - Everywhere: Make Application stop "run" on closing (it still runs in the background for some reason) ★★★☆☆(person)
            - ExpanseOverview: Euro symbol is broken ★★★☆☆(person)
            - Everywhere: Words get clipped on translation ★★☆☆☆(person)
            - Everywhere: Check translations to be correct ★☆☆☆☆(person)
            - ExpenseManagement: Invalid syntax pop-up shows up multiple times ★★☆☆☆(person)
            - Everywhere: Make pop-ups show neatly inside "current" pane not somewhere random ★☆☆☆☆(person)
            - EventOverview: Make the whole expense clickable not just the name ★★☆☆☆(person)
            - Everywhere: Implement outcome of the discussion of point 1 ★☆☆☆☆(person)
            - HomeScreen: make it so "enter" key works on event code ★★☆☆☆(person)
            - ExpenseManagement: Implement outcome of the discussion of point 2 ★☆☆☆☆(person)
            - ExpenseManagement: Make sure you can choose the tag "travel" (the other two can already be chosen) ★☆☆☆☆(person)
            - Manage expense button in Event Overview ★★☆☆☆ (Xiaoyu Du)
            - EventOverview: Get rid of the payment buttons or add label with "Coming Soon" ★☆☆☆☆(person)
            - EventOverview: Have statistics and unusable buttons say something like "Coming Soon" ★☆☆☆☆(person)
            - AdminOverview: Have a default for the choice order boxes ★★☆☆☆(person)
            - Everywhere: Have tab navigation show up from the first tab click (now it shows from start) ★★☆☆☆(person)
            - EventOverview: Make "Splitty: Going Dutch" not a button anymore ★☆☆☆☆(person)
            - EventOverview: Make tab traversal nicer ★☆☆☆☆(person)
        - Warnings to be resolved:
            - On going from an event overview to the home screen, '-fx-text-background-color' errors are received (person)
            - On startup there are enum warnings (person)
            - On language change "client/css/globals.css" not found (person)
            **NOTE: anything that was skipped or unclear will be discussed in detail after the TA meeting.**

## Wrapping Up (5 min)
- **Summarize action points: Who , what , when? (2 min)**
- **Feedback round: What went well and what can be improved next time? (3 min)**
- **Any Questions?: Does anyone have anything to add before the meeting closes? (2 min)**
- **Closure (1 min)**
