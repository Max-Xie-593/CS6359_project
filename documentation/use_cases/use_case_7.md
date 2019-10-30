**Name:** Student Leave Queue

**Description:** Students should be able to leave the queue if they no longer want to wait

**Type:** User-level

**Actors:** Student

**Blocked by:** Use Case #1, 2

**Related to:** Use Case #4, 6

**TUCBW:** The wants to leave the queue.

**TUCEW:** The student sees the welcome message.

**Preconditions:** Student is signed in. They are already in the queue waiting for a mentor. At least one mentor is checked in. No mentors are available. They see a leave queue button.

**Postconditions:** N/A

**Main Course:**

1) The student clicks Leave Queue button.
2) The system removes the student from the queue of waiting students.
3) The student is shown the welcome message and the get help button.

**Alternate Course:** N/A

**Exceptions:** The system cannot remove the user from the queue, and so the student sees an error message.
