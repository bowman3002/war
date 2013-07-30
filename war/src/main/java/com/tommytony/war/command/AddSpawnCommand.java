/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tommytony.war.command;

import com.tommytony.war.War;
import com.tommytony.war.Warzone;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Austin
 */
class AddSpawnCommand extends AbstractWarCommand {

    public AddSpawnCommand(WarCommandHandler aThis, CommandSender sender, String[] arguments) throws NotZoneMakerException {
        super(aThis, sender, arguments);

		if (sender instanceof Player) { // i hate java for this.
			if (!War.war.isZoneMaker((Player) sender)) {
				for (String name : War.war.getZoneMakersImpersonatingPlayers()) {
					if (((Player) sender).getName().equals(name)) {
						return;
					}
				}
				throw new NotZoneMakerException();
			}
		}
    }

    @Override
    public boolean handle() {
        if (!(this.getSender() instanceof Player)) {
            this.badMsg("You can't do this if you are not in-game.");
            return true;
        }
        
        Player p=(Player)this.getSender();
        
        if(War.war.isZoneMaker(p)) {
            if(Warzone.getZoneByLocation(p)!=null) {
                Warzone w=Warzone.getZoneByLocation(p);
                w.addExtraSpawn(p.getLocation());
                p.sendMessage("EXTRA SPAWN ADDED!");
                return true;
            }
        }
        return false;
    }   
}
