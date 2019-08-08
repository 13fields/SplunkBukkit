package splunkbukkit;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.*;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;

public class Main extends JavaPlugin implements Listener {

  FileConfiguration c;
  
// setting reusable locations 

public void onEnable() {
    getServer().getPluginManager().registerEvents(this, this);
    saveDefaultConfig();
    c = getConfig();
    new SplunkBukkit(System.getProperty("user.dir") + c.getString("logFile", "/logs/events.log"));
    if (c.getBoolean("logLoggerStatus"))
    SplunkBukkit.get.print("Logging started: SplunkBukkit starting up");
}

public void onDisable() {
    if (c.getBoolean("logLoggerStatus"))
    SplunkBukkit.get.print("Logging stopped: SplunkBukkit shutting down");
}

  @EventHandler
  public void logJoin(PlayerJoinEvent e) {
      if (c.getBoolean("logPlayerJoin")) {
        Player p = e.getPlayer();
        String ip = p.getAddress().getAddress().toString().replace("/", "");
        // this is messy but i'd like to get rid of pitch and yaw
        String loc = p.getLocation().toString().replace("Location","").replace("{", "").replace("}", "").replace(",", ", ").replace("name=world", "");
        
        SplunkBukkit.get.print(p.getDisplayName() + " decided to join in from " + ip + " at " + loc);
      }
  }

  @EventHandler
  public void logQuit(PlayerQuitEvent e) {
      if (c.getBoolean("logPlayerQuit")) {
        Player p = e.getPlayer();

        SplunkBukkit.get.print(p.getDisplayName() + " has disconnected"); 
      }
  }

  @EventHandler
  public void logKick(PlayerKickEvent e) {
      Player p = e.getPlayer();

      if (c.getBoolean("logPlayerQuit")) {
        SplunkBukkit.get.print(p.getDisplayName() + " has left: " + e.getLeaveMessage()); 
      }
  }

  @EventHandler
  public void logBreak(BlockBreakEvent e) {
      if (!e.isCancelled() && c.getBoolean("logBlockBreak")) {
        Player p = e.getPlayer();
        String loc = e.getBlock().getLocation().toString().replace("Location","").replace("{", "").replace("}", "").replace(",", ", ").replace("name=world", "");

        SplunkBukkit.get.print(p.getDisplayName() + " broke block " + e.getBlock().getType() + " ("
                  + e.getBlock().getBlockData().getAsString()  + ") at " + loc );
      }
  }

  @EventHandler
  public void logPlace(BlockPlaceEvent e) {
    if (!e.isCancelled() && c.getBoolean("logBlockPlace")) {
      Player p = e.getPlayer();
      String loc = e.getBlock().getLocation().toString().replace("Location","").replace("{", "").replace("}", "").replace(",", ", ").replace("name=world", "");


      SplunkBukkit.get.print(p.getDisplayName() + " placed block " + e.getBlock().getType() + " ("
                + e.getBlock().getBlockData().getAsString()   + ") at " + loc );
    }
  }

  @EventHandler
  public void logDrop(EntityDropItemEvent e) {
      if (!e.isCancelled() && c.getBoolean("logItemDrop")) {

        String loc = e.getEntity().getLocation().toString().replace("Location","").replace("{", "").replace("}", "").replace(",", ", ").replace("name=world", "");

        SplunkBukkit.get.print(e.getEntity().getName() + " dropped " + e.getItemDrop().getItemStack().getAmount() + " x " + e.getItemDrop().getItemStack().getType() + " ("
                  + e.getItemDrop().getItemStack().getData()  + ") at " + loc);

      }
  }


  @EventHandler
  public void logDrop(EntityPickupItemEvent e) {
      if (!e.isCancelled() && c.getBoolean("logItemPickup")) {
        
        String loc = e.getEntity().getLocation().toString().replace("Location","").replace("{", "").replace("}", "").replace(",", ", ").replace("name=world", "");

        SplunkBukkit.get.print(e.getEntity().getName() + " picked up " + e.getItem().getItemStack().getAmount() + " x " + e.getItem().getItemStack().getType() + " ("
                  + e.getItem().getItemStack().getData() + ") at " + loc);
      }
  }

  @EventHandler
  public void logFill(PlayerBucketFillEvent e) {
      if (!e.isCancelled() && c.getBoolean("logBucketFill")) {
        Player p = e.getPlayer();
        String loc = e.getBlockClicked().getLocation().toString().replace("Location","").replace("{", "").replace("}", "").replace(",", ", ").replace("name=world", "");

      if ( e.getBucket() != null && e.getItemStack().getType()==Material.LAVA_BUCKET) {

          SplunkBukkit.get.print(p.getDisplayName() + " filled bucket with LAVA at " + loc);
          }

      else if ( e.getBucket() != null && e.getItemStack().getType()==Material.WATER_BUCKET) {

          SplunkBukkit.get.print(p.getDisplayName() + " filled bucket with WATER at " + loc);
          }

      else if ( e.getBucket() != null && e.getItemStack().getType()==Material.MILK_BUCKET) {

          SplunkBukkit.get.print(p.getDisplayName() + " filled bucket with MILK at " + loc);
          }

      else if ( e.getBucket() != null && e.getItemStack().getType()==Material.COD_BUCKET) {

          SplunkBukkit.get.print(p.getDisplayName() + " filled bucket with COD at " + loc);
          }      

      else if ( e.getBucket() != null && e.getItemStack().getType()==Material.SALMON_BUCKET) {

          SplunkBukkit.get.print(p.getDisplayName() + " filled bucket with SALMON at " + loc);
          }      

      else if ( e.getBucket() != null && e.getItemStack().getType()==Material.PUFFERFISH_BUCKET) {

          SplunkBukkit.get.print(p.getDisplayName() + " filled bucket with PUFFERFISH at " + loc);
          }     

      else if ( e.getBucket() != null && e.getItemStack().getType()==Material.TROPICAL_FISH_BUCKET) {

          SplunkBukkit.get.print(p.getDisplayName() + " filled bucket with TROPICAL FISH at " + loc);
          }     
          
      else {

          SplunkBukkit.get.print(p.getDisplayName() + " filled bucket with " + e.getBlockClicked().getType() + " " + loc);
          }

      }
  }

  @EventHandler
  public void logEmpty(PlayerBucketEmptyEvent e) {
      if (!e.isCancelled() && c.getBoolean("logBucketEmpty")) {
        Player p = e.getPlayer();
        String loc = e.getBlockClicked().getLocation().toString().replace("Location","").replace("{", "").replace("}", "").replace(",", ", ").replace("name=world", "");
      
      if ( e.getBucket() != null && e.getBucket()==Material.LAVA_BUCKET) {

          SplunkBukkit.get.print(p.getDisplayName() + " emptied bucket of LAVA at " + loc);
          }

      else if ( e.getBucket() != null && e.getBucket()==Material.WATER_BUCKET) {

          SplunkBukkit.get.print(p.getDisplayName() + " emptied bucket of WATER at " + loc);
          }

      else if ( e.getBucket() != null && e.getBucket()==Material.MILK_BUCKET) {

          SplunkBukkit.get.print(p.getDisplayName() + " emptied bucket of MILK at " + loc);
          }

      else if ( e.getBucket() != null && e.getBucket()==Material.COD_BUCKET) {

          SplunkBukkit.get.print(p.getDisplayName() + " emptied bucket of COD at " + loc);
          }      

      else if ( e.getBucket() != null && e.getBucket()==Material.SALMON_BUCKET) {

          SplunkBukkit.get.print(p.getDisplayName() + " emptied bucket of SALMON at " + loc);
          }      

      else if ( e.getBucket() != null && e.getBucket()==Material.PUFFERFISH_BUCKET) {

          SplunkBukkit.get.print(p.getDisplayName() + " emptied bucket of PUFFERFISH at " + loc);
          }     
      
      else if ( e.getBucket() != null && e.getBucket()==Material.TROPICAL_FISH_BUCKET) {

          SplunkBukkit.get.print(p.getDisplayName() + " emptied bucket of TROPICAL FISH at " + loc);
          }      
      }
  }

  @EventHandler
  public void logCommand(PlayerCommandPreprocessEvent e) {
      if (!e.isCancelled() && c.getBoolean("logPlayerCommand")) {
        Player p = e.getPlayer();
        String loc = p.getLocation().toString().replace("Location","").replace("{", "").replace("}", "").replace(",", ", ").replace("name=world", "");

        SplunkBukkit.get.print(p.getDisplayName() + " ran command (" + e.getMessage() + ") at " + loc );
      }
  }

  // if attack causes the player to die then this happens
  @EventHandler
  public void logDeath(PlayerDeathEvent e) {
      if (c.getBoolean("logPlayerDeath")) {
          String deathMessage = "";
          String playerName = e.getEntity().getName();
          
          Player p = e.getEntity();
          String loc = p.getLocation().toString().replace("Location","").replace("{", "").replace("}", "").replace(",", ", ").replace("name=world", "");

          // Get the default death message
          String message = this.convertConfigTags(String.valueOf(this.c.get("CUSTOM")), playerName);

          // Get a list of damage cause
          EntityDamageEvent ev = e.getEntity().getLastDamageCause();
          DamageCause cause = e.getEntity().getPlayer().getLastDamageCause().getCause();
          
  // This part is for entity attack entity
  if (ev instanceof EntityDamageByEntityEvent) {
      Entity damager = ((EntityDamageByEntityEvent) ev).getDamager();

      // This condition check is to prevent class cast exception caused by mob attack
      if ((damager instanceof Player) && !(cause == DamageCause.PROJECTILE)) {

        String itemName = ((Player) damager).getInventory().getItemInMainHand().getType().toString().toLowerCase();
        message = this.convertConfigTags(this.c.getString("KILL_BY_WEAPON"), playerName,
        damager.getName(), itemName);

      } else if (cause == DamageCause.ENTITY_EXPLOSION) {

        deathMessage = this.c.getString("ENTITY_EXPLOSION");
        message = this.convertConfigTags(deathMessage, playerName, damager.getName());

      } else if (cause == DamageCause.PROJECTILE) {

        // this gets the name of the projectile and damager
        deathMessage = this.c.getString("PROJECTILE");
        message = this.convertConfigTags(deathMessage, playerName, damager.getName());

        // Mob attack

      } else if (cause == DamageCause.LIGHTNING) {

        deathMessage = this.c.getString("LIGHTNING");
        message = this.convertConfigTags(deathMessage, playerName, damager.getName());

      } else if (cause == DamageCause.ENTITY_ATTACK && !(damager instanceof Player)) {

        deathMessage = this.c.getString("MOB_ATTACK");
        message = this.convertConfigTags(deathMessage, playerName, damager.getName());
      }
    } else {

      message = this.getDeathMessage(cause, playerName);
  }

    String finalMsg = message;
    e.setDeathMessage(finalMsg);
    
    SplunkBukkit.get.print(message + " at " + loc);

      }
  }

 /**
   * This method determine the death message of each death case, including the name of the victim.
   * @param cause
   * @return the death message for different cases except ENTITY_EXPLOSION, ENTITY_ATTACK, and PROJECTILE
   */
  public String getDeathMessage(DamageCause cause, String playerName) {
    String deathMessage;
    switch (cause) {
      case SUFFOCATION:
        deathMessage = this.c.getString("SUFFOCATION");
        break;
      case FALL:
        deathMessage = this.c.getString("FALL");
        break;
      case FIRE:
        deathMessage = this.c.getString("FIRE");
        break;
      case FIRE_TICK:
        deathMessage = this.c.getString("FIRE_TICK");
        break;
      case LAVA:
        deathMessage = this.c.getString("LAVA");
        break;
      case DROWNING:
        deathMessage = this.c.getString("DROWNING");
        break;
      case BLOCK_EXPLOSION:
        deathMessage = this.c.getString("BLOCK_EXPLOSION");
        break;
      case VOID:
        deathMessage = this.c.getString("VOID");
        break;
      case SUICIDE:
        deathMessage = this.c.getString("SUICIDE");
        break;
      case MAGIC:
        deathMessage = this.c.getString("MAGIC");
        break;
      default:
        deathMessage = this.c.getString("CUSTOM");
    }
    String result = this.convertConfigTags(deathMessage, playerName);
    return result;
  }

  // only triggers to show if entity gets attacked but survives 
  @EventHandler 
  public void playerDamage(EntityDamageByEntityEvent e) {
      if (!e.isCancelled() && c.getBoolean("logPlayerDamage"))  {
      if (e.getEntity() instanceof Player) {

      // what entity was damaged and where? 
      Player p = (Player) e.getEntity();
      String playerName = p.getDisplayName();
      LivingEntity alive = (LivingEntity) p;
      double health = alive.getHealth();

      // where did it happen? 
      String loc = p.getLocation().toString().replace("Location","").replace("{", "").replace("}", "").replace(",", ", ").replace("name=world", "");

      String damageMessagePlayer = this.c.getString("PLAYER_DAMAGE");
      String messagePlayer = this.convertConfigTagsPlayer(damageMessagePlayer, playerName);
      
          if( health - e.getFinalDamage() >= 0) {

          damageMessagePlayer = this.c.getString("PLAYER_ATTACK_DAMAGE");
          messagePlayer = this.convertConfigTagsPlayer(damageMessagePlayer, playerName, e.getDamager().getName());
          } 

          SplunkBukkit.get.print(messagePlayer + " at " + loc);
          }


      } 
  }   

  // if entity is damaged OR dies then this occurs 
  @EventHandler 
  public void entityDamage(EntityDamageByEntityEvent e) {
      if (!e.isCancelled() && c.getBoolean("logEntityDeath"))  {
      if(!(e.getEntity() instanceof Player)) {
      // what entity was damaged? 
          String entityName = e.getEntity().getName();
          LivingEntity alive = (LivingEntity) e.getEntity();
          double health = alive.getHealth();

      // where did it happen? 
      String loc = e.getEntity().getLocation().toString().replace("Location","").replace("{", "").replace("}", "").replace(",", ", ").replace("name=world", "");
   
      // Get the default death message
          String deathMessageEntity = this.c.getString("MOB_CUSTOM");
          String messageEntity = this.convertConfigTagsEntity(deathMessageEntity, entityName);
      
      // who did the damage and with what? 
          Entity damager = e.getDamager();

          String itemName = ((Player) damager).getInventory().getItemInMainHand().getType().toString().toLowerCase();

          if(((health - e.getDamage()) <= 0) && (!(itemName == null | itemName.length() == 0))) {
              messageEntity = this.convertConfigTagsEntity(this.c.getString("MOB_KILL_BY_WEAPON"), entityName, damager.getName(), itemName);
                  
          } else if(((health - e.getDamage()) <= 0) && ((itemName == "Air"))) {
              itemName = "fists";
              messageEntity = this.convertConfigTagsEntity(this.c.getString("MOB_KILL_BY_WEAPON"), entityName, damager.getName(), itemName);

          } else if(((health - e.getDamage()) <= 0) && (itemName == null | itemName.length() == 0)) {
              deathMessageEntity = this.c.getString("MOB_DEATH");
              messageEntity = this.convertConfigTagsEntity(deathMessageEntity, entityName);
                
          } else if ((health - e.getDamage()) <= 0) {
              DamageCause causeEntity = e.getEntity().getLastDamageCause().getCause();
              messageEntity = this.getDeathMessageEntity(causeEntity, entityName);

          } else if(((health - e.getDamage()) >= 0) &&  (!(itemName == null | itemName.length() == 0))) {
              deathMessageEntity = this.c.getString("MOB_DAMAGE_BY_WEAPON");
              messageEntity = this.convertConfigTagsEntity(deathMessageEntity, entityName, damager.getName(), itemName);

          } else if (((health - e.getDamage()) >= 0) && (!(itemName == "Air" | itemName.length() == 3))) {
              deathMessageEntity = this.c.getString("MOB_DAMAGE");
              messageEntity = this.convertConfigTagsEntity(deathMessageEntity, entityName, damager.getName());
          } else {
              
          }  
          SplunkBukkit.get.print(messageEntity + " at " + loc);

          } else {

          }
      }
  }            
  
/**
   * This method determine the death message of each death case, including the name of the victim.
   * @param causeEntity
   * @return the death message for different cases except ENTITY_EXPLOSION, ENTITY_ATTACK, and PROJECTILE
   */
  public String getDeathMessageEntity(DamageCause causeEntity, String entityName) {
    String deathMessageEntity;
    switch (causeEntity) {
      default:
      deathMessageEntity = this.c.getString("MOB_DEATH");
    }
    String result = this.convertConfigTags(deathMessageEntity, entityName);
    return result;
  }

/**
   * This method will convert configuration tags to variables in a provided String
   * @param String deathMessage
   * @param String deathMessageEntity
   * @param String damageMessagePlayer
   * @param String entityName
   * @param String playerName
   * @param String weaponName
   * @param String Attacker
   * @param String 
   */
  public String convertConfigTags(String deathMessage, String playerName, String Attacker,
      String weaponName) {
    String newDeathMessage = "";
    newDeathMessage = deathMessage.replace("<Player>", playerName);
    newDeathMessage = newDeathMessage.replace("<Attacker>", Attacker);
    newDeathMessage = newDeathMessage.replace("<WeaponName>", weaponName);
    return newDeathMessage;
  }

  public String convertConfigTags(String deathMessage, String playerName, String Attacker) {
    String newDeathMessage = "";
    newDeathMessage = deathMessage.replace("<Player>", playerName);
    newDeathMessage = newDeathMessage.replace("<Attacker>", Attacker);
    return newDeathMessage;
  }

  public String convertConfigTags(String deathMessage, String playerName) {
    String newDeathMessage = "";
    newDeathMessage = deathMessage.replace("<Player>", playerName);
    return newDeathMessage;
  }

  public String convertConfigTagsEntity(String deathMessageEntity, String entityName, String Attacker,
      String weaponName) {
    String newDeathMessageEntity = "";
    newDeathMessageEntity = deathMessageEntity.replace("<Entity>", entityName);
    newDeathMessageEntity = newDeathMessageEntity.replace("<Attacker>", Attacker);
    newDeathMessageEntity = newDeathMessageEntity.replace("<WeaponName>", weaponName);
    return newDeathMessageEntity;
  }

  public String convertConfigTagsEntity(String deathMessageEntity, String entityName, String Attacker) {
    String newDeathMessageEntity = "";
    newDeathMessageEntity = deathMessageEntity.replace("<Entity>", entityName);
    newDeathMessageEntity = newDeathMessageEntity.replace("<Attacker>", Attacker);
    return newDeathMessageEntity;
  }

  public String convertConfigTagsEntity(String deathMessageEntity, String entityName) {
    String newDeathMessageEntity = "";
    newDeathMessageEntity = deathMessageEntity.replace("<Entity>", entityName);
    return newDeathMessageEntity;
  }

  public String convertConfigTagsPlayer(String damageMessagePlayer, String entityName, String Attacker) {
    String newDamageMessagePlayer = "";
    newDamageMessagePlayer = damageMessagePlayer.replace("<Player>", entityName);
    newDamageMessagePlayer = newDamageMessagePlayer.replace("<Attacker>", Attacker);
    return newDamageMessagePlayer;
  }

  public String convertConfigTagsPlayer(String damageMessagePlayer, String entityName) {
    String newDamageMessagePlayer = "";
    newDamageMessagePlayer = damageMessagePlayer.replace("<Player>", entityName);
    return newDamageMessagePlayer;
  }

}