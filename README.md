# NiceSetSpawn

## Last Build Status:
 - Main: [![Main CI/CD](https://github.com/Edulynch/NiceSetSpawn/actions/workflows/main.yml/badge.svg?branch=main)](https://github.com/Edulynch/NiceSetSpawn/actions/workflows/main.yml)
 - Developer: [![Developer CI/CD](https://github.com/Edulynch/NiceSetSpawn/actions/workflows/developer.yml/badge.svg?branch=developer)](https://github.com/Edulynch/NiceSetSpawn/actions/workflows/developer.yml)

## Support Versión:
1.8, 1.9, 1.10, 1.11, 1.12, 1.13, 1.14, 1.15, 1.16, 1.17

## This Plugin Need [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/) to Work.

## Commands & Permissions

|Command|Usage|Permission|Default|
|-------|-----|----------|-------|
|/setspawn|Set spawn point|nicesetspawn.setspawn|OP|
|/spawn|Teleport to spawn|nicesetspawn.teleportothers|all|
|/spawn [player]|Teleport to spawn a player|nicesetspawn.spawn|OP|
|/nss help|show NiceSetSpawn help|nicesetspawn.help|OP|
|/nss info|Show NiceSetSpawn Information|nicesetspawn.info|OP|
|/nss reload|Reload NiceSetSpawn configs|nicesetspawn.reload|OP|
|/nss setdelay [seconds]|Set delay in seconds|nicesetspawn.setdelay|OP|
| |Allows you to bypass teleport delay|nicesetspawn.bypassdelay|OP|
| |Allows you to bypass teleport in PVP|nicesetspawn.bypasspvp|OP|
| |Access to all commands|nicesetspawn.all|OP|
| |Access to all commands|nicesetspawn.\* |OP|

## Functions
 - Easy setup and config.
 - Customizable messages.
 - Lobby Friendly
 - Teleport to spawn on the first join/join/respawn/void fall.
 - Teleportation delay.
 - Block /spawn command in PvP.
 - Player join, player quit, first join broadcast.
 - Player join, first join welcome message.
 - Set fly/gamemode on join.
 - Check for new updates.

## Installation
 - Download the plugin.
 - Put EasySetSpawn.jar into the plugins folder.
 - Start your server.
 - Modify config.yml if you need (Optional).
 - Reload config (if you modified config.yml).

## Update
 - Download the plugin.
 - Replace the plugin with the new one in the plugins folder.
 - Start your server.
 - Modify config.yml to look like the older one.
 - Reload config.

## Donation:
 - https://ko-fi.com/edulynch
 - https://www.buymeacoffee.com/edulynch

## Discord:
https://discord.gg/fUhdvyTE

## TODO
 - Translatable
 - Fly Toggle
 - Block PVP in Join Temporarily
 - check new version
 - Request by Issues

## pom.xml
#### (Replace outputDirectory tag with yours)
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-jar-plugin</artifactId>
    <version>2.3.1</version>
    <configuration>
        <outputDirectory>C:/Users/MyUser/Desktop/MINECRAFT_SERVER/plugins/</outputDirectory>
    </configuration>
</plugin>
```