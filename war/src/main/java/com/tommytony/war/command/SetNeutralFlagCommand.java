/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tommytony.war.command;

import com.tommytony.war.Team;
import com.tommytony.war.War;
import com.tommytony.war.Warzone;
import com.tommytony.war.config.TeamKind;
import com.tommytony.war.mapper.WarzoneYmlMapper;
import com.tommytony.war.structure.NeutralFlag;
import java.util.logging.Level;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Bowman
 */
public class SetNeutralFlagCommand extends AbstractZoneMakerCommand {
    
    public SetNeutralFlagCommand(WarCommandHandler handler, CommandSender sender, String[] args) throws NotZoneMakerException {
        super(handler, sender, args);
    }
    
    @Override
    public boolean handle() {
        if (!(this.getSender() instanceof Player)) {
			this.badMsg("You can't do this if you are not in-game.");
			return true;
		}

		Player player = (Player) this.getSender();

		if (this.args.length != 1) {
			return false;
		}
		Warzone zone = Warzone.getZoneByLocation(player);

		if (zone == null) {
			return false;
		} else if (!this.isSenderAuthorOfZone(zone)) {
			return true;
		}
                
                NeutralFlag nF = zone.getNeutralFlag(args[0]);
                
                if(nF == null) {
                    //No neutral flag of such name
                    
                    nF = new NeutralFlag(args[0], player.getLocation(), zone);
                    zone.addNeutralFlag(nF);
                    
                    this.msg("Neutral flag " + args[0] + " added here.");
                    WarzoneYmlMapper.save(zone);
                    War.war.log(this.getSender().getName() + " created neutral flag " + args[0] + " in warzone " + zone.getName(), Level.INFO);

                } if(nF !=null) {
                    //move Neutral Flag
                    
                    nF.resetFlag(player.getLocation());
                    
                    this.msg("Neutral flag " + args[0] + " moved.");
			WarzoneYmlMapper.save(zone);
			War.war.log(this.getSender().getName() + " moved neutral flag " + args[0] + " in warzone " + zone.getName(), Level.INFO);
                }

		/*if (team == null) {
			// no such team yet
			this.msg("Place the team spawn first.");
		} else if (team.getFlagVolume() == null) {
			// new team flag
			team.setTeamFlag(player.getLocation());
			Location playerLoc = player.getLocation();
			player.teleport(new Location(playerLoc.getWorld(), playerLoc.getBlockX() + 1, playerLoc.getBlockY(), playerLoc.getBlockZ()));
			this.msg("Team " + team.getName() + " flag added here.");
			WarzoneYmlMapper.save(zone);
			War.war.log(this.getSender().getName() + " created team " + team.getName() + " flag in warzone " + zone.getName(), Level.INFO);
		} else {
			// relocate flag
			team.getFlagVolume().resetBlocks();
			team.setTeamFlag(player.getLocation());
			Location playerLoc = player.getLocation();
			player.teleport(new Location(playerLoc.getWorld(), playerLoc.getBlockX() + 1, playerLoc.getBlockY(), playerLoc.getBlockZ() + 1));
			this.msg("Team " + team.getName() + " flag moved.");
			WarzoneYmlMapper.save(zone);
			War.war.log(this.getSender().getName() + " moved team " + team.getName() + " flag in warzone " + zone.getName(), Level.INFO);
		}*/
                
                

		return true;
    }

}
