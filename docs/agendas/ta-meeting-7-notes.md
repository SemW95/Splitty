| Key          | Value                              |
| ------------ | ---------------------------------- |
| Date:        | 26-03-2024                         |
| Time:        | 13:45 - 14:30 (45 min)             |
| Location:    | Drebbelweg-PC Hall 1 Cubicle nr. 7 |
| Chair        | Xiaoyu Du                          |
| Minute Taker | Lukas Milieška                     |
| Attendees:   | Wing Wong                          |
|              | Xiaoyu Du                          |
|              | Rens Pols                          |
|              | Lukas Milieška                     |
|              | Sem van der Weijden                |
|              | Eva Miesen                         |

## START (18 min)

- **Opening by chair (1 min)**
- **Check-in: How is everyone doing? (1 min)**
  - Did anybody not understand something?
  - Did everybody's task feel managable?
- **Summary of last weeks sprint (3 min)**
  - Xiaoyu: advice - add descriptions to merge requests and pay attention to milestones
  - Most of the UI has been built:
    - Admin Overview Screen: Luke
      - Upon entering a randomly generated password in an admin credential popup, it shows an admin overview screen which lists all events. The admin can order, delete, download and import them.
    - Expense Overview Screen & Manage Expense Popup: Wing
      - Connected to the database (persists data changes). Still need to add/fix some things (validation, clarity)
    - Event Overview Screen & Expense Card Popup: Eva
      - Created a `.fxml` file for a card component because they expense cards need to be created dynamically
      - Anna note: should maybe check for colour contrast
    - Add Participant Popup, Edit Participant Popup, Manage Participants Screen & Delete Participant Confirmation Popup: Xiaoyu
  - Some other works:
    - Added dummy data initialized and reset on startup: Sem
      - Added more initial data to the seeder and wrote tests to check if it is seeded correctly
    - Pickuped all the small TODO's in the project: Rens
    - Splitted the tests into parallel jobs: Rens
    - Test for Expense JPA: Wing
    - Mockito tests for the personService class: Sem
      - Mockito - framework for testing, uses depedancy injection to mock objects
- **Issues from the previous sprint which haven't been done (2 min)**
  - Tag Creation UI has not been created as an issue
  - The size of screen
    - Consensus: 1000x600
- **Approval of last minutes - Did everyone read the minutes from the previous meeting? (1 min)**
  - Is everything clear? Any feedback?
- **Announcements by the TA (2 min)**
  - Anna will briefly talk about testing rubrics at the end of the meeting
- **Presentation of the current app to TA (8 min)**
  - Show every screen and popup seperately.
    - Screens have not been connected yet.

## Talking Points (22 min)

- **Some details about UI (_discuss_)(10 min)**

  - Usability

    - Need to improve the interface this week

    - Color Contrast (_discuss_)
      - Need to look more into it. Maybe have a theme colour? We should always a high contrast. Tags have their colour, but the text colour of that should
    - Keyboard Shortcuts (_discuss_)
      - Delete, Ctrl+Z
    - Keyboard Navigation (_discuss_)
      - Tab, Shift+Tab, Enter, Esc. The tab order depends on the hierarchy in the scene builder/fxml file maybe?
    - Supporting Undo Actions (_discuss_)
      - Have a Ctrl+Z to undo some actions (change title, create expense). Probably do this later (after the UI is created fully and screens are linked). Could store a list of changes so it's easy to undo to a previous state.
    - Error Messages & Informative Feedback & Confirmation for Key Actions
      - Delete confirmation popups should be commonly used.

- **Plan of this week's work (_discuss and decision-making_)(9 min)**
  - Product Pitch: update the presentation draft to BrightSpace (Deadline: 28th March, this Thursday)
    - We should take it seriously even though it's formative. The slides should be simple, not contain too much technical information. A google slides document will be created and shared it with the team. Eva
  - Tag Creation UI
    - Maybe create a simple UI for now if it's difficult to make a tag very customizable. Xiaoyu
  - Connect all the screens and popups
    - High priority. Sem, Luke and others will help
  - Usability of UI: improve the interface
    - Everyone will work on their respective tasks and improve their screen UI.
  - Write business logic code & Connect UI to backend code
    - Some missing endpoints can already be created. Mostly Rens
  - Have an MVP by 2024-04-06
- **Code of Conduct (_discuss_)(3 min)**
  - Does anyone have any advice on the Code of Conduct?
    - There have been instances people of implementing features before discussing how the implementation should look/work.
    - Changes the team's communaction: WhatsApp is used for everything, Discord for online meetings, Mattermost only for communication with TA
    - Add something to the code of conduct about writing comments.
- **NOTE: anything that was skipped or unclear will be discussed in detail after the TA meeting.**

## Wrapping Up (5 min)

- **Decide on next chair and minute taker (1 min)**
  - Chair: Wing
  - Minute taker: Rens
- **Any final questions? Do we still need another meeting this week?(3 min)**
- **Closure (1 min)**
  - Anna notes on testing:
    - Should write tests for the client side
      - Just functionality
    - Should add unit tests for server since we currently have integration tests
