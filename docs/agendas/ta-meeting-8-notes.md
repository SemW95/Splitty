| Key          | Value                              |
| ------------ | ---------------------------------- |
| Date:        | 02-04-2024                         |
| Time:        | 13:45 - 14:30 (45 min)             |
| Location:    | Drebbelweg-PC Hall 1 Cubicle nr. 7 |
| Chair        | Wing Wong                          |
| Minute Taker | Rens Pols                          |
| Attendees:   | Wing Wong                          |
|              | Xiaoyu Du                          |
|              | Rens Pols                          |
|              | Lukas Milieška                     |
|              | Sem van der Weijden                |
|              | Eva Miesen                         |

## START (5 min, starts 13:45)

- **Opening by chair (1 min)**
- **Check-in: How is everyone doing? (1 min)- Did everybody's task feel managable?**
- **Approval of last minutes - Did everyone read the minutes from the previous meeting? (1 min)**
- **Announcements by the TA (2 min)**

## Talking Points (35 min, starts 13:50)

- **Summary of work (20 min, starts 13:50)**
  - Initialize Tag Creation UI (Xiaoyu Du)
  - Integration Testing (Rens Pols & Sem)
  - Config file for storage of persistence of events by their code (Sem van der Weijden) 
  - Finalize Event Overview (Eva Miesen) - Making a seperate component for the expense card (since high detail)
  - Code of Conduct (Eva Miesen)
  - DEMO Home Screen UI (Sem van der Weijden)
  - DEMO Finalize Expense Overview & Manager - address last weeks remarks + improvements (Wing Wong)
  - DEMO Initialize and Finalize new Expense Add Participant (Wing Wong)
  - DEMO Connecting Scenes & Simplified pop-ups and scene handling (Lukas Milieška)

- **Bigger Picture (10 min, starts 14:10)**
  - Final coding deadline 14 April (end of week 9) - Can the TA confirm this official deadline?
  - Final Product Pitch 18th April 14:40
  - What did we fail on, except what we have already addressed?
    - **Human Computer Interactions**
      - Keyboard Navigation - Tab navigation
      - Supporting Undo Actions - Ctrl + Z
      - Error Messages - Invalid Syntax warning
      - Informative Feedback - Confirmation dialogue pop-ups
    - **Technology** (Done)
    - **Testing**
      - Coverage
        - Test coverage above 80% (high priority (as stressed by TA))
      - Unit Testing
        - Configurable subclasses to replace components we depend on in tests, for most tests - Ask TA what is meant
      - Endpoint Testing
        - Automated tests that cover regular & exceptional usage of endpoints - no progress yet
- **This week's sprint ordered by importance and star difficulty rating (5 min, starts 14:20)**

  - Join by invite code ★☆☆☆☆(person)
    - @Sem
  - Event Creation UI ★★☆☆☆(person)
    - @Eva
  - Event Rename UI ★★☆☆☆(person)
    - @Xiaoyu Du
  - Finalize event Manage Participants UI ★★☆☆☆ (person)
    - @Xiaoyu Du
  - Debt logic and UI ★★★★☆(person)
    - @Lukas
    - Is now two screens (paid off and open debts)
  - Server address in config ★☆☆☆☆ (person)
    - @Sem
  - Language Switching ★★★★☆(person)
    - @Lukas
  - Websockets and long-polling ★★★☆☆ (everyone wants to learn this)
    - @Eva
      - With assistants: @Sem, @Lukas, @Sem & @Wing
    - Long polling is used for server status
  - Endpoint Testing ★★★☆☆(everyone should learn this)
    - @Rens
    - Explain in Flux
  - Keyboard Shortcuts:
    - Keyboard Undo ★★★★★(person) - ctrl z
      - @Lukas
    - Keyboard Navigation ★★★★☆(person) tab, enter, escape
      - @Wing
  - Expand error alert coverage ★★☆☆☆ (everyone should add it to the UI's they made)
    - @* for their own UI
  - Confirm pop-ups ★★☆☆☆ (everyone should add it to the UI's they made) for Informative Feedback Rubric
    - @* for their own UI
  - Switch all ImageView elements ★☆☆☆☆(person) to use class path
    - @Eva
  - Clientside Testing (everyone) TestFX & (to be figured out)
    - @* for their own UI

  **-Sprint W9 expectations**

  - Finalize Product Pitch ★★★★☆ Slides, Demo, Script(Sem, Eva, Lukas, Xiaoyu, Rens, Wing)
    - Doable, but requires a lot of effort

  - MVP by sixth of April - is this still a realistic goal?

- **NOTE: anything that was skipped or unclear will be discussed in detail after the TA meeting.**

## Wrapping Up (5 min, starts 14:25)

- **Next Chair and Minute Taker (1 min)**
- **Feedback round: What went well this sprint and what can be improved for the next sprint? (3 min)**
  - address what large-scale changes we would make if we could start over
- **Closure (1 min)**


# Notes
## Objectives and Tasks

### Code of Conduct (CoC) Adjustments
Eva is currently busy modifying the CoC. Further discussion is required to determine if additional features need to be implemented.

### Endpoint Integration
Merge the endpoints branch as soon as possible to streamline development processes.

### Testing Framework
Continue using Mockito for testing purposes to ensure robustness and reliability of our code.

### Event Handling
Implement functionality to delete recently viewed Events. This will be based on the EventId specified in the config file. If an EventId is removed from the config, it will also be deleted for the user.
Ensure that all tags for an event can be retrieved efficiently.

### User Feedback
Implement confirmation text for any changes made by users to improve the user experience and prevent accidental modifications.

## Deadlines
### Coding Deadline
April 14th is set as the recommended deadline for completing coding tasks.
The official deadline is tied to the product pitch on April 18th.

## Questions about CoC
Further discussion is planned regarding the necessity of additional features for the CoC.

## Notes
### Technical Issues
Xiaoyu Du is encountering problems with her seeder, which may require attention to ensure development continuity.

## Next Week's Meeting
Chair: @Eva
Minute-taker: @Wing
