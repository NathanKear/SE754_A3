Narrative:
IN ORDER TO access the service,
AS A user,
I WANT TO sign up

Scenario: User sign up
Given Application is open
When I enter the username "user" and password "password"
Then I have a new account created for me with username "user"