Narrative:
IN ORDER TO access the service,
AS A user,
I WANT TO sign up

Scenario: User sign up
Given I have not signed up
When I enter the username "user" and password "password"
Then I have a new account created for me