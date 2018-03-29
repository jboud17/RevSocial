## Database

- Tables involved - Users, Posts, Post_Likes (Lookup Table)
	
- User Table had the primary key field of user id
	- A hash string value to later be used for retrieving the image from the S3 
	- His/Her first name, last name, password, email and birthdate
		
- Post Table had the primary key of post id
	- A hash string value to later be used for retrieving the image from the S3 
	- His/Her post's text, title and user id
		
- PostLikes is a lookup table having 2 columns
	- Every time a post is liked by some user, an entry is inserted to the table
	- This entry includes the user id and post id


## Backend

- Beans
	- We have 3 beans; User, Post, and PostLikes
	
	- The bean fields are synonymous with the columns of the correspondingtables in the database.
	- The User bean has the fields:
		- user id, hash string value, first name, last name, password, email and birthdate
	- The Post bean has the fields:
		- post id, hash string value, post's text, title and user id
	- The PostLikes bean has the fields:
		- post id, user id

- DAO
	- We have 3 DAOs; Post contains all the post related methods, User contains all user related methods, PostLike contains all methods related to liking posts
	
	- PostDAO contains the methods:
		- getAllPosts() -> return a list of all the posts ever created
		- getPostsByUserID(int userId) -> returns a list of all posts made by a certain user of user id 'userId'
		- getPostsOfLoggedInUser(HttpSession httpSession) -> returns a list of all posts made by the user that is currently logged in
		- postLikes() -> returns the number of likes on a post
						
	- UserDAO contains the methods:
		- makeUser(int user_id, String first_name, String last_name, String username, String password, String email, Timestamp birthdate) -> returns a boolean of whether the new user was created successfully or not
		- login(String username, String password) -> returns the logged in user, if found in the database, null otherwise
		- updateDetails(int userID, String first_name, String last_name, String email) -> does not return anything, but does update the database with any new values given by the user
		- resetPassword(String username, String password) -> does not return anything, but does securely change the logged in user's password in the database
		- emailUser(String email, String password) -> does not return anything, but does email alert the user of changing their password
		- changePic(int user_id) -> does not return anything, but does change the profile pic of user with user id 'user_id'
		- viewMyProfile(String username) -> returns the user object in the database with the username 'username'
		- viewAProfile(String fname, String lname) -> returns the user object in the database with the first and last name 'fname' and 'lname'
		- viewFeed() -> returns list of posts containing all entries in the Posts table of the database
		- allUsers() -> returns a list of users containing all entries in the Users table of the database

	- PostLikesDAO contains the method:
		- insertRecord(int postId, int userId) -> returns success/failure boolean, insert a row into the Post_Likes table to indicate liking a post	
