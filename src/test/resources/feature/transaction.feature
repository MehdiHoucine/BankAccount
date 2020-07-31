Feature: Transaction

  Scenario Outline: A user can query the transaction history
    Given An Account with an id "1"
    Given An Account with an id "2"
    Given An Account with an id "3"
    Given An Account with an id "4"
    Given A Transaction "1" from "1" to "2"
    Given A Transaction "2" from "1" to "3"
    Given A Transaction "3" from "2" to "3"
    When the User retrieves the transactions for the account "<accountId>"
    Then the Transaction ids are "<result>"
    Examples:
      | accountId | result |
      | 1         | 1,2    |
      | 2         | 1,3    |
      | 3         | 2,3    |
      | 4         |        |