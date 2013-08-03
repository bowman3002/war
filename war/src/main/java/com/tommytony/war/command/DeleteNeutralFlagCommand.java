/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tommytony.war.command;

import com.tommytony.war.Team;
import com.tommytony.war.War;
import com.tommytony.war.Warzone;
import com.tommytony.war.mapper.WarzoneYmlMapper;
import com.tommytony.war.structure.NeutralFlag;
import com.tommytony.war.structure.ZoneLobby;
import java.util.logging.Level;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Bowman
 */
public class DeleteNeutralFlagCommand extends AbstractZoneMakerCommand {

    public DeleteNeutralFlagCommand(WarCommandHandler handler, CommandSender sender, String[] args) throws NotZoneMakerException {
        super(handler, sender, args);
    }
    
    @Override
    public boolean handle() {
        Warzone zone;

            if (this.args.length == 0) {
                    return false;
            } else if (this.args.length == 2) {
                    zone = Warzone.getZoneByName(this.args[0]);
                    this.args[0] = this.args[1];
            } else if (this.args.length == 1) {
                    if (!(this.getSender() instanceof Player)) {
                            return false;
                    }
                    zone = Warzone.getZoneByLocation((Player) this.getSender());
                    if (zone == null) {
                            ZoneLobby lobby = ZoneLobby.getLobbyByLocation((Player) this.getSender());
                            if (lobby == null) {
                                    return false;
                            }
                            zone = lobby.getZone();
                    }
            } else {
                    return false;
            }

            if (zone == null) {
                    return false;
            } else if (!this.isSenderAuthorOfZone(zone)) {
                    return true;
            }

            NeutralFlag nFlag = null;
            for (NeutralFlag nF : zone.getNeutralFlags()) {
                    if (nF.getName().equalsIgnoreCase(this.args[0].toLowerCase())) {
                            nFlag = nF;
                    }
            }
            if (nFlag != null) {
                    nFlag.deleteFlag();
                    zone.deleteNeutalFlag(nFlag);

                    WarzoneYmlMapper.save(zone);
                    zone.reinitialize();
                    this.msg(nFlag.getName() + " flag removed.");
                    War.war.log(this.getSender().getName() + " deleted neutral flag " + nFlag.getName() + " in warzone " + zone.getName(), Level.INFO);
            } else {
                    this.badMsg("No such team flag.");
            }

            return true;
    }

}
