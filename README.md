# Bamboo Manager
** Manage your Minecraft server well, in an easy way. **
Yet another simple administrator tool.

#### Requirement
1. Java Development Kit 8 or higher
2. Bukkit API 1.13 or higher

| Command | Subcommand | Description | Usage |
| -------- | -------- | -------- | -------- |
| bamboo | adminkits | Give the command executor (Player) administator tools | /bamboo adminkits|
|| broadcast | Send message to the whole server | /bamboo broadcast &#60;Message&#62; |
|| info | Get the information about the plugin | /bamboo info |
|| platform | Generate an 8x8 2D platform at the feet of the command executor | /bamboo platform |
|| playerlist | Display current online player list at the chat bar | /bamboo playerlist |
|| playerinfo | Get the information about an online player | /bamboo playerinfo &#60;Player&#62; |
|| plugins | Display installed plugin list at the chat bar | /bamboo plugins |
|| remove | Remove entities | /bamboo remove &#60;monsters &#124; items&#62; |
|| serverinfo | Get the information about your server | /bamboo serverinfo |
| point | / | Report the command executor's position or messure the distance to another player | /point [Player] |

| Annotation | Listener | Description |
| -------- | -------- | -------- |
| EventHandler | onPlayerInteract | Listen the usage of administrator tool and the custom items |
| EventHandler | onInteractAtEntity | Listen when the administrator use the "Entity Killer" tool |
| EventHandler | onInventoryClick | Provide entry to the GUI |
| EventHandler | onPlayerBedEnter | Broadcast a message when a player enter his/her bed |
| EventHandler | onPlayerBedLeave | Broadcast a message when a player leave his/her bed |
| EventHandler | onPlayerAdvancementDone | Give custom items when a player achieved an advancement |
| EventHandler | onPlayerDeath | Give a player 0.05% chance to keep his/her xp level & inventory |
| EventHandler | onEntitiyExplode | Broadcast a warning message when someone trys to make explosion |
| EventHandler | onJoin | Replace the original message with this one |
| EventHandler | onQuit | Replace the original message with this one |
| EventHandler | onAsyncPlayerChat | Record chat messages in game to the plugins' folder |

#### Donate us
Click the link: https://www.bambooisland.top/donate/
Thank you for your support ~

