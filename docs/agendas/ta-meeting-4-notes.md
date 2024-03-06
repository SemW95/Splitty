|              |                      |
| ------------ | -------------------- |
| Date:        | 2024-03-05           |
| Time:        | 13:45 - 14:30 (45min) |
| Location:    | Drebbelweg-PC Hall 1 |
|              | Cubicle nr. 7        |
| Chair        | Lukas Milieška       |
| Minute Taker | Xiaoyu Du            |
| Attendees:   | Sem van der Weijden  |
|              | Wing Wong            |
|              | Xiaoyu Du            |
|              | Rens Pols            |
|              | Eva Miesen           |
|              | Lukas Milieška       |

## START

- **Check-in**
  - TA mentioned that: Add comments and add review are the same thing. Members can do any of them.
- **Summary of last week's sprint**
  - IBAN, BIC check (Sem, Wing): They added some methods to make sure that IBAN is valid.
  - Split pipeline into multiple jobs (Eva): She split the pipeline into three stages of compile, checkstyle and test.
  - Fix class structure (Everyone): Some members are confused on the class structure about debts and how to pay. This week we fixed the problems and built a new class structure.
  - Delete old template files (Lukas)
  - Convert most classes to JPA entities and add repositories for them (Everyone): Members added entities and relations to the classes and added the corresponding repository and interface method, like findPeopleByFirstNameContainingIgnoreCase() created by Wing.
  - Currency conversion rate (Lukas): He created some function of currency using Frankfurter Api (https://www.frankfurter.app/).
  - Person controller, service (Sem): He completed the controller of Person and the corresponding server and repository.
  - Tests for classes and repositories (Everyone)
  - Seeder class (Lukas): It is used to populate initial data into the database.
- **Issues/MRs from the previous sprint which haven't been done**
  - GitLab triage bot: Rens added something in Gitlab that can automatically apply the missing labels.
  - Tests: Members decided that tests are not mandatory. If you can make sure that the method works, you are not forced to add a test for it. But we recommend you to add a TODO comment or create an issue as a reminder.
  - A few JPA entities and their repositories not completed: Members will complete them this week.
- **Remarks from the previous sprint which need discussing**
  - Event's code generation: Now, the UUID is too long. Maybe change to something with RandomStringUtils. We cannot test that part, but it is fine.

## Planning the upcoming sprint

- **UI**
  - What scenes will we have?
    - Home screen: Settings, in which users can change the language.
    - Event: Two ideas: 1)The event overview and the event editor are combined in one screen. 2) The event overview and the event editor are separated into two screens (using a new screen or popup)
  - Some advice to know:
    - The scene don’t have to be very nice now. Working properly is important.
    - Something useful in Monday’s lecture: Consider the keyboard deadlocks.
    - We need some symbols, like language buttons and go back button/go home button.
  - Reminder (things that can be considered later):
    - Invite code and invite button.
    - Add the tags later, as it is a feature.
    - Something need to be learnt: Popup and the new screen.

## Wrapping Up

- **Decide on next chair and minute taker (1 min)**
  - Eva Miesen will be the chair and Sem van der Weijden will be the minute taker next time.

