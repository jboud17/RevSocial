## Database

- Tables involved - Users, Posts, Post_Likes (Lookup Table)
	
- User Table had the primary key field of user id
	- A hash string value to later be used for retrieving the image from the S3 
	- His/Her first name, last name, password, email and birthdate
		
- Post Table had the primary key of post id
	- A hash string value to later be used for retrieving the image from the S3 
	- His/Her post's text, title and user id
		
- PostLikes is a lookup table having 3 columns
	- Every time a post is liked by some user, an entry is inserted to the table
	- This entry includes a unique postLikeId, the user id, and post id


## Backend

- Beans
	- We have 3 beans; UserNew, Post, and PostLikes
	
	- The bean fields are synonymous with the columns of the correspondingtables in the database.
	- The User bean has the fields:
		- user id, hash string value, first name, last name, password, email and birthdate
	- The Post bean has the fields:
		- post id, hash string value, post's text, title and user id
	- The PostLikes bean has the fields:
		- post id, user id

- SocialWebsiteApplication
	- Our @SpringBootApplication class, just calls SpringApplication.run()

- Controllers
	- We have 4 controllers: one for each bean, then a FrontController
	- The FrontController class is just a method to add headers to the requests to allow Angular access
	- Each bean controller contains methods to deal with requests to certain URLs on the server (detailed below)
		- postController, userController, postLikeController	
		- Manage transforming database information into objects readable by Angular (generally JSONs)

- Repositories
	- One for each bean
		- postRepo, PostLikeRepo, userRepo
		- Implement CrudRepository in order to gain basic CRUD functionality in the tables
		- Set map for any other database accessing required by controllers for their appropriate tables 

- Services
	- One for each bean
		- postService, PostLikeService, userService
		- Uses autowired repositories to get information from the database and return them to the Controllers

- Utilities
	- S3Bucket - has two methods:
		- uploadToS3(byte[] fileByteArray) -> generates a random hash, uploades the byte[] to S3 with the hash appended to the url, returns the hash
		- randomHash() -> returns a randomly generated hash for uploadToS3()
	- SessionServlet - 
		- Writes information needed from the current session into a printwriter for Angular to use

## Paths to call
- All paths will be localhost:8080/SocialMedia/*

#### Get methods
- /session -> Returns a JSON for the currently logged-in user's information
- /allPosts -> Returns a JSON for all the posts in the database and their information
- /likePost -> Returns a success/fail JSON for attempting to add a PostLike table row
- /allUsers -> Returns a JSON of all the users in the database (DOESN'T include their passwords)
- /logout -> Invalidates the HttpSession, redirects to Login Page

#### Post methods
- /NewPostServlet -> 
	- Given information: String posttext, String title, MultipartFile file
	- Takes information from new post form submission
	- Translates the file input to byte array and uploads to S3
	- Inserts new row into Posts table
	- Redirects to Home Page
- /registerUser ->
	- Given information: String firstname, String lastname, String username, String password, String email, String date
	- Takes information from register user form submission
	- Converts the string from date input into a Timestamp object for the database
	- Creates new UserNew object, saves into database
	- Redirects to Login Page
- /updateInfo ->
	- Given information: String firstname, String lastname, String email
	- Takes information from submitting the left form on the angular updateInfo page
	- Finds the currently logged in user in the database, updates their information to match the given info
	- Redirects to Home Page
- /login ->
	- Given information: String username, String password
	- Takes information from login form submission
	- Attempts to find the user with the given username/password in the database
		- If successful, redirects to Home Page and sets HttpSession values
		- If failed, reloads the Login Page
- /resetPassword ->
	- Given information: String password
	- Takes information from submitting the right form on the angular updateInfo page
	- Updates the password for the logged-in user in the database
	- Emails the logged-in user about the password change
	- Redirects to Home Page
- /profilePic ->
	- Given information: MultipartFile file
	- Takes information from submitting the form that appears on the Home Page when the user has no profile picture saved
	- Converts the file input into a byte array, uploads to S3
	- Updates the logged-in user to store the hash to the newly uploaded image
	- Redirects to Home Page
