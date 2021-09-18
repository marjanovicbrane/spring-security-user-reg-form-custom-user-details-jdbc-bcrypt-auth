# Spring CRM with Spring Security and user registration form (custom user details) using BCRYPT algorithm and JDBC authentication

In this project I used Spring Security for JDBC authentication and authorization with custom login page.
I applied authorization for appropriate user roles (EMPLOYEE, MANAGER or ADMIN).

![1](https://user-images.githubusercontent.com/61464267/133894398-2ad95442-8f03-4ef2-a26e-7156fa538c54.PNG)

I also made registration form, so we can register a new user and save it to the database.Every new user which we registered have EMPLOYEE role by default.In database we have already stored some users with role MANAGER and ADMIN, these users have also role EMPLOYEE.

This registration form have validation rules, because in this example we used Hibernate Validator.We made our custom validation rules, ie. custom java annoations.On the next picture we can see how registration form looks like, but first we will break this validations to se all error messages.

![2](https://user-images.githubusercontent.com/61464267/133895376-e52b2dea-e073-4ade-8ff0-8b8ae8d87bca.PNG)

Username field, can't have null value and must have min 1 char.I also checked the case if we enterd the username which already exists in the database, to show as an error message:"User name already exists".

Password field, can't have null value and must have min 1 char.

Confirm Password field, can't have null value and must have min 1 char also.
For these two fields, I made my custom validation rule and my custom annotation, because these two fields must match.If they don't match we will get an error message:"The password fields must match".

First name field, can't have null value and must have min 1 char.

Last name field, can't have null value and must have min 1 char.

Email field, can't have null value and must have min 1 char.Here I also made my custom validation rule and my custom annotation, for this field I used regular expressions for email.

When we enter a valid data in registration form, and when we press button register, then we saved all that user information into the database.In this case we made one database with following tables:user, role and user_role.

![2](https://user-images.githubusercontent.com/61464267/133896298-41d9d68a-8361-4182-8687-e73bc9dee8bd.PNG)![3](https://user-images.githubusercontent.com/61464267/133896310-a2e52a6b-580c-4d88-bde7-434e75ed8993.PNG)![4](https://user-images.githubusercontent.com/61464267/133896322-9ff1d7b6-9466-4768-a046-d2166f5e087f.PNG)

In this project as we can see I used bcrypt algorithm for password encryption, this is one-way encrypted hashing, so the password in the database can never be decrypted. To protect against CSRF attacks I used additional authentication data/token into all HTML forms.On this way we can prevent evil website to tricks us into executing an action on a web application that you are currently logged in.For each request we have randomly generated token and Spring Security verifies token before processing.

On the next picture we can see relationships between the tables.We made N:M-MANY TO MANY relationship between the tables user and role, because one user can have many roles and one role can have many users.To achieve this I made one more table, link table called user_role.

![5](https://user-images.githubusercontent.com/61464267/133896678-60110a13-30a7-4d89-802d-c9325958c656.PNG)

When we are logged in our app with appropriate username and password, we have security authorization, so the user with role EMPLOYEE can only see this page with all information about user, such is:username, role, first name, last name and email.

![6](https://user-images.githubusercontent.com/61464267/133896944-6283aad7-3328-4435-a6f8-9702927bc30b.PNG)

The user with role MANAGER have access some additional page Leadership Meeting.

![7](https://user-images.githubusercontent.com/61464267/133897121-245b2e7f-b973-464c-a194-fa5e8cf81572.PNG)

When we enter on that page we can see some additional information.

![8](https://user-images.githubusercontent.com/61464267/133897190-dff6ce06-554d-4ae4-9126-b45739742704.PNG)

The user with role ADMIN also have access some additional page IT Systems Meeting.

![9](https://user-images.githubusercontent.com/61464267/133897223-d2495a70-76c6-4cf9-a68a-be5cd5202392.PNG)

When we enter on that page we can see some following additional information.

![10](https://user-images.githubusercontent.com/61464267/133897271-fed5c538-814b-41f2-a742-a6fb0e86beff.PNG)

I also added logout button, because we want to logout the user from the system, on that way we also removing http session, cookies, etc…

If some other user which is not authorized trys to access some additional information and pages, he will get access denied page with message.

![11](https://user-images.githubusercontent.com/61464267/133897329-3aabc3c5-6e90-4a40-a77c-646727740664.PNG)
