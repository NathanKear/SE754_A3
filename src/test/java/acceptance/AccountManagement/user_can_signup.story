Narrative:
IN ORDER TO access the service,
AS A user,
I WANT TO sign up

Scenario: User sign up
Given I am in the application
When I signup for a BASIC account with the username user and password password
Then I have a new BASIC account created for me with username user

Scenario: ADMIN sign up
Given I am in the application
When I signup for a ADMIN account with the username admin and password password
Then I have a new ADMIN account created for me with username admin