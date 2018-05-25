Narrative:
IN ORDER TO access the service,
AS A user,
I WANT TO log in

Scenario: User log in
Given I have an account with username "user" and password "password"
When I enter the username "user" and password "password" for a basic user account
Then I am logged into my account