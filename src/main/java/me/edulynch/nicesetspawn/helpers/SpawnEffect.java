package me.edulynch.nicesetspawn.helpers;

import me.edulynch.nicesetspawn.config.EnumConfig;
import me.edulynch.nicesetspawn.Main;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.metadata.FixedMetadataValue;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

public class SpawnEffect {

    public static void register(Location location) {
        playSoundInWorld(location);
        createHelix(location);
        spawnFireworks(location);
    }

    @SuppressWarnings("all")
    private static void playSoundInWorld(Location location) {
        if (EnumConfig.OPTIONS_SPAWN_SOUND_ENABLED.getConfigBoolean()) {
            World world = location.getWorld();
            Sound sound = Sound.valueOf(EnumConfig.OPTIONS_SPAWN_SOUND_NAME.getConfigString(null));
            world.playSound(location, sound, 100, 0);

        }
    }

    private static void createHelix(Location location) {
        if (EnumConfig.OPTIONS_SPAWN_PARTICLE_ENABLED.getConfigBoolean()) {
            int radius = 2;
            for (double y = 0; y <= 20; y += 0.025) {
                double x = radius * Math.cos(y * 7);
                double z = radius * Math.sin(y * 7);
                String search = "";
                try {
                    search = EnumConfig.OPTIONS_SPAWN_PARTICLE_NAME.getConfigString(null);
                    ParticleEffect particle = ParticleEffect.valueOf(search);
                    ParticleBuilder particleBuilder = new ParticleBuilder(particle,
                            new Location(location.getWorld(), (float) (location.getX() + x), (float) (location.getY() + y), (float) (location.getZ() + z)))
                            .setOffset(0, 0, 0)
                            .setSpeed(0)
                            .setAmount(1);
                    particleBuilder.display();
                } catch (Exception e) {
                    Main.getInstance().getLogger().severe("§cParticle: " + search + " Not Found.");
                }
            }
        }
    }

    @SuppressWarnings("all")
    private static void spawnFireworks(Location location) {
        if (EnumConfig.OPTIONS_SPAWN_FIREWORKS_ENABLED.getConfigBoolean()) {

            Firework fw = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
            fw.setMetadata("nodamage", new FixedMetadataValue(Main.getInstance(), true));
            FireworkMeta fwm = fw.getFireworkMeta();

            fwm.setPower(2);
            fwm.addEffect(FireworkEffect.builder().withColor(Color.LIME).flicker(true).build());

            fw.setFireworkMeta(fwm);
            fw.detonate();

            for (int i = 0; i < EnumConfig.OPTIONS_SPAWN_FIREWORKS_AMOUNT.getConfigInteger(); i++) {
                Firework fw2 = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
                FireworkMeta fwm2 = fw.getFireworkMeta();
                fwm2.addEffect(FireworkEffect.builder().withColor(Color.LIME).flicker(true).build());
                fw2.setFireworkMeta(fwm);
            }
        }
    }

}
