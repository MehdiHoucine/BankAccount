Feature: Account

  Scenario Outline: A user can withdraw an amount of money from his account
    Given An Account with an amount "<initial>" and an id "1"
    When the User withdraws or deposits the amount "<amount>" from the account with the id "1"
    Then the remaining amount in the account "1" is "<result>"
    And an exception of type "<exception>" is logged
    Examples:
      | initial | amount | result | exception                    |
      | 12      | -5     | 7      |                              |
      | 0       | -5     | 0      | InsufficientBalanceException |
      | 12      | 5      | 17     |                              |
      | 0       | 5      | 5      |                              |

  Scenario Outline: A user can transfer an amount of money from one account to another
    Given An Account with an amount "<initial1>" and an id "1"
    Given An Account with an amount "<initial2>" and an id "2"
    When the User transfers the amount "<amount>" from the account "1" to the account "2"
    Then the remaining amount in the account "1" is "<result1>"
    Then the remaining amount in the account "2" is "<result2>"
    And an exception of type "<exception>" is logged
    And a Transaction from "1" to "2" is "<transaction>" with the amount "<amount>"
    Examples:
      | initial1 | initial2 | amount | result1 | result2 | exception                    | transaction |
      | 12       | 0        | 5      | 7       | 5       |                              | created     |
      | 0        | 0        | 5      | 0       | 0       | InsufficientBalanceException | no created  |
      | 12       | 0        | 0      | 12      | 0       | InvalidAmountException       | not created |
      | 12       | 0        | -9     | 12      | 0       | InvalidAmountException       | not created |