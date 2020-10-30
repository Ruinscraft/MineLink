# Setting Up MineLink
MineLink requires significant setup. Please read over these steps carefully.

## First Steps
1. Install the MineLink.jar into your Bukkit server's plugin directory. MineLink is not a BungeeCord or other plugin, only Bukkit.
2. Start or restart your Bukkit server to generate the MineLink configuration file. You will need to edit this next.
3. Open the MineLink configuration file (`yourserver/plugins/MineLink/config.yml`)
4. Ensure you have properly configured the `storage` configuration section. The default storage type is `mysql`. Double check your connection information, etc.
5. Determine what "link services" you want to use. For example, you may want to use XenForo and Discord. Add these to the `services.use` list in the configuration.
    ```
    services:
      use:
        - xenforo
        - discord
    ```
6. For each of the services you want to use, properly fill out their respective configuration information. For example, for XenForo, you should supply your board URL and the API key. Continue below for more information on this.

## Preparing Services
### XenForo
In order to use XenForo, you will need:
1. The MineLink XenForo plugin
2. An API key generated in your ACP

The XenForo plugin creates a custom route handler in XenForo that allows MineLink to listen for unique URLs. For example, when a player wishes to link their Minecraft account to a XenForo account, the MineLink Bukkit plugin generates a unique code in a database which is used to tie their Minecraft account to a randomly generated code. This code is used to verify, when the player clicks the unique URL, that a XenForo user has access to the Minecraft account they say they do (they must be logged into your board in order to link their account).

The unique URL the player has to visit may look like this: https://yourboard.com/link?code=8d4fdf75e06f44db8d5771f13d6b2371

#### Installing the MineLink XenForo plugin
1. Download the MineLink plugin .zip file
2. Head to `Addons > Addons` in your XenForo ACP
3. Click `Install & Upgrade`
4. Switch to `Install/upgrade from archive`
5. Select the .zip file and click `Upload`

#### Creating the API Key
To create an API key for MineLink to use. Head to `Setup > API keys` in your XenForo ACP. Click the `Add API key` button.

You will need to set the following options:
```
Title: Set an appropriate title, eg MineLink
Key type: Guest key
Allowed scopes: user:write
API key is active: ensure checked
```

After clicking `Save`, you should be shown an API key which you can copy. It will look something like this: `e-68vzy4TXLvrDMI6bjojS9RXT1AFNDk`

Head to your MineLink config.yml and update `services.xenforo.api-key` with this key.

### Discord
In order to use Discord, you will need:
