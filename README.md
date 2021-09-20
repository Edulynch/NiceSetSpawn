<h1 style="text-align: center;">
  <br>NiceSetSpawn<br>
</h1>

<p style="text-align: center;">
  <b>A Minecraft Plugin to set the global spawn with useful configuration with Support from <code>1.8</code> to <code>1.17.1</code></b><br><br>
  <a href="https://www.codacy.com/gh/Edulynch/NiceSetSpawn/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Edulynch/NiceSetSpawn&amp;utm_campaign=Badge_Grade">
      <img src="https://app.codacy.com/project/badge/Grade/9503545c58a54a7f9029ce0f8b8c1ec1" alt="codacy"/>
  </a>
  <a href="https://github.com/Edulynch/NiceSetSpawn/issues">
      <img alt="issues" src="https://img.shields.io/github/issues/Edulynch/NiceSetSpawn">
  </a>
  <a href="https://github.com/Edulynch/NiceSetSpawn/stargazers">
      <img alt="stars" src="https://img.shields.io/github/stars/Edulynch/NiceSetSpawn">
  </a>
  <a href="https://img.shields.io/github/workflow/status/Edulynch/NiceSetSpawn/Main%20Java%20CI%20with%20Maven/main">
    <img src="https://img.shields.io/github/workflow/status/Edulynch/NiceSetSpawn/Main%20Java%20CI%20with%20Maven/main" alt="build"/><br><br>
  </a>
  <a href="https://discord.gg/CShtDSdvTv">Discord</a> •
  <a href="https://bstats.org/plugin/bukkit/NiceSetSpawn/12777">Statistics</a> •
  <a href="https://www.spigotmc.org/resources/nicesetspawn.96240/">Download</a> •
  <a href="https://github.com/Edulynch/NiceSetSpawn/wiki">Wiki</a></p>
<div style="text-align: center;"><h2>This Plugin Need <a href="https://www.spigotmc.org/resources/placeholderapi.6245/">PlaceholderAPI</a> to Work.</h2></div>

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
|/nss showeffects|Test Spawn Particles, Sound And Fireworks|nicesetspawn.showeffects|OP|
| |Allows you to bypass teleport delay|nicesetspawn.bypassdelay|OP|
| |Allows you to bypass teleport in PVP|nicesetspawn.bypasspvp|OP|
| |Access to all commands|nicesetspawn.all|OP|
| |Access to all commands|nicesetspawn.\* |OP|

## New Features

- Added Particle when Player Spawn
    - Support all Particles between 1.8.x and 1.17.x
- Fully Translatable Messages
    - messages-en_US.yml : Just copy and edit message then edit config.yml
- New options in config.yml
    - translate-messages: en_US (change for yours messages-*.yml)
    - options.disable-fall-damage: Disable Fall Damage (true/false)
    - options.spawn.particle.enabled: Enable or Disable Particle in Spawn (true/false)
    - options.spawn.particle.name: Name of Particle (ex: FLAME)
    - options.spawn.fireworks.enabled: Enable or Disable Fireworks in Spawn (true/false)
    - options.spawn.fireworks.amount: Amount of Fireworks in Spawn (ex: 5)
    - options.spawn.sound.enabled: Enable or Disable Sound in Spawn (true/false)
    - options.spawn.sound.name: Name of Sound in Spawn (ex: ENTITY_GHAST_SCREAM)
- New command:
    - /nss showeffects (Test Spawn Particles, Sound And Fireworks)
- PlaceholderAPI Library compatibility improved

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

- Fly Toggle
- Block PVP in Join Temporarily
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