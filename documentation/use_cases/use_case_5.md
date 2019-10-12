**Name:** Add/Delete/Edit Tutors

**Description:** Admin should be able to modify the list of tutors by add/delete/edit.

**Actors:** Admin, Tutors

**TUCBW:** Admin selects an “edit tutors” button (or something of that nature)

**TUCEW:** Admin sees changes reflected in the list of tutors.

**Preconditions:** admin signed in and is viewing the tutors list.

**Postconditions:** The changes will be viewed if a user traverses to the tutors list in the future.

**Main Course:**
1. Admin selects add/delete/edit (proceed to 2,3, or 4. Then skip to 5.)
2. Add subcase:

		2a. Admin adds a tutor with a name and unique ID.
		2b. System authorize the tutor and proceeds to add tutor to database.

3. Delete subcase:

		3a. Admin deletes an existing tutor with a name and Unique ID.
		3b. System checks if tutor in database and proceeds to delete tutor from database.
		
4. Edit subcase:

		4a. Admin requests an existing tutor with a name and Unique ID.
		4b. System checks if tutor in database and proceeds to bring up tutor page from database.
		4c. Admin edit page and proceeds to save page.

5. System processes request(s) and saves results into database.

**Alternate Course:** N/A

**Exceptions:** if an invalid ID is submitted in any of the subcases (Add/Delete/Edit), System should inform admin that is it invalid ID.
