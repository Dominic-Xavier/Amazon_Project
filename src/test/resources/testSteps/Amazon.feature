Feature: Automation Test
Description: This is an automation test for freelancing

Background: Opening application
	Given User opens the app

Scenario: Automation Test
	Given user clicks on hamburger menu
	When user selects Tv, Electronics category and selects Televisions
	Then Select Samsung and sort by proci high to low
	Then User selects the second largest price TV and prints the description under About this item section