## Functional Requirements

1) The app needs to be able to sign in administrators, tutors and students to the system using their unique IDs.
2) Tutors should be able to check-in and check-out, but admins should not.
3) Students should be able to check-out.
4) Student should be able to select a course they would like to be tutored in when the check-in.
5) Students should be able to check-in and be assigned a tutor (precondition: there is a tutor available).
6) Allow admin(s) to add, remove, or edit available tutors.
7) Admins should be able to edit tutoring schedules as tutors change their schedule, new tutors are hired or tutors quit. 
8) Keep track of the times when tutors are available. 
9) The app needs to be able to clock in and out tutors and keep track of hours worked.
10) The app needs to allow tutors to take a walk-in appointment if they are available, mark them as busy while tutoring, and mark them back as available when done tutoring. 
11) Students should be able to check-in and be added to a queue (precondition: there is not a tutor available).
12) Students should be notified if they are in the queue and are assigned a free tutor to help them (precondition: they were added to the queue).
13) The app needs to allow students to cancel existing requests if they are in the queue to be helped, but no longer need help.
14) When a student checks in and picks the subject to be tutored in, the system should pick an appropriate available tutor and match them up, and the tutor must accept this match. If the tutor does not accept the match, find the next available tutor and repeat until the match is accepted, the student is put on a queue, or the student cancels. 
15) The app needs to be able to send a notification to the tutor before their shift starts.
16) Allow admin(s) to add and remove other admins.

## Non-Functional Requirements
1) There are 3 different kinds of users: administrators, tutors, and students. 
2) Students must not be able to login and/or operate as an admin or tutor, and vice versa. 
3) The queue should have a maximum of 30 students, and once the capacity has been reached, we must reject students requests. 
4) Students cannot check-in during closed hours, or when no tutors are clocked in.
5) At least one tutor must be hired and scheduled for the system to operate, otherwise the system is closed. 
6) If a student wants to be tutored in a subject that none of the tutors clocked in are specialized in, the student gets matched with a random tutor or gets put on the queue accordingly.
7) A tutor can only help one student at a time.
8) A tutor can only be marked as available once they clock in. If they are not clocked in, they are not available, even if they are scheduled for that time. 
9) The app needs to notify tutors when there is a student signed in that matched with them. 
