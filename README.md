# PronounMC
PronounMC is a Minecraft plugin designed to make it easier to keep track of pronouns.

## Supported pronouns
There are a few pronouns supported by default, but can be modified by adding a new pronoun to the `config.yml` config file:
* **He**
* **She**
* **They**
* **It**
* **Any**
* **Other**
* **Ask**
* **Username**

## Permissions
- **pronounmc.modify**: Set your own pronouns
- **pronounmc.modify.other**: Set someone else's pronouns
- **pronounmc.get**: Get pronouns

## Commands
- **/addpronouns \<pronoun\> [player]**: Add pronouns to your own or someone else.
- **/removepronouns \<pronoun\> [player]**: Remove pronouns from your own or someone else.
- **/getpronouns [player]**: Get your own or someone else's pronouns.

## Integration into other plugins
PronounMC softdepends on PlaceholderAPI for integration into other plugins.