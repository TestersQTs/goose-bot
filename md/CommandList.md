# Command list

### Utility

| Command | Permission Required | Description                                   | Example |
| ------- | ------------------- | --------------------------------------------- | ------- |
| !ping   | None                | Returns the bot latency                       | !ping   |
| !info   | None                | Returns the configuration values              | !info   |
| !help   | None                | Returns some useful information about the bot | !help   |

<br>

### Configuration

| Command                                                         | Permission Required | Description                                                    | Example                                          |
| --------------------------------------------------------------- | ------------------- | -------------------------------------------------------------- | ------------------------------------------------ |
| !prefix `[str:new prefix]`                                      | MANAGE_SERVER       | Update the prefix                                              | !prefix `?`                                      |
| !reset                                                          | MANAGE_SERVER       | Resets the configuration values                                | !reset                                           |
| !configure events EVENT_MESSAGE_SEND `[double:new value]`       | MANAGE_SERVER       | Update the value for the goose to send messages                | !configure events EVENT_MESSAGE_SEND `0.4`       |
| !configure events EVENT_USER_VC_DISCONNECT `[double:new value]` | MANAGE_SERVER       | Update the value for the goose to disconnect you from VC       | !configure events EVENT_USER_VC_DISCONNECT `0.4` |
| !configure events EVENT_MESSAGE_DELETE `[double:new value]`     | MANAGE_SERVER       | Update the value for the goose to delete your message          | !configure events EVENT_MESSAGE_DELETE `0.4`     |
| !configure events EVENT_ROLE_DELETE `[double:new value]`        | MANAGE_SERVER       | Update the value for the goose to deleted the role you created | !configure events EVENT_ROLE_DELETE `0.4`        |
| !configure events EVENT_MESSAGE_REACT `[double:new value]`      | MANAGE_SERVER       | Update the value for the goose to react to your message        | !configure events EVENT_MESSAGE_REACT `0.4`      |

<br>

### Default configuration values

**MESSAGE_SEND** = `0.15 (15%)`  
**MESSAGE_REACT** = `0.33 (33%)`  
**USER_VC_DISCONNECT** = `0.35 (35%)`  
**MESSAGE_DELETE** = `0.05 (5%)`  
**ROLE_DELETE** = `0.33 (33%)`
