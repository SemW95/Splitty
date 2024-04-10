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
- **Announcements by the TA (2 min)**
- We have to write a README tutorial

## Talking Points (25 min)
- **Talking Points: (Inform/ brainstorm/ decision-making/ discuss)**
    - **Extra Features**
        - How much time can everyone invest this week? (Formal deadling: Friday 12th of April) (_inform_)
        - wing friday evening
        - xiaoyu saturday
        - rens saturday-sunday
        - eva friday evening
        - sem thursday-evening
        - lukas until sunday
        - Which extra features do we want to finish? (_discuss + dicision-making_)
        - Focus on the following extra features:
          - Open Debt
          - Detailed Expenses
          - Live Language switch
    - **Finish for basic requirements**
        - Decide if mouse icon changes on hover on a button (1) (_discuss + dicision-making_)
            - we will do this by adapting the stylesheet for buttons
        - ExpenseManagement: Make it more clear that you have to press enter to submit the change --> maybe add a buton? (2) (_discuss + dicion-making_)
            - Add a label to make it clear that you press enter to save
        - UI Finishing touches:
            - Everywhere: Make Application stop "run" on closing (it still runs in the background for some reason) ★★★☆☆(lukas, finished)
            - ExpanseOverview: Euro symbol is broken ★★★☆☆(Sem)
            - Everywhere: Words get clipped on translation ★★☆☆☆(WOMP WOMP)
            - Everywhere: Check translations to be correct ★☆☆☆☆(Eva)
            - ExpenseManagement: Invalid syntax pop-up shows up multiple times ★★☆☆☆(Wing)
            - Everywhere: Make pop-ups show neatly inside "current" pane not somewhere random ★☆☆☆☆(Xiaoyu)
            - EventOverview: Make the whole expense clickable not just the name ★★☆☆☆(Wing)
            - Everywhere: Implement outcome of the discussion of point 1 ★☆☆☆☆(Xiaoyu)
            - HomeScreen: make it so "enter" key works on event code ★★☆☆☆(Sem)
            - ExpenseManagement: Implement outcome of the discussion of point 2 ★☆☆☆☆(Eva)
            - ExpenseManagement: Make sure you can choose the tag "travel" (the other two can already be chosen) ★☆☆☆☆(lukas)
            - Manage expense button in Event Overview ★★☆☆☆ (Xiaoyu Du)
            - EventOverview: Get rid of the payment buttons or add label with "Coming Soon" ★☆☆☆☆(Eva)
            - EventOverview: Have statistics and unusable buttons say something like "Coming Soon" ★☆☆☆☆(Eva)
            - AdminOverview: Have a default for the choice order boxes ★★☆☆☆(WOMP WOMP)
            - Everywhere: Have tab navigation show up from the first tab click (now it shows from start) ★★☆☆☆(Wing)
            - EventOverview: Make "Splitty: Going Dutch" not a button anymore ★☆☆☆☆(Lukas Eva)
            - EventOverview: Make tab traversal nicer ★☆☆☆☆(Sem)
        - Warnings to be resolved:
            - On going from an event overview to the home screen, '-fx-text-background-color' errors are received (person)
            - On startup there are enum warnings (person)
            - On language change "client/css/globals.css" not found (person)
            **NOTE: anything that was skipped or unclear will be discussed in detail after the TA meeting.**

## Wrapping Up (5 min)
- **Summarize action points: Who , what , when? (2 min)**
- refer to the issues assignment above for content related issues
- Sem is planning on researching the clientside testing (mockito, testfx) on Thursday
- Make sure you create issues for all your responsibilities, with description, with frontend/backend labels.