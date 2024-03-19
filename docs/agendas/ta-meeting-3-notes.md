|              |                       |
|--------------|-----------------------|
| Date:        | 27-2-2024             |
| Time:        | 13:45 - 14:30 (45min) |
| Location:    | Drebbelweg-PC Hall 1  |
|              | Cubicle nr. 7         |
| Chair        | Sem van der Weijden   |
| Minute Taker | Wing Wong             |
| Attendees:   | Sem van der Weijden   |
|              | Wing Wong             |
|              | Xiaoyu Du             |
|              | Rens Pols             |
|              | Eva Miesen            |
|              | Lukas Milieška       |

## START (15 min)
- **Opening by chair (1 min)**
    - We are starting well on time.
- **Check-in: How is everyone doing? (2 min)**
    - Everyone is in a good mood and ready for this meeting.
- **Announcements by the team (2 min)**
    - On closing branches after merge request; since we're working according to the principle
    - that general issues should be split into many small issues, a branch should never remain after being merged once.
    - Therefore, a merge request should go alongside the closing/pruning of the branch that deviates from main.
- **Summary of last week's sprint (3 min)**
    - Everything is well-discussed, and concerns for dependencies on other people to be done were well-communicated
    - in WhatsApp. Certain classes require additional testing.
- **Approval of the agenda - Does anyone have any additions? (1 min)**
    - No one has additions for now.
- **Approval of last minutes - Did everyone read the minutes from the previous meeting? (1 min)**
    - Last week's notes were well-received, and the remarks by the TA were addressed.
- **Announcements by the TA (2 min)**
    - The TA stresses the importance of having a correct name and email set up for git commits.

- **Presentation of the current app to TA (3 min)**
    - Rens volunteers to demonstrate understanding of the current status of the class structure.
    - Later, we decide that everyone should speak for their own piece of code since they are most familiar with it.
    - Events have constructors that need to be fixed.
    - Merge.

## Talking Points (22 min)
- **Talking Points: (Inform/brainstorm/decision-making/discuss)**

    - <Agenda -Schema> Schema (7 min)
        - We are going to use an H2 database.
        - We'll be implementing via JPA, which means each object/class will have its own relation in our database.
        - We will aim to set up our database with JPA within this sprint.
        - During the Friday meeting, we will make big strides for the database.
        - This was chosen since we require having learned about it in the lecture which is on Wednesday.
    - <Agenda -UX-Skeleton> UX Skeleton (7 min)
        - For this sprint, we will have the UX document initialized.
        - We have decided to distribute the load over Rens and Eva, with other members tuning in order to learn.
        - The full deadline is in two weeks but an informal deadline for the bare minimum is this sprint (this week).
        - We should have the UX before we start working.
        - We are using the Landscape orientation.
        - Validation of IBAN BIC etc is done both in the front- and backend (commons).
        - Controller backend.
    - NEXT WEEK: (time constraints)
    - <Agenda -Basic-Implementation> Basic Implementation (8 min)
        - What features/classes need to be fleshed out?
        - Currency is a difficult subject, what to do?

**NOTE: Anything that was skipped or unclear will be discussed in detail after the TA meeting.**

## Wrapping Up (8 min)
- **Summarize action points: Who, what, when? (5 min)**
    -  We will not use GitLab's features for estimating how much time it takes to implement an issue.
    -  The assignee is the person who works on the code.
    -  Two people will be the reviewers; we communicate who still needs to merge for the weekly knockout criteria.
    -  That person will merge the request itself.
    -  We should simply work on UX; proper estimates on when a deadline should be can only be agreed upon after the sprint
    -  today and the lecture tomorrow.
    -  The code of conduct is uploaded by Rens.
    -
    - Lukas will be head, Xiaoyu will be minute-taker next time.
    - The discussed rule of two reviewers will not be reflected in formal rules on GitLab, but a mutual respect towards the
    - rule.
- **Any Questions? (2 min)**
    - Next week feedback round.
- **Closure (1 min)**
