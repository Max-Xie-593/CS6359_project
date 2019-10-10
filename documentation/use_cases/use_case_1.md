**Name:** Sign In
**Description:** The app needs to be able to sign in administrators, tutors and students to the system using their unique IDs.
**Actors:** Administrators, Tutors, Students
**TUCBW:** A person enters their unique id.
**TUCEW:** A person reads a message that they have successfully signed in.
**Preconditions:** If the person has never signed in before, they are a student. The original administrator is set by the system creators, any additional admins are added by the admins. Mentors get added by the admins as well.
**Postconditions:** Person will be signed into the system.
**Main Course:** 
1. The person will enter their unique id.
2. The system will ask the person if they want to sign-in or check-in.
3. The person selects the sign-in option.
4. The person has signed in before, so the person's unique id will be checked to see if they are an admin, tutor, or student.
  4a. If the person's unique id corresponds to an administrator, the system will say Welcome to the Admin page <admin_name>!
  4b. If the person's unique id corresponds to an tutor, the system will say Welcome to the Tutor page <tutor_name>! 
  4c. If the person's unique id corresponds to an student, the system will say Welcome to the Student page <student_name>!
5. The person reads a message that they have successfully signed in.

**Alternate Course:**
1. The person will enter their unique id.
2. The system will check if their unique id is already in the system.
3. The person's id will not be in the system.
4. The person has not signed in before, so the system will ask the person what their name is and add their unique id to the system as a Student. 
5. The system will say Welcome to the Student page <student_name>!
6. The person reads a message that they have successfully signed in.

**Exceptions:** If the person enters an invalid id, the system will inform the person they entered the invalid id.