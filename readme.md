# Revature - Project 2 - Social Media Website

## See the readme files within /java and /Angular for specific information on those implementations

## To run:

*You'll likely be able to skip these first two steps if you've already installed ojdbc8 locally for another project*
1. Download ojdbc8.jar onto your computer from the [Oracle website](http://www.oracle.com/technetwork/database/features/jdbc/jdbc-ucp-122-3110062.html)
2. To Maven install the jdbc driver so it works for this project:
```
mvn install:install-file -Dfile={Path/to/your/ojdbc8.jar} -DgroupId=com.oracle -DartifactId=ojdbc8 -Dversion=12.2.0.1 -Dpackaging=jar
```
*You might need to replicate these steps for our aws-java-sdk dependency as well*

3. Open the /java directory in STS
4. Import it as an existing Maven project
5. Run on Tomcat Server

6. Open the /Angular directory in VS Code
7. Run "npm install" to get node_modules and everything else needed to run the angular
8. Run using "ng serve"

## Default values to use
- User (id 3):
	- username: joe
	- password: @@
	- name: Sam Mendez
	- email: sammymendino@gmail.com
	- email password (if you want to check that emails are sent): 3mailpass
- User (id 5):
	- username: jim
	- password: @!
	- name: Jim Jam
	- email: non-existant email (stored as jimjam@email.com, not an actual email)

## Page definitions
*All pages will start with http://localhost:4200 in the url*
- 404 page (any url not mentioned below)
	- Displays our custom 404 page
- Login page (/login or /)
	- Displays a form for logging in
	- Login button will attempt to validate the user's login
		- If successful login, redirects to Home Page
		- If failed login, reloads Login Page
	- Register button will take the user to the Register Page
- Register page (/register)
	- Displays a form for registering a new user
	- Register button will submit the form and create a new user for the database

#### All of the pages below will display this header:
- Header: 
	- On any page other than a specific user's profile page, displays logged-in user's profile pic and name
	- On specific user's profile page
	- Left and middle buttons change based on which page the user views
		- On Home Page: left takes you to Base profile page, right takes you to Update info page
		- On either Profile Page: left takes you to home Page, right takes you to Update info page
		- On Update info page: left takes you to home Page, right takes you to Base profile page
	- Right button always logs out the user, takes them back to the login page
	- Search bar suggests any users that have a username, first name, or last name that starts with the input
		- Clicking on the suggestion takes you to the user's specific profile page

- Home page (/home):
	- If the user doesn't have a profile picture saved in the database:
		- Asks the user to upload a profile picture before continuing
	- If the user has a profile picture:
	- Displays the posts from every user
	- Allows the user to upload a new post
- Base profile page (/profile)
	- Displays the logged-in user's posts
	- Allows the user to upload a new post
- Specific profile page (/profile/{{username}})
	- Displays the posts from the user with the username in the url
	- If the user has no posts, displays a heading saying so
- Update info page (/updateInfo)
	- Left form autofills with the logged-in user's information
		- Submitting this form updates their information in the database
	- Right form allows the user to change their password
		- Submitting this form updates their password in the database and sends them an email