Feature: Basic arithmetic

  Scenario: Add two numbers
    Given the calculator is open
    When I enter 12
    And I press add
    And I enter 7
    And I press equals
    Then the result should be 19

  Scenario: Subtract two numbers
    Given the calculator is open
    When I enter 20
    And I press subtract
    And I enter 5
    And I press equals
    Then the result should be 15

  Scenario: Multiply two numbers
    Given the calculator is open
    When I enter 3
    And I press multiply
    And I enter 4
    And I press equals
    Then the result should be 12

  Scenario: Divide two numbers
    Given the calculator is open
    When I enter 9
    And I press divide
    And I enter 3
    And I press equals
    Then the result should be 3

  Scenario: Percent of a number
    Given the calculator is open
    When I enter 50
    And I press percent
    Then the result should be 0.5

  Scenario: All clear resets to zero
    Given the calculator is open
    When I enter 99
    And I press AC
    Then the result should be 0

  Scenario: Subtract to get a negative result
    Given the calculator is open
    When I enter 5
    And I press subtract
    And I enter 10
    And I press equals
    Then the result should be -5

  Scenario: Add two numbers with decimals
    Given the calculator is open
    When I enter 2.5
    And I press add
    And I enter 3.5
    And I press equals
    Then the result should be 6

  Scenario: Perform a chained operation
    Given the calculator is open
    When I enter 10
    And I press add
    And I enter 5
    And I press subtract
    And I enter 3
    And I press equals
    Then the result should be 12

  Scenario: View math tutor explanation after operation
    Given the calculator is open
    When I enter 15
    And I press multiply
    And I enter 3
    And I press equals
    Then the result should be 45
    When I open the math tutor
    Then the math tutor should be visible
    And the explanation should contain "15"
    And the explanation should contain "3"

  Scenario: Open currency converter and enter amount
    Given the calculator is open
    When I open the currency converter
    Then the currency converter should be displayed
    When I enter amount "100"
    Then the amount should be displayed as "100"

  Scenario: Currency converter displays default currencies
    Given the calculator is open
    When I open the currency converter
    Then the currency converter should be displayed
    And the first currency should show "PKR"
    And the second currency should show "USD"

  Scenario: Calculate sine of 30 degrees
    Given the calculator is open
    When I open the scientific calculator
    Then the scientific calculator should be displayed
    When I switch to degree mode
    And I press sin button
    And I enter scientific number "30"
    And I press right parenthesis
    And I press scientific equals
    Then the scientific result should contain "0.5"

  Scenario: Calculate square root of 16
    Given the calculator is open
    When I open the scientific calculator
    Then the scientific calculator should be displayed
    When I press sqrt button
    And I enter scientific number "16"
    And I press right parenthesis
    And I press scientific equals
    Then the scientific result should contain "4"

  Scenario: Calculate 2 raised to power 3
    Given the calculator is open
    When I open the scientific calculator
    Then the scientific calculator should be displayed
    When I enter scientific number "2"
    And I press power button
    And I enter scientific number "3"
    And I press scientific equals
    Then the scientific result should be "8"

  Scenario: Calculate factorial of 5
    Given the calculator is open
    When I open the scientific calculator
    Then the scientific calculator should be displayed
    When I enter scientific number "5"
    And I press factorial button
    And I press scientific equals
    Then the scientific result should be "120"

  Scenario: Calculate square of 7
    Given the calculator is open
    When I open the scientific calculator
    Then the scientific calculator should be displayed
    When I enter scientific number "7"
    And I press square button
    And I press scientific equals
    Then the scientific result should be "49"

  Scenario: View math tutor explanation in scientific calculator
    Given the calculator is open
    When I open the scientific calculator
    Then the scientific calculator should be displayed
    When I enter scientific number "6"
    And I press factorial button
    And I press scientific equals
    Then the scientific result should be "720"
    When I open the math tutor in scientific calculator
    Then the scientific math tutor should be visible
    And the scientific explanation should contain "6"

  Scenario: Ask chatbot about calculator features
    Given the calculator is open
    When I open the chatbot
    Then the chatbot should be displayed
    When I send message "What is 5 plus 3?"
    Then the chatbot should show a response

