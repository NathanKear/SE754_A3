Narrative:
IN ORDER TO access the service,
AS A user,
I WANT TO log in

Scenario: User log in
Given I have a BASIC account with username user and password password
When I enter the username user and password password
Then I am logged into my BASIC account with username user

Scenario: Admin log in
Given I have a ADMIN account with username admin and password password
When I enter the username admin and password password
Then I am logged into my ADMIN account with username admin