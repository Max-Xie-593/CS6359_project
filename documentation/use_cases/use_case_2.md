**Name**: Tutor Check-in

**Description**: Tutors should be able to check-in.

**Actors**: Tutors

**Related to**: Issue #1 

**TUCBW**: A tutor clicks the check-in button.

**TUCEW**: A tutor reads a message that they have successfully checked in.

**Preconditions**: The person trying to check-in is a tutor. They have already signed in.

**Postconditions**: Students checking in can now match with this tutor when the tutor is available.

**Main Course**:

1. The tutor selects the check-in option.
2. The system adds the tutor to the list of checked in tutors on duty.
3. The tutor reads a message that they have successfully checked in.

**Alternate Course**: N/A

**Exceptions**: N/A

**NAME**: Tutor Check-out

**Description**: Tutors should be able to check-out.

**Actors**: Tutors

**Related to**: Issue #1 

**TUCBW**: A tutor clicks the check-out button.

**TUCEW**: A tutor reads a message that they have successfully checked out.

**Preconditions**: The person trying to check-out is a tutor. The tutor has already signed in and checked in.

**Postconditions**: Students checking in can not match with this tutor.

**Main Course**:

1. The tutor selects the check-out option.
2. The system removes the tutor to the list of checked in tutors on duty.
3. The tutor reads a message that they have successfully checked out.

**Alternate Course**: N/A

**Exceptions**: N/A
