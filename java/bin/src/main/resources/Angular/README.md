# Project 2 Angular

## Components

- HomePageComponent:
	- Displays the entirety of the home page (http://localhost:4200/home)
	- Holds a ProfileHeaderComponent, NewPostComponent, and PostViewComponent
	- Uses the CurrentUserService to set a reference to the currently logged-in user to avoid pinging the server every time
	- If there is no currently logged-in user on the server's HttpSession, alerts and kicks user back to login page
	- If the logged-in user doesn't have a profile pic:
		- Doesn't display the NewPostComponent or PostViewComponent
		- Still displays ProfileHeaderComponent
		- Shows a form for submitting a profile image (which will call the server to add it to S3 and save the hash in the DB)

- LoginPageComponent:
	- Displays the entirety of the login page (/login)
	- Calls server to validate login, which redirects to the home page if successful, or reloads login page if failed

- NewPostComponent: 
	- Displays the form for submitting a new post
	- Allows file upload as well as the appropriate text inputs for a post (title and summary)
	- Calls server to convert the form information into a new post object and save it in the DB

- PageNotFoundComponent:
	- Displays our custom 404 page (just an image and a heading)
	- Shown only when the user puts localhost:4200/* where * isn't registered in the Router yet

- PostViewComponent:
	- Displays the appropriate list of posts, depending on which page you're on (determined by looking in the url):
		- Home page (/home): Displays posts from all users (all in the database)
		- Base profile page (/profile): Displays only the posts from the currently logged-in user
		- Specific profile page (/profile/{{username}}): Displays only posts from the user with the username in the url
	- A post will display:
		- Its title
		- The username of the user that made the post
		- The image for the post (loaded from S3 via a hash that's stored in the Post bean and appended to the S3 url)
		- A post description uploaded from NewPostComponent
		- A like button, with a count for the current amount of likes for that post
	- Uses CurrentUserService to determine the logged-in user
	- Uses AllUsersService to get the list of all users (for specific profile pages, to match the user's username)
	- Uses AllPostsService to set and use the list of all posts to determine which to display in a much more timely manner
	- If appropriate, displays a heading stating that the page doesn't have posts to display (ex. if the logged-in user has no posts and is on the base profile page)
	- Handles all post like functionality via the like button (sending to server to insert into post_likes database table)

- ProfileHeaderComponent:
	- Displays the profile header at the top of all pages but /login and /register
	- Uses CurrentUserService to determine the logged-in user
	- Uses AllUsersService to set and use the list of all users
	- On all pages but the specific user profile page (/profile/{{username}}), displays the logged-in user's name and profile picture
	- On the specific user profile page, displays the currently-viewed user's name and profile picture
	- Button row (right button is always Logout):
		- On home page, displays buttons that link to /profile and /updateInfo
		- On either profile page, displays buttons linking to /home and /updateInfo
		- On update info page, displays buttons linking to /home and /profile
	- Manages the search user bar
		- Displays "Loading..." while loading all users from the db to search from
		- Displays "Search users" once done loading all users
		- Upon inputting a character into the search bar:
			- Searches the user list for users whose first name, last name, or username start with the current input in the search bar
			- Updates a list (usersForSearch) to display as suggested results (table rows displayed directly below the search bar)
			- Clicking on a suggested result takes you to the user's specific profile page (/profile/{{username}})

- ProfilePageComponent:
	- Displays the entirety of the profile pages (both /profile and /profile/{{username}})
	- Holds a ProfileHeaderComponent, NewPostComponent, and PostViewComponent
	- If on a specific user's profile page, doesn't display NewPostComponent

- RegisterComponent:
	- Displays the entirety of the register page (/register)
	- Contains a form for registering a new user
		- Submitting this form will call the server to create a new User and save into the database

- UpdateprofileComponent:
	- Displays the entirety of the update info page (/updateInfo)
	- Uses CurrentUserService to determine the logged-in user
	- Contains a form for updating a user's information
		- Autofills the form with the user's current information
		- Submitting this form will call the server to change the user's information in the database
	- Contains a second form for changing the user's password
		- Doesn't autofill with the user's password for security reasons (would need to give the password from back to front end)
		- Submitting this form will call the server to update the user's password in the database, and email them a notification that it has changed

## Guard

- LoggedInGuard:
	- Checks if the user's information is saved in the CurrentUserService
	- If CurrentUserService contains null information, alerts and kicks the user to the login page

## Model

- User: 
	- Stores all information needed for a user in the front-end:
		- id number, first name, last name, email, and image hash (for the S3 bucket)

## Services

- CurrentUserService:
	- Stores a user object that will indicate the currently logged-in user
	- Used to avoid pinging the server every time
	- Accessible in the components that have it injected
- AllUsersService:
	- Stores a list of users that will indicate all the users in the database
	- Used to avoid pinging the server every time in the components where it's injected (namely ProfileHeaderComponent)
- AllPostsService:
	- Stores a list of JSONs that will indicate all the posts in the database
	- Used to avoid pinging the server every time in the components where it's injected (namely PostViewComponent)