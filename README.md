# PronounMC

<div align="center">
    <p>A Minecraft plugin for sharing pronouns</p>

[![Twitter](https://img.shields.io/badge/%40AceKiron--blue.svg?style=social&logo=Twitter)](https://twitter.com/AceKiron)
[![Discord](https://img.shields.io/badge/AceKiron%20Studio--blue.svg?style=social&logo=Discord)](https://dsc.gg/acekiron-studio)

![Workflow status](https://img.shields.io/github/workflow/status/AceKiron-Community/pronounmc/Build)
</div>

![PronounMC](/Resources/Branding/PronounMCLogo.png?raw=true "PronounMC")

***
<br>
<div id="default-pronouns"></div>

## Default supported pronouns

By default, PronounMC supports the following 13 pronouns:
Pronouns | More pronouns |
|---|---|
| He | She |
| They | It |
| Any | Other |
| Ask | Username |
| Ze | Xe |
| Ey | Ve |
| Ne | |

***
<br>

## Configuration
Name | Description | Default value |
|---|---|---|
| available-pronouns | A list of the pronouns a player can choose from | [See default supported pronouns](#default-pronouns) |
| handle-chat | "true" if PronounMC should handle chat, "false" is recommended when another plugin already handles chat | true |
| message-prefix | What all messages sent by PronounMC start with | "PronounMC: " |
| verbose-logging | Enabled verbose logging, mainly used for debugging purposes | false |
| log-changes | Log whenever someone adds or removes a pronoun | true |

***
<br>

## Commands
Command + Syntax | Description | Required permission | Optional permissions |
|---|---|---|---|
| /addpronouns \<pronoun\> [player] | Add pronouns to yourself or someone else | pronounmc.modify | pronounmc.modify.other |
| /removepronouns \<pronoun\> [player] | Remove pronouns from yourself or someone else | pronounmc.modify | pronounmc.modify.other |
| /getpronouns [player] | Get your own or someone else's pronouns | pronounmc.get | pronounmc.get.other |

***
<br>

## Permissions
Permission | Description |
|---|---|
| pronounmc.modify | Modify your own pronouns |
| pronounmc.modify.other | Modify someone else's pronouns |
| pronounmc.get | Get your own pronouns |
| pronounmc.get.other | Get someone else's pronouns (applies to the command, not chat) |